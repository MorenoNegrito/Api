package com.example.api_desarrolladores.Data.SucursalDTOs;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VeterinarioInfoDTO {
    private Long id;
    private String nombre;
    private String especialidad;
}