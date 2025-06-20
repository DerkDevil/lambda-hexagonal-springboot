package com.example.demo.infrastructure.adapters.out.database;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUsuarioRepository extends JpaRepository<UsuarioEntity, String> {
}
