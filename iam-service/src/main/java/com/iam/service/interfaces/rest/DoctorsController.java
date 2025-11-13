package com.iam.service.interfaces.rest;

import com.iam.service.domain.services.UserCommandService;
import com.iam.service.interfaces.rest.resources.RegisterDoctorResource;
import com.iam.service.interfaces.rest.transform.RegisterDoctorCommandFromResourceAssembler;
import com.iam.service.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controller for operations related to doctors in the system.
 */
@RestController
@RequestMapping(value = "/api/v1/doctors", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Doctors", description = "Doctor Management Endpoints")
public class DoctorsController {

    private final UserCommandService userCommandService;

    public DoctorsController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    /**
     * Register a new doctor by a manager.
     *
     * @param resource The doctor data
     * @param request The HTTP request containing X-User-Id header with the manager's ID
     * @return ResponseEntity with the created doctor user or an error
     */
    @PostMapping(value = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Register a new doctor", description = "Allows a manager to register a new doctor user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Doctor registered successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid data or email already exists"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Not logged in"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Not a manager")
    })
    public ResponseEntity<?> registerDoctor(
            @RequestBody RegisterDoctorResource resource,
            HttpServletRequest request) {

        // Get manager ID from header set by gateway
        String userIdHeader = request.getHeader("X-User-Id");
        if (userIdHeader == null || userIdHeader.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "message", "Unauthorized",
                            "error", "User not authenticated",
                            "errorCode", "NOT_AUTHENTICATED"
                    ));
        }

        Long managerId;
        try {
            managerId = Long.valueOf(userIdHeader);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "message", "Bad request",
                            "error", "Invalid user ID",
                            "errorCode", "INVALID_USER_ID"
                    ));
        }

        // Verify user has manager role
        String rolesHeader = request.getHeader("X-User-Roles");
        if (rolesHeader == null || !rolesHeader.contains("MANAGER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of(
                            "message", "Access denied",
                            "error", "Manager role required for this operation",
                            "errorCode", "REQUIRES_MANAGER_ROLE"
                    ));
        }

        try {
            var command = RegisterDoctorCommandFromResourceAssembler.toCommandFromResource(resource, managerId);
            var doctor = userCommandService.handle(command);

            if (doctor.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of(
                                "message", "Error registering doctor",
                                "error", "Invalid data",
                                "errorCode", "INVALID_DATA"
                        ));
            }

            var doctorResource = UserResourceFromEntityAssembler.toResourceFromEntity(doctor.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(doctorResource);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Email already exists")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of(
                                "message", "Failed to register doctor",
                                "error", "Email already exists",
                                "errorCode", "EMAIL_EXISTS"
                        ));
            } else if (e.getMessage().contains("Role name not found")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of(
                                "message", "Error registering doctor",
                                "error", "Role not found",
                                "errorCode", "ROLE_NOT_FOUND"
                        ));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of(
                                "message", "Error registering doctor",
                                "error", e.getMessage(),
                                "errorCode", "REGISTRATION_ERROR"
                        ));
            }
        }
    }
}
