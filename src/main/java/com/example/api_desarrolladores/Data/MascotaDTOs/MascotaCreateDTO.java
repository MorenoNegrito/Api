package com.example.api_desarrolladores.Data.MascotaDTOs;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MascotaCreateDTO {
    private String nombre;
    private String especie;
    private String raza;
    private String edad;
    private Double peso;
    private String color;
    private String vacunas;
    private String alergias;
}
