package com.example.api_desarrolladores.Data.ResponsesGenerales;

import com.example.api_desarrolladores.Data.ResenaDTO.ResenaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResenasResponse {
    private List<ResenaDTO> resenas;
    private Double promedioCalificacion;
    private Integer totalResenas;
}
