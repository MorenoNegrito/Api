package com.example.api_desarrolladores.Data.SucursalDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SucursalCreateDTO {
    private String nombre;
    private String direccion;
    private String telefono;
    private String horarioAtencion;
    private String serviciosDisponibles;
    private String ciudad;
}