package com.concesionario.vigorMotors.service;

import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.concesionario.vigorMotors.dto.*;
import com.concesionario.vigorMotors.entity.Users;
import com.concesionario.vigorMotors.repository.UsersRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final JwtService jwtService;

    public MessageResponseDTO register(RegisterRequestDTO request) {
        if (usersRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Este email ya está registrado");
        }

        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("CLIENT");

        usersRepository.save(user);

        MessageResponseDTO response = new MessageResponseDTO();
        response.setMessage("Registro exitoso");
        return response;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        Optional<Users> user = usersRepository.findByEmail(request.getEmail());

        if (user.isEmpty()) {
            throw new RuntimeException("Este usuario no se encuentra registrado");
        }

        Users userFound = user.get();

        if (!passwordEncoder.matches(request.getPassword(), userFound.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        String jwt = jwtService.generateToken(userFound.getId(), userFound.getEmail(), userFound.getRole());

        LoginResponseDTO response = new LoginResponseDTO();
        response.setMessage("Inicio de sesión exitoso");
        response.setJwt(jwt);
        return response;
    }

    public RefreshTokenResponseDTO refreshToken(String token) {
        String jwt = jwtService.refreshToken(token);
        RefreshTokenResponseDTO response = new RefreshTokenResponseDTO();
        response.setJwt(jwt);
        return response;
    }
}