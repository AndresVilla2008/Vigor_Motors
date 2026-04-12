package com.concesionario.vigorMotors.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.concesionario.vigorMotors.entity.Vehicle.FuelType;
import com.concesionario.vigorMotors.entity.Vehicle.Transmission;

import lombok.Data;

@Data
public class RegisterVehicleResponseDTO {

    private Long id;
    private String brand;
    private String model;
    private Integer year;
    private BigDecimal price;
    private Integer stock;
    private String color;
    private FuelType fuelType;
    private Transmission transmission;
    private Integer mileage;
    private String description;
    private String imageUrl;
    private LocalDateTime createdAt;
    private String message;
}