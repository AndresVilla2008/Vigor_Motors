package com.concesionario.vigorMotors.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.concesionario.vigorMotors.dto.OrderItemsRequestDTO;
import com.concesionario.vigorMotors.entity.OrderItem;
import com.concesionario.vigorMotors.entity.Vehicle;
import com.concesionario.vigorMotors.repository.OrderItemRepository;
import com.concesionario.vigorMotors.repository.VehicleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    
    private final OrderItemRepository orderItemRepository;
    private final VehicleRepository vehicleRepository;
    private final JwtService jwtService;

    public List<OrderItem> saveItems(OrderItemsRequestDTO requestDTO, String token) {
        List<OrderItem> items = new ArrayList<>();

        Long userId = jwtService.extractUserId(token);

        for (Long vehicleId : requestDTO.getVehicleIds()) {
            Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() -> new RuntimeException("Vehiculo no encontrado: "+ vehicleId));

            OrderItem item = new OrderItem();
            item.setVehicle(vehicleId);
            item.setQuantity(requestDTO.getVehicleIds().size());
            item.setPrice(vehicle.getPrice());
            item.setUserId(userId);
            
            if (item.getQuantity() > 1) {
                item.setPrice(vehicle.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            }

            orderItemRepository.save(item);
            items.add(item);
        }

        return items;
    }
}
