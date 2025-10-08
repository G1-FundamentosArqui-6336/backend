package org.upc.cobox.iam.domain.model.commands;

import org.upc.cobox.iam.domain.model.entities.Role;

import java.util.List;

public record SignUpCommand(
        String email,
        String password,
        String firstName,
        String lastName,
        String phone,
        List<Role> roles) {
}
