package com.iam.service.domain.model.commands;

/**
 * Command to register a new patient.
 * <p>
 *     This command is used when a patient creates an account on their own.
 *     Unlike doctors, patients do not require a manager to create their account,
 *     and do not provide a list of roles. The system automatically assigns
 *     the {@code ROLE_PATIENT} role during the sign-up process.
 * </p>
 *
 * @param email the email of the patient
 * @param password the password of the patient
 */

public record RegisterPatientCommand(String email, String password) {
}
