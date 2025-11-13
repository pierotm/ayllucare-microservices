package com.iam.service.interfaces.rest.resources;

/**
 * Resource for registering a new patient.
 */
public record RegisterPatientResource(String email, String password) {
}
