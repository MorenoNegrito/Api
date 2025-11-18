package com.example.api_desarrolladores.Data.CitaDTOs;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaUsuarioDTO {
    private Long id;
    private LocalDateTime fechaHora;
    private String estado;
    private String motivoCita;
    private String mensajeCliente;

    // Info de la mascota
    private String nombreMascota;
    private String razaMascota;

    // Info de la sucursal
    private String nombreSucursal;
    private String direccionSucursal;

    // Info del veterinario
    private String nombreVeterinario;
    private String especialidadVeterinario;

    private LocalDateTime createdAt;
}
