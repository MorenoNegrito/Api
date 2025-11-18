package com.example.api_desarrolladores.Data.ResenaDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ResenaCreateDTO {
    private Long citaId;
    private Integer estrellas;
    private String comentario;
}
