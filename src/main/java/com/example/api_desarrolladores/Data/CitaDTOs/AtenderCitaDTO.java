package com.example.api_desarrolladores.Data.CitaDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtenderCitaDTO {
    private String diagnostico;
    private String tratamiento;
    private String observaciones;
}
