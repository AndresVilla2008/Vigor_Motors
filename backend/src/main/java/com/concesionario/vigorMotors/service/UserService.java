package com.concesionario.vigorMotors.service;

import org.springframework.stereotype.Service;

import com.concesionario.vigorMotors.dto.ChangeRoleRequestDTO;
import com.concesionario.vigorMotors.dto.MessageResponseDTO;
import com.concesionario.vigorMotors.entity.Users;
import com.concesionario.vigorMotors.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;

    public MessageResponseDTO changeRole(ChangeRoleRequestDTO dto) {
        Users user = usersRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("No se encontró ningún usuario con el email " + dto.getEmail()));

        user.setRole(dto.getRole());
        usersRepository.save(user);

        MessageResponseDTO response = new MessageResponseDTO();
        response.setMessage("Rol de " + user.getUsername() + " cambiado a " + dto.getRole() + " correctamente");
        return response;
    }
}