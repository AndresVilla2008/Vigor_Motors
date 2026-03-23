package com.concesionario.vigorMotors.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un email válido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener mínimo 8 caracteres")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=(?:.*\\d){2,})(?=.*[@$!%*?&._-]).+$",
             message = "La contraseña debe tener mayúscula, minúscula, 2 números y un carácter especial")
    private String password;

}