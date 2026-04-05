package com.concesionario.vigorMotors.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "order_items")
public class OrderItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "vehicle_id")
    private Long vehicle;

    @Column(name = "quantity")
    private Integer quantity = 1;

    @Column(name = "price_at_purchase")
    private BigDecimal price;

    @Column(name = "user_id")
    private Long userId;
}
