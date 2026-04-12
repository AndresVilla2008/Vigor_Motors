package com.concesionario.vigorMotors.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.concesionario.vigorMotors.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long>{
   
}
