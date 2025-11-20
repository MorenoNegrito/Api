package com.example.api_desarrolladores.Data.CitaDTOs;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CitaUsuarioDTO {
    private Long id;
    private LocalDateTime fechaHora;
    private String motivo;
    private String estado;
    private String mascotaNombre;
    private String veterinarioNombre;
    private String veterinarioEspecialidad;
    private String sucursalNombre;
}