package com.concesionario.vigorMotors.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.concesionario.vigorMotors.dto.MessageResponseDTO;
import com.concesionario.vigorMotors.dto.OrderItemsRequestDTO;
import com.concesionario.vigorMotors.service.JwtService;
import com.concesionario.vigorMotors.service.OrderItemService;
import com.concesionario.vigorMotors.service.OrdersService;
import com.concesionario.vigorMotors.service.TokenBlacklistService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ordersItems")
@RequiredArgsConstructor
public class OrderController {

    private final TokenBlacklistService tokenBlacklistService;
    private final OrderItemService orderItemService;
    private final OrdersService ordersService;
    private final JwtService jwtService;

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
            error.setMessage("Sesion Cerrada, Inicie sesion nuevamente");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        String role = jwtService.extractRole(token);
        if (!role.equals("CLIENT")) {
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage("No tienes permisos para realizar esta acción");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        }

        orderItemService.saveItems(request, token);
        MessageResponseDTO response = new MessageResponseDTO();
        response.setMessage("Producto(s) seleccionado(s) correctamente");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchase(HttpServletRequest httpRequest) {

        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage("Token no proporcionado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        String token = authHeader.substring(7);

        if (tokenBlacklistService.isBlacklisted(token)) {
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage("La sesión fue cerrada, inicia sesión nuevamente");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        String role = jwtService.extractRole(token);
        if (!role.equals("CLIENT")) {
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage("No tienes permisos para realizar esta acción");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        }

        ordersService.purchase(token);
        MessageResponseDTO response = new MessageResponseDTO();
        response.setMessage("Se ha realizado la compra exitosamente");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @GetMapping("/viewSelectedProducts")
    public ResponseEntity<?> getSelectedProducts(HttpServletRequest httpRequest) {

        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage("Token no proporcionado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        String token = authHeader.substring(7);

        if (tokenBlacklistService.isBlacklisted(token)) {
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage("Sesión cerrada, inicie sesión nuevamente");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        String role = jwtService.extractRole(token);
        if (!role.equals("CLIENT")) {
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage("No tienes permisos para realizar esta acción");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        }

        return ResponseEntity.ok(orderItemService.getSelectedItems(token));
    }

    @DeleteMapping("/deleteItem/{itemId}")
    public ResponseEntity<?> deleteItem(@PathVariable Long itemId, HttpServletRequest httpRequest) {

        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage("Token no proporcionado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        String token = authHeader.substring(7);

        if (tokenBlacklistService.isBlacklisted(token)) {
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage("La sesión fue cerrada, inicia sesión nuevamente");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        String role = jwtService.extractRole(token);
        if (!role.equals("CLIENT")) {
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage("No tienes permisos para realizar esta acción");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        }

        Long userId = jwtService.extractUserId(token);
        orderItemService.deleteItem(itemId, userId);

        MessageResponseDTO response = new MessageResponseDTO();
        response.setMessage("Producto eliminado correctamente");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/purchaseHistory")
    public ResponseEntity<?> getPurchaseHistory(HttpServletRequest httpRequest) {

        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage("Token no proporcionado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        String token = authHeader.substring(7);

        if (tokenBlacklistService.isBlacklisted(token)) {
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage("Sesión cerrada, inicie sesión nuevamente");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        String role = jwtService.extractRole(token);
        if (!role.equals("CLIENT")) {
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage("No tienes permisos para realizar esta acción");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        }

        try {
            return ResponseEntity.ok(orderItemService.getPurchaseHistory(token));
        } catch (RuntimeException e) {
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @GetMapping("/admin/purchaseHistory")
    public ResponseEntity<?> getAllPurchaseHistory(HttpServletRequest httpRequest) {

        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage("Token no proporcionado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        String token = authHeader.substring(7);

        if (tokenBlacklistService.isBlacklisted(token)) {
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage("Sesión cerrada, inicie sesión nuevamente");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        String role = jwtService.extractRole(token);
        if (!role.equals("ADMIN")) {
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage("No tienes permisos para realizar esta acción");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        }

        try {
            return ResponseEntity.ok(orderItemService.getAllPurchaseHistory());
        } catch (RuntimeException e) {
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}