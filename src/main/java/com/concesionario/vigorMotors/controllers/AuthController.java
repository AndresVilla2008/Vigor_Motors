package com.concesionario.vigorMotors.controllers;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.concesionario.vigorMotors.dto.*;
import com.concesionario.vigorMotors.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<MessageResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
        } catch (Exception e) {
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(authService.login(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<RefreshTokenResponseDTO> refreshToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        try {
            return ResponseEntity.ok(authService.refreshToken(authHeader.substring(7)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}