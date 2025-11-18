package com.example.api_desarrolladores.Data.AuthDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterVeterinarioRequest {
    private String nombre;
    private String email;
    private String password;
    private String telefono;
    private String especialidad;
    private String licencia;
}