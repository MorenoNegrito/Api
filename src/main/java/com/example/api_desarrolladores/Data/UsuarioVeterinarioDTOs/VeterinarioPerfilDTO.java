package com.example.api_desarrolladores.Data.UsuarioVeterinarioDTOs;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VeterinarioPerfilDTO {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String especialidad;
    private String licencia;
    private String sucursalNombre;
    private boolean activo;
}