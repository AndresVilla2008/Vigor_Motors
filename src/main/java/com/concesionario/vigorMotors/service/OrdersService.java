package com.concesionario.vigorMotors.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.concesionario.vigorMotors.entity.OrderItem;
import com.concesionario.vigorMotors.entity.Orders;
import com.concesionario.vigorMotors.enums.OrderStatus;
import com.concesionario.vigorMotors.repository.OrderItemRepository;
import com.concesionario.vigorMotors.repository.OrdersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final JwtService jwtService;

    public Orders purchase(String token) {

        Long userId = jwtService.extractUserId(token);

        List<OrderItem> items = orderItemRepository.findByOrderIdIsNull();

        if (items.isEmpty()) {
            throw new RuntimeException("No se encontraron productos seleccionados");
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
