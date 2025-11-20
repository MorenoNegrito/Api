package com.example.api_desarrolladores.Data.ResponsesGenerales;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstadisticasVeterinarioDTO {
    private long totalCitasCompletadas;
    private long citasPendientes;
    private long citasHoy;
    private double promedioCalificacion;
    private long totalResenas;
}