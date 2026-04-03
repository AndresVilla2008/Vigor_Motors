package com.concesionario.vigorMotors.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.concesionario.vigorMotors.entity.OrderItem;

@Repository
public interface OrderItemRepository extends  JpaRepository<OrderItem, Long>{
     List<OrderItem> findByOrderIdIsNull();
}
