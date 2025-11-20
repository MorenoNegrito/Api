package com.example.api_desarrolladores.Data.ResenaDTO;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResenaCreateDTO {
    private Long citaId;
    private int estrellas; // 1-5
    private String comentario;
}
