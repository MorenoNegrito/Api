package com.example.api_desarrolladores.Data.AuthDTOs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private Long userId;
    private Long veterinarioId; // Solo para veterinarios
    private String nombre;
    private String email;
    private String rol;
    private String especialidad; // Solo para veterinarios
}
