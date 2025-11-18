package com.example.api_desarrolladores.Data.ResponsesGenerales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadisticasVeterinarioDTO {
    private Integer tasaCumplimiento;
    private Integer serviciosCompletados;
    private Integer serviciosCancelados;
    private Double nivelSatisfaccion;
    private Double promedioCalificacion;
    private Integer citasDelMes;
    private Integer citasDelDia;
}