package com.concesionario.vigorMotors.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.concesionario.vigorMotors.entity.Vehicle;
import com.concesionario.vigorMotors.entity.Vehicle.FuelType;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    int countByBrandIgnoreCaseAndModelIgnoreCase(String brand, String model);

    List<Vehicle> findByBrandIgnoreCase(String brand);

    List<Vehicle> findByFuelType(FuelType fuelType);

    List<Vehicle> findByStockGreaterThan(int stock);
}