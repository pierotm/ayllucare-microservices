package com.profiles.service.interfaces.rest.resources;

import com.profiles.service.domain.model.valueobjects.EmergencyContact;
import com.profiles.service.domain.model.valueobjects.Gender;

import java.time.LocalDate;

/**
 * REST resource representing a full user profile.
 */
public record ProfileResource(Long id,
                              Long userId,
                              String fullName,
                              LocalDate birthDate,
                              Gender gender,
                              String phoneNumber,
                              String email,
                              String city,
                              EmergencyContact emergencyContact,
                              Long primaryDoctorId) {}
