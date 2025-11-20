package com.example.api_desarrolladores.Data.ResenaDTO;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResenaDTO {
    private Long id;
    private int calificacion; // 1-5
    private String comentario;
    private LocalDateTime fecha;
    private String nombreCliente;
    private String nombreMascota;
}
