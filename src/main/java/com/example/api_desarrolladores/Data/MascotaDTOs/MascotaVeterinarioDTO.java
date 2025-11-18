package com.example.api_desarrolladores.Data.MascotaDTOs;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaVeterinarioDTO {
    private Long id;
    private String nombre;
    private String especie;
    private String raza;
    private String edad;
    private Double peso;
    private String color;
    private List<String> vacunas;
    private String alergias;

    // Datos adicionales para veterinario
    private String historialMedico;
    private String medicacionActual;
    private String notasVeterinarias;

    // Info del dueño
    private String nombreDueno;
    private String telefonoDueno;

    private LocalDateTime createdAt;
}
