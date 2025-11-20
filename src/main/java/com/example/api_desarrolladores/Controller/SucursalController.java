package com.example.api_desarrolladores.Controller;

import com.example.api_desarrolladores.Data.ResponsesGenerales.MessageResponse;
import com.example.api_desarrolladores.Data.SucursalDTOs.SucursalConVeterinariosDTO;
import com.example.api_desarrolladores.Data.SucursalDTOs.SucursalDTO;
import com.example.api_desarrolladores.Data.UsuarioVeterinarioDTOs.VeterinarioDTO;
import com.example.api_desarrolladores.Model.Sucursal;
import com.example.api_desarrolladores.Model.Veterinario;
import com.example.api_desarrolladores.Repository.SucursalRepository;
import com.example.api_desarrolladores.Repository.VeterinarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sucursales")
@RequiredArgsConstructor
public class SucursalController {

    private final SucursalRepository sucursalRepository;
    private final VeterinarioRepository veterinarioRepository;

    @GetMapping
    public ResponseEntity<?> obtenerSucursales() {
        try {
            List<Sucursal> sucursales = sucursalRepository.findByActivaTrue();
            List<SucursalDTO> sucursalesDTO = sucursales.stream()
                    .map(this::mapearASucursalDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(sucursalesDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerSucursal(@PathVariable Long id) {
        try {
            Sucursal sucursal = sucursalRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

            List<Veterinario> veterinarios = veterinarioRepository.findActivosBySucursalId(id);

            SucursalConVeterinariosDTO dto = new SucursalConVeterinariosDTO();
            dto.setId(sucursal.getId());
            dto.setNombre(sucursal.getNombre());
            dto.setDireccion(sucursal.getDireccion());
            dto.setTelefono(sucursal.getTelefono());
            dto.setHorarioAtencion(sucursal.getHorarioAtencion());
            dto.setVeterinarios(veterinarios.stream()
                    .map(this::mapearAVeterinarioDTO)
                    .collect(Collectors.toList()));

            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/{id}/veterinarios")
    public ResponseEntity<?> obtenerVeterinariosPorSucursal(@PathVariable Long id) {
        try {
            List<Veterinario> veterinarios = veterinarioRepository.findActivosBySucursalId(id);
            List<VeterinarioDTO> veterinariosDTO = veterinarios.stream()
                    .map(this::mapearAVeterinarioDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(veterinariosDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    private SucursalDTO mapearASucursalDTO(Sucursal s) {
        SucursalDTO dto = new SucursalDTO();
        dto.setId(s.getId());
        dto.setNombre(s.getNombre());
        dto.setDireccion(s.getDireccion());
        dto.setTelefono(s.getTelefono());
        dto.setHorarioAtencion(s.getHorarioAtencion());
        return dto;
    }

    private VeterinarioDTO mapearAVeterinarioDTO(Veterinario v) {
        VeterinarioDTO dto = new VeterinarioDTO();
        dto.setId(v.getId());
        dto.setNombre(v.getNombre());
        dto.setEspecialidad(v.getEspecialidad());
        dto.setCalificacion(v.getCalificacion());
        return dto;
    }
}