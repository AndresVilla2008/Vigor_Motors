package com.concesionario.vigorMotors.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.concesionario.vigorMotors.dto.MessageResponseDTO;
import com.concesionario.vigorMotors.dto.OrderItemsRequestDTO;
import com.concesionario.vigorMotors.entity.OrderItem;
import com.concesionario.vigorMotors.service.OrderItemService;
import com.concesionario.vigorMotors.service.TokenBlacklistService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ordersItems")
@RequiredArgsConstructor
public class OrderItemController {

    private final TokenBlacklistService tokenBlacklistService;
    private final OrderItemService orderItemService;

    @PostMapping("/selectProduct")
public ResponseEntity<?> selectProducts(@RequestBody OrderItemsRequestDTO request, HttpServletRequest httpRequest) {

    if (request == null || request.getVehicleIds() == null || request.getVehicleIds().isEmpty()) {
        MessageResponseDTO error = new MessageResponseDTO();
        error.setMessage("Debes seleccionar al menos un vehículo");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    String authHeader = httpRequest.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        MessageResponseDTO error = new MessageResponseDTO();
        error.setMessage("Token no proporcionado");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    String token = authHeader.substring(7);

    if (tokenBlacklistService.isBlacklisted(token)) {
        MessageResponseDTO error = new MessageResponseDTO();
        error.setMessage("Session closed, please login again");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    orderItemService.saveItems(request);
    return ResponseEntity.status(HttpStatus.OK).body("Producto(s) seleccionado(s)");
}
}
