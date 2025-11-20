package com.example.api_desarrolladores.Data.SucursalDTOs;

import com.example.api_desarrolladores.Data.UsuarioVeterinarioDTOs.VeterinarioDTO;
import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SucursalConVeterinariosDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String horarioAtencion;
    private List<VeterinarioDTO> veterinarios;
}