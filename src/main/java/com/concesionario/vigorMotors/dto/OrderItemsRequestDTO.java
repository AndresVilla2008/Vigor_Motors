package com.concesionario.vigorMotors.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderItemsRequestDTO {
    private List<Long> vehicleIds;
}
