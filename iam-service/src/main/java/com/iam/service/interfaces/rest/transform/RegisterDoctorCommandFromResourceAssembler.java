package com.iam.service.interfaces.rest.transform;

import com.iam.service.domain.model.commands.RegisterDoctorCommand;
import com.iam.service.interfaces.rest.resources.RegisterDoctorResource;

/**
 * Assembler to convert a RegisterDoctorResource to a RegisterDoctorCommand.
 */
public class RegisterDoctorCommandFromResourceAssembler {

    /**
     * Converts a RegisterDoctorResource to a RegisterDoctorCommand.
     *
     * @param resource The resource to convert
     * @param managerId The ID of the manager registering the doctor
     * @return The RegisterDoctorCommand
     */
    public static RegisterDoctorCommand toCommandFromResource(RegisterDoctorResource resource, Long managerId) {
        var roles = RoleListFromStringAssembler.toRoleListFromStringList(resource.roles());
        return new RegisterDoctorCommand(resource.email(), resource.password(), roles, managerId);
    }
}
