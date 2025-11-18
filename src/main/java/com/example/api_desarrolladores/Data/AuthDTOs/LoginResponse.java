package com.example.api_desarrolladores.Data.AuthDTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String tipo = "Bearer";
    private Long id;
    private String nombre;
    private String email;
    private String rol;
}