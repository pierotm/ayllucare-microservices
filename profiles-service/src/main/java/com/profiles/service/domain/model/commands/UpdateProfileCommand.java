package com.profiles.service.domain.model.commands;

import com.profiles.service.domain.model.valueobjects.EmergencyContact;
import com.profiles.service.domain.model.valueobjects.Gender;

import java.time.LocalDate;

/**
 * Update Profile Command
 * @param userId ID of the user this profile belongs to
 * @param firstName First name
 * @param lastName Last name
 * @param phoneNumber Phone number
 */
public record UpdateProfileCommand(
    Long userId,
    String firstName,
    String lastName,
    LocalDate birthDate,
    Gender gender,
    String phoneNumber,
    String email,
    String city,
    EmergencyContact emergencyContact
) {}
