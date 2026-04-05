package com.concesionario.vigorMotors.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concesionario.vigorMotors.entity.OrderItem;
import com.concesionario.vigorMotors.entity.Orders;
import com.concesionario.vigorMotors.entity.Orders.OrderStatus;
import com.concesionario.vigorMotors.entity.Vehicle;
import com.concesionario.vigorMotors.repository.OrderItemRepository;
import com.concesionario.vigorMotors.repository.OrdersRepository;
import com.concesionario.vigorMotors.repository.VehicleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final VehicleRepository vehicleRepository;
    private final JwtService jwtService;

    @Transactional
    public Orders purchase(String token) {

        Long userId = jwtService.extractUserId(token);

        List<OrderItem> items = orderItemRepository.findByUserIdAndOrderIdIsNull(userId);

        if (items.isEmpty()) {
            throw new RuntimeException("No se encontraron productos seleccionados");
        }

        for (OrderItem item : items) {
            Vehicle vehicle = vehicleRepository.findById(item.getVehicle())
                    .orElseThrow(() -> new RuntimeException("Vehículo no encontrado: " + item.getVehicle()));

            int nuevoStock = vehicle.getStock() - item.getQuantity();
            if (nuevoStock < 0) {
                throw new RuntimeException(
                        "Stock insuficiente para el vehículo: " + vehicle.getBrand() + " " + vehicle.getModel()
                        + ". Stock disponible: " + vehicle.getStock()
                        + ", cantidad solicitada: " + item.getQuantity());
            }

            vehicle.setStock(nuevoStock);
            vehicleRepository.save(vehicle);
        }

        BigDecimal total = items.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Orders order = new Orders();
        order.setUserId(userId);
        order.setTotalPrice(total);
        order.setStatus(OrderStatus.COMPLETED);
        order.setCreatedAt(LocalDateTime.now());
        orderRepository.save(order);

        items.forEach(item -> item.setOrderId(order.getId()));
        orderItemRepository.saveAll(items);

        return order;
    }
}
