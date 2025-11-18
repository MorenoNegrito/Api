package com.example.api_desarrolladores.Data.UsuarioVeterinarioDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    private String rol;
}