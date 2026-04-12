package com.concesionario.vigorMotors.dto;
import lombok.Data;

@Data
public class LoginResponseDTO extends MessageResponseDTO {
    private String jwt;
}