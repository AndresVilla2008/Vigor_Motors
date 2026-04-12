package com.concesionario.vigorMotors.dto;
import lombok.Data;

@Data
public class RefreshTokenResponseDTO extends MessageResponseDTO {
    private String jwt;
}