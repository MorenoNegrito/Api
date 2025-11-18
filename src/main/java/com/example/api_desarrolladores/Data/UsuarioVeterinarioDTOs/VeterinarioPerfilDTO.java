package com.example.api_desarrolladores.Data.UsuarioVeterinarioDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeterinarioPerfilDTO {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String especialidad;
    private String veterinaria;
    private String sucursal;
    private Double calificacion;
    private Integer tasaCumplimiento;
    private Integer serviciosCompletados;
    private Integer serviciosCancelados;
    private Double nivelSatisfaccion;
}