package com.concesionario.vigorMotors.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.concesionario.vigorMotors.dto.ChangeRoleRequestDTO;
import com.concesionario.vigorMotors.dto.MessageResponseDTO;
import com.concesionario.vigorMotors.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/changeRole")
    public ResponseEntity<MessageResponseDTO> changeRole(
            @Valid @RequestBody ChangeRoleRequestDTO dto,
            HttpServletRequest request) {

        String role = (String) request.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage("Acceso denegado: necesitas rol ADMIN para realizar esta acción");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        }

        try {
            return ResponseEntity.ok(userService.changeRole(dto));
        } catch (RuntimeException e) {
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}