package com.example.api_desarrolladores.Data.MascotaDTOs;
import lombok.*;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaCreateDTO {
    private String nombre;
    private String especie;
    private String raza;
    private String edad;
    private Double peso;
    private String color;
    private List<String> vacunas;
    private String alergias;
}