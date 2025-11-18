package com.example.api_desarrolladores.Data.CitaDTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CitaCreateDTO {
    private Long mascotaId;
    private Long sucursalId;
    private Long veterinarioId; // Opcional, puede ser asignado por el sistema
    private LocalDateTime fechaHora;
    private String motivoCita;
    private String mensajeCliente;
}