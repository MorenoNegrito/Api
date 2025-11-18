package com.example.api_desarrolladores.Data.SucursalDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SucursalDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String horarioAtencion;
    private List<String> servicios;
    private Integer cantidadVeterinarios;
    private Boolean activa;
}