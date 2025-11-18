package com.example.api_desarrolladores.Data.UsuarioVeterinarioDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeterinarioDTO {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String especialidad;
    private String licencia;
    private String nombreSucursal;
    private Double calificacion;
    private Integer serviciosCompletados;
    private Integer serviciosCancelados;
}
