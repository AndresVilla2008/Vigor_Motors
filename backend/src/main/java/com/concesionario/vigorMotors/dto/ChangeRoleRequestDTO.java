package com.concesionario.vigorMotors.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ChangeRoleRequestDTO {

    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "El rol es obligatorio")
    @Pattern(regexp = "ADMIN|CLIENT", message = "El rol debe ser ADMIN o CLIENT")
    private String role;
}