package com.dibyendu.demoeventmanagement.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDto {
    @NotEmpty
    @NotNull
    @Email(message = "Provide a valid email format")
    private String email;
    @NotEmpty
    @NotNull
    private String password;
    private String role="ADMIN";
    private String associatedEvent;
}
