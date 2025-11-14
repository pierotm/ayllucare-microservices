package com.profiles.service.interfaces.rest.resources;

import com.profiles.service.domain.model.valueobjects.EmergencyContact;
import com.profiles.service.domain.model.valueobjects.Gender;

import java.time.LocalDate;

/**
 * Resource for updating a user's profile.
 * Contains only fields that can be updated independently.
 */
public record UpdateProfileResource(
        String firstName,
        String lastName,
        LocalDate birthDate,
        Gender gender,
        String phoneNumber,
        String email,
        String city,
        EmergencyContact emergencyContact,
        Long primaryDoctorId
) {}
