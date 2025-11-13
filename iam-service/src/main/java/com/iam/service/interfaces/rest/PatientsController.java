package com.iam.service.interfaces.rest;

import com.iam.service.domain.services.UserCommandService;
import com.iam.service.interfaces.rest.resources.RegisterPatientResource;
import com.iam.service.interfaces.rest.transform.RegisterPatientCommandFromResourceAssembler;
import com.iam.service.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for operations related to patients in the system.
 */
@RestController
@RequestMapping(value = "/api/v1/patients", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Patients", description = "Patient Registration Endpoints")
public class PatientsController {
    private final UserCommandService userCommandService;

    public PatientsController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    /**
     * Register a new patient.
     *
     * @param resource Patient registration data
     * @return ResponseEntity with the created patient user or an error
     */
    @PostMapping(value = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Register a new patient", description = "Allows a new patient to register in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Patient registered successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid data"),
            @ApiResponse(responseCode = "409", description = "Conflict - Email already exists")
    })
    public ResponseEntity<?> registerPatient(@RequestBody RegisterPatientResource resource) {

        try {
            var command = RegisterPatientCommandFromResourceAssembler.toCommandFromResource(resource);
            var patient = userCommandService.handle(command);

            if (patient.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(
                                java.util.Map.of(
                                        "message", "Error registering patient",
                                        "error", "Invalid data",
                                        "errorCode", "INVALID_DATA"
                                )
                        );
            }

            var patientResource = UserResourceFromEntityAssembler.toResourceFromEntity(patient.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(patientResource);

        } catch (RuntimeException e) {

            if (e.getMessage().contains("Email already exists")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(
                                java.util.Map.of(
                                        "message", "Failed to register patient",
                                        "error", "Email already exists",
                                        "errorCode", "EMAIL_EXISTS"
                                )
                        );
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(
                            java.util.Map.of(
                                    "message", "Error registering patient",
                                    "error", e.getMessage(),
                                    "errorCode", "REGISTRATION_ERROR"
                            )
                    );
        }
    }
}
