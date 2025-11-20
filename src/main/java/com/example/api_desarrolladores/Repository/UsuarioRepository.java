package com.example.api_desarrolladores.Repository;


import com.example.api_desarrolladores.Model.Rol;
import com.example.api_desarrolladores.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByEmailAndRol(String email, Rol rol);

    boolean existsByEmail(String email);

    boolean existsByEmailAndRol(String email, Rol rol);
}