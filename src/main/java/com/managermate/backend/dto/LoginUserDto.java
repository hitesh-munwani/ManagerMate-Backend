package com.managermate.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginUserDto {

    @NotBlank
    @Schema(description = "Email Address ")
    private String email;

    @NotBlank
    @Schema(description = "Password ")
    private String password;

}
