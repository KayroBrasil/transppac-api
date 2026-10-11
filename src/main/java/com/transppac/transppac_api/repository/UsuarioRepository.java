package com.transppac.transppac_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transppac.transppac_api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);
}