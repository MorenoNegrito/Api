package com.example.api_desarrolladores.Data.ResponsesGenerales;

import com.example.api_desarrolladores.Data.CitaDTOs.CitaVeterinarioDTO;
import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CitasAgendasResponse {
    private List<CitaVeterinarioDTO> citas;
    private int total;
}