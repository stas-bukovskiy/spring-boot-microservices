package com.recordsystem.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.recordsystem.auth.models.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateUserRequest {

    @NotBlank(message = "username can not be empty")
    @Size(min = 3, message = "username must be larger that 3 characters")
    private String username;

    @NotBlank(message = "password can not be empty")
    @Size(min = 6, message = "password must be larger that 6 characters")
    private String password;

    @NotBlank(message = "first name can not be empty")
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank(message = "last name can not be empty")
    @JsonProperty("last_name")
    private String lastName;

    @JsonIgnore
    private UserRole role;
}
