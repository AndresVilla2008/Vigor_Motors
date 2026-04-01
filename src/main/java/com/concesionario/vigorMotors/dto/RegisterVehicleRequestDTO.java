package com.concesionario.vigorMotors.dto;

import java.math.BigDecimal;

import com.concesionario.vigorMotors.entity.Vehicle.FuelType;
import com.concesionario.vigorMotors.entity.Vehicle.Transmission;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RegisterVehicleRequestDTO {

    @NotBlank(message = "La marca es obligatoria")
    private String brand;

    @NotBlank(message = "El modelo es obligatorio")
    private String model;

    @NotNull(message = "El año es obligatorio")
    @Min(value = 1900, message = "El año debe ser mayor a 1900")
    private Integer year;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    private BigDecimal price;

    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock = 0;

    private String color;

    private FuelType fuelType;

    private Transmission transmission;

    @Min(value = 0, message = "El kilometraje no puede ser negativo")
    private Integer mileage = 0;

    private String description;

    private String imageUrl;
}