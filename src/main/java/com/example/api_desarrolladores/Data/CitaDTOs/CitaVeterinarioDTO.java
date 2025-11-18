package com.example.api_desarrolladores.Data.CitaDTOs;

import com.example.api_desarrolladores.Data.MascotaDTOs.MascotaVeterinarioDTO;
import lombok.*;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaVeterinarioDTO {
    private Long id;
    private LocalDateTime fechaHora;
    private String estado;
    private String motivoCita;
    private String mensajeCliente;

    // Datos de diagnóstico
    private String diagnostico;
    private String tratamiento;
    private String observaciones;

    // Info completa de la mascota
    private MascotaVeterinarioDTO mascota;

    // Info del usuario
    private String nombreUsuario;
    private String telefonoUsuario;

    // Info de la sucursal
    private String nombreSucursal;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
