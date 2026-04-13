package com.concesionario.vigorMotors.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.concesionario.vigorMotors.dto.RegisterVehicleRequestDTO;
import com.concesionario.vigorMotors.dto.RegisterVehicleResponseDTO;
import com.concesionario.vigorMotors.dto.VehicleClientResponseDTO;
import com.concesionario.vigorMotors.entity.Vehicle;
import com.concesionario.vigorMotors.repository.VehicleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public RegisterVehicleResponseDTO create(RegisterVehicleRequestDTO dto) {
        int nuevoStock = vehicleRepository.countByBrandIgnoreCaseAndModelIgnoreCase(
                dto.getBrand(), dto.getModel()) + 1;

        Vehicle vehicle = mapToEntity(dto);
        vehicle.setStock(nuevoStock);

        List<Vehicle> existentes = vehicleRepository.findByBrandIgnoreCase(dto.getBrand())
                .stream()
                .filter(v -> v.getModel().equalsIgnoreCase(dto.getModel()))
                .toList();

        for (Vehicle v : existentes) {
            v.setStock(nuevoStock);
        }
        vehicleRepository.saveAll(existentes);

        Vehicle saved = vehicleRepository.save(vehicle);
        return mapToResponse(saved, "Vehículo " + saved.getBrand() + " " + saved.getModel() + " registrado correctamente. Stock actual: " + saved.getStock());
    }

    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    public Vehicle findById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró ningún vehículo con el id " + id));
    }

    public RegisterVehicleResponseDTO update(Long id, RegisterVehicleRequestDTO dto) {
        Vehicle existing = findById(id);
        existing.setBrand(dto.getBrand());
        existing.setModel(dto.getModel());
        existing.setYear(dto.getYear());
        existing.setPrice(dto.getPrice());
        existing.setColor(dto.getColor());
        existing.setFuelType(dto.getFuelType());
        existing.setTransmission(dto.getTransmission());
        existing.setMileage(dto.getMileage());
        existing.setDescription(dto.getDescription());
        existing.setImageUrl(dto.getImageUrl());
        Vehicle updated = vehicleRepository.save(existing);
        return mapToResponse(updated, "Vehículo " + updated.getBrand() + " " + updated.getModel() + " actualizado correctamente");
    }

    public void delete(Long id) {
        Vehicle existing = findById(id);
        vehicleRepository.delete(existing);

        List<Vehicle> restantes = vehicleRepository.findByBrandIgnoreCase(existing.getBrand())
                .stream()
                .filter(v -> v.getModel().equalsIgnoreCase(existing.getModel()))
                .toList();

        int nuevoStock = restantes.size();
        for (Vehicle v : restantes) {
            v.setStock(nuevoStock);
        }
        vehicleRepository.saveAll(restantes);
    }

    public VehicleClientResponseDTO mapToClientDTO(Vehicle v) {
        VehicleClientResponseDTO dto = new VehicleClientResponseDTO();
        dto.setId(v.getId());
        dto.setBrand(v.getBrand());
        dto.setModel(v.getModel());
        dto.setYear(v.getYear());
        dto.setPrice(v.getPrice());
        dto.setStock(v.getStock());
        dto.setColor(v.getColor());
        dto.setFuelType(v.getFuelType());
        dto.setTransmission(v.getTransmission());
        dto.setMileage(v.getMileage());
        dto.setDescription(v.getDescription());
        dto.setImageUrl(v.getImageUrl());
        return dto;
    }

    private Vehicle mapToEntity(RegisterVehicleRequestDTO dto) {
        Vehicle v = new Vehicle();
        v.setBrand(dto.getBrand());
        v.setModel(dto.getModel());
        v.setYear(dto.getYear());
        v.setPrice(dto.getPrice());
        v.setColor(dto.getColor());
        v.setFuelType(dto.getFuelType());
        v.setTransmission(dto.getTransmission());
        v.setMileage(dto.getMileage());
        v.setDescription(dto.getDescription());
        v.setImageUrl(dto.getImageUrl());
        return v;
    }

    private RegisterVehicleResponseDTO mapToResponse(Vehicle v, String message) {
        RegisterVehicleResponseDTO response = new RegisterVehicleResponseDTO();
        response.setId(v.getId());
        response.setBrand(v.getBrand());
        response.setModel(v.getModel());
        response.setYear(v.getYear());
        response.setPrice(v.getPrice());
        response.setStock(v.getStock());
        response.setColor(v.getColor());
        response.setFuelType(v.getFuelType());
        response.setTransmission(v.getTransmission());
        response.setMileage(v.getMileage());
        response.setDescription(v.getDescription());
        response.setImageUrl(v.getImageUrl());
        response.setCreatedAt(v.getCreatedAt());
        response.setMessage(message);
        return response;
    }
}