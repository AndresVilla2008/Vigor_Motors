package com.concesionario.vigorMotors.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.concesionario.vigorMotors.dto.MessageResponseDTO;
import com.concesionario.vigorMotors.dto.RegisterVehicleRequestDTO;
import com.concesionario.vigorMotors.dto.RegisterVehicleResponseDTO;
import com.concesionario.vigorMotors.dto.VehicleClientDTO;
import com.concesionario.vigorMotors.entity.Vehicle;
import com.concesionario.vigorMotors.service.VehicleService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody RegisterVehicleRequestDTO dto,
                                    HttpServletRequest request) {
        if (!isAdmin(request)) {
            return forbidden();
        }
        try {
            RegisterVehicleResponseDTO response = vehicleService.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return error("No se pudo registrar el vehículo: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll(HttpServletRequest request) {
        try {
            List<Vehicle> vehicles = vehicleService.findAll();
            if (vehicles.isEmpty()) {
                return error("No hay vehículos registrados en el sistema", HttpStatus.OK);
            }
            if (isAdmin(request)) {
                return ResponseEntity.ok(vehicles);
            }

            List<VehicleClientDTO> result = vehicles.stream()
                    .map(vehicleService::mapToClientDTO)
                    .toList();
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return error("Error al obtener los vehículos: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id, HttpServletRequest request) {
        try {
            Vehicle vehicle = vehicleService.findById(id);
            
            if (isAdmin(request)) {
                return ResponseEntity.ok(vehicle);
            }

            return ResponseEntity.ok(vehicleService.mapToClientDTO(vehicle));
        } catch (RuntimeException e) {
            return error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody RegisterVehicleRequestDTO dto,
                                    HttpServletRequest request) {
        if (!isAdmin(request)) {
            return forbidden();
        }
        try {
            RegisterVehicleResponseDTO response = vehicleService.update(id, dto);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest request) {
        if (!isAdmin(request)) {
            return forbidden();
        }
        try {
            Vehicle vehicle = vehicleService.findById(id);
            String nombre = vehicle.getBrand() + " " + vehicle.getModel();
            vehicleService.delete(id);
            return ok("Vehículo " + nombre + " eliminado correctamente");
        } catch (RuntimeException e) {
            return error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    private boolean isAdmin(HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        return "ADMIN".equals(role);
    }

    private ResponseEntity<MessageResponseDTO> forbidden() {
        return error("Acceso denegado: necesitas rol ADMIN para realizar esta acción", HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<MessageResponseDTO> ok(String mensaje) {
        MessageResponseDTO response = new MessageResponseDTO();
        response.setMessage(mensaje);
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<MessageResponseDTO> error(String mensaje, HttpStatus status) {
        MessageResponseDTO response = new MessageResponseDTO();
        response.setMessage(mensaje);
        return ResponseEntity.status(status).body(response);
    }
}