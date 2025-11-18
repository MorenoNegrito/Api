package com.example.api_desarrolladores.Data.CitaDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaResumenDTO {
    private Long id;
    private String nombreMascota;
    private String raza;
    private String hora; // "09:00"
    private String estado;
    private String alerta; // Si tiene alergias o alertas importantes
}