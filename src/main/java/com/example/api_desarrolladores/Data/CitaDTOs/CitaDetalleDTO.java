package com.example.api_desarrolladores.Data.CitaDTOs;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CitaDetalleDTO {
    private Long id;
    private LocalDateTime fechaHora;
    private String motivo;
    private String estado;
    private String diagnostico;
    private String tratamiento;
    private String observaciones;

    // Datos de la mascota
    private Long mascotaId;
    private String mascotaNombre;
    private String mascotaRaza;
    private String mascotaEdad;
    private String mascotaSexo;
    private Double mascotaPeso;
    private String mascotaVacunas;
    private String mascotaHistorialMedico;
    private String mascotaMedicacionActual;
    private String mascotaNotasVeterinarias;

    // Datos del dueño
    private String duenoNombre;
    private String duenoTelefono;
    private String duenoEmail;
}