package com.iam.service.interfaces.rest.transform;
import com.iam.service.domain.model.commands.RegisterPatientCommand;
import com.iam.service.interfaces.rest.resources.RegisterPatientResource;

/**
 * Assembler to convert a RegisterPatientResource to a RegisterPatientCommand.
 */
public class RegisterPatientCommandFromResourceAssembler {
    /**
     * Converts a RegisterPatientResource to a RegisterPatientCommand.
     *
     * @param resource The resource to convert
     * @return The RegisterPatientCommand
     */
    public static RegisterPatientCommand toCommandFromResource(RegisterPatientResource resource) {
        return new RegisterPatientCommand(resource.email(), resource.password());
    }
}
