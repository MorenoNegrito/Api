package com.example.api_desarrolladores.Data.ResenaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResenaDTO {
    private Long id;
    private String nombreCliente;
    private String comentario;
    private Integer estrellas;
    private String fecha; // Formateado: "Hace 2 días"
}