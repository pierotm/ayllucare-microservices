package com.profiles.service.interfaces.rest.transform;

import com.profiles.service.domain.model.aggregates.Profile;
import com.profiles.service.interfaces.rest.resources.ProfileResource;

public class ProfileResourceFromEntityAssembler {
    public static ProfileResource toResourceFromEntity(Profile entity) {
        return new ProfileResource(
                entity.getId(),
                entity.getUserId(),
                entity.getFullName(),
                entity.getBirthDate(),
                entity.getGender(),
                entity.getPhoneNumber(),
                entity.getEmail(),
                entity.getCity(),
                entity.getEmergencyContact(),
                entity.getPrimaryDoctorId());
    }
}
