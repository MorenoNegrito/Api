package com.example.api_desarrolladores.Data.ResponsesGenerales;

import com.example.api_desarrolladores.Data.CitaDTOs.CitaResumenDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitasAgendaResponse {
    private String fecha;
    private List<CitaResumenDTO> citas;
}