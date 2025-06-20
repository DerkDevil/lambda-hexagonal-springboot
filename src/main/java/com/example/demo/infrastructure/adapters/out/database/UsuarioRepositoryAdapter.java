package com.example.demo.infrastructure.adapters.out.database;

import com.example.demo.domain.model.Usuario;
import com.example.demo.domain.ports.out.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final JpaUsuarioRepository jpa;

    @Override
    public Optional<Usuario> findById(String id) {
        return jpa.findById(id)
                .map(e -> new Usuario(e.getId(), e.getNombre()));
    }
}
