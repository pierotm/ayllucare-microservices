package com.profiles.service.interfaces.rest.resources;

import com.profiles.service.domain.model.valueobjects.EmergencyContact;
import com.profiles.service.domain.model.valueobjects.Gender;

import java.time.LocalDate;

public record CreateProfileResource(Long userId,
                                    String firstName,
                                    String lastName,
                                    LocalDate birthDate,
                                    Gender gender,
                                    String phoneNumber,
                                    String email,
                                    String city,
                                    EmergencyContact emergencyContact) {

    public CreateProfileResource {
        if (userId == null)
            throw new IllegalArgumentException("User ID is required");
        if (firstName == null || firstName.isBlank())
            throw new IllegalArgumentException("First name is required");
        if (lastName == null || lastName.isBlank())
            throw new IllegalArgumentException("Last name is required");
        if (birthDate == null)
            throw new IllegalArgumentException("Birth date is required");
        if (gender == null)
            throw new IllegalArgumentException("Gender is required");
        if (phoneNumber == null || phoneNumber.isBlank())
            throw new IllegalArgumentException("Phone number is required");
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("Email is required");
        if (city == null || city.isBlank())
            throw new IllegalArgumentException("City is required");
        if (emergencyContact == null)
            throw new IllegalArgumentException("Emergency contact is required");
    }
}
