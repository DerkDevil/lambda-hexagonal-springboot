package com.example.demo.application.usecases;

import com.example.demo.domain.model.Usuario;
import com.example.demo.domain.ports.out.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultarUsuarioUseCase {

    private final UsuarioRepositoryPort repository;

    public Usuario consultarPorId(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));
    }
}
