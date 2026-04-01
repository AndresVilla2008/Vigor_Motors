package com.concesionario.vigorMotors.service;
 
import java.util.List;
 
import org.springframework.stereotype.Service;
 
import com.concesionario.vigorMotors.dto.RegisterVehicleRequestDTO;
import com.concesionario.vigorMotors.dto.RegisterVehicleResponseDTO;
import com.concesionario.vigorMotors.entity.Vehicle;
import com.concesionario.vigorMotors.repository.VehicleRepository;
 
import lombok.RequiredArgsConstructor;
 
@Service
@RequiredArgsConstructor
public class VehicleService {
 
    private final VehicleRepository vehicleRepository;
 
    public RegisterVehicleResponseDTO create(RegisterVehicleRequestDTO dto) {
        Vehicle vehicle = mapToEntity(dto);
        Vehicle saved = vehicleRepository.save(vehicle);
        return mapToResponse(saved, "Vehículo " + saved.getBrand() + " " + saved.getModel() + " registrado correctamente con id " + saved.getId());
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
        existing.setStock(dto.getStock());
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
    }
 
    private Vehicle mapToEntity(RegisterVehicleRequestDTO dto) {
        Vehicle v = new Vehicle();
        v.setBrand(dto.getBrand());
        v.setModel(dto.getModel());
        v.setYear(dto.getYear());
        v.setPrice(dto.getPrice());
        v.setStock(dto.getStock());
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