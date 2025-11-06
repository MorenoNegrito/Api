package com.example.api_desarrolladores.Repository;

import com.example.api_desarrolladores.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>
{

    //Metodos personalizadoas
}
