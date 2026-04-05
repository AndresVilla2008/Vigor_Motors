package com.concesionario.vigorMotors.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PurchaseHistoryAdminResponseDTO {
    private Long userId;

    private BigDecimal totalPrice;
    private String status;

    private String brand;
    private String model;
    private Integer year;
    private String color;
    private String fuelType;
    private String transmission;
    private String imageUrl;

    private Integer quantity;
    private BigDecimal price;
}

