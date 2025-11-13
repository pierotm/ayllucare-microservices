package com.iam.service.interfaces.rest.resources;

import java.util.List;

/**
 * Resource for registering a new doctor by a manager.
 */
public record RegisterDoctorResource(String email, String password, List<String> roles) {
}
