package com.example.demo.domain.ports.out;

import com.example.demo.domain.model.Usuario;
import java.util.Optional;

public interface UsuarioRepositoryPort {
    Optional<Usuario> findById(String id);
}
