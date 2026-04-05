package com.concesionario.vigorMotors.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SelectedItemResponseDTO {
    private Long itemId;
    private Integer quantity;
    private BigDecimal price;
    private Long vehicleId;
    private String brand;
    private String model;
    private Integer year;
    private String color;
    private String fuelType;
    private String transmission;
    private String imageUrl;
}
