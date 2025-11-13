package com.iam.service.domain.model.commands;

import com.iam.service.domain.model.entities.Role;

import java.util.List;

/**
 * Command to register a new doctor by a manager.
 * <p>
 *     This command is used by managers to create new doctor users in the system.
 *     It contains the doctor's username, password, assigned roles, and the ID of the manager creating the doctor.
 * </p>
 */
public record RegisterDoctorCommand(String username, String password, List<Role> roles, Long managerId) {
}
