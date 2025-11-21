package com.example.api_desarrolladores.Controller;


import com.example.api_desarrolladores.Data.ResponsesGenerales.MessageResponse;
import com.example.api_desarrolladores.Data.SucursalDTOs.SucursalCreateDTO;
import com.example.api_desarrolladores.Data.SucursalDTOs.SucursalDTO;
import com.example.api_desarrolladores.Model.Sucursal;
import com.example.api_desarrolladores.Repository.SucursalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final SucursalRepository sucursalRepository;

    @PostMapping("/sucursales")
    public ResponseEntity<?> crearSucursal(@RequestBody SucursalCreateDTO dto) {
        try {
            Sucursal sucursal = new Sucursal();
            sucursal.setNombre(dto.getNombre());
            sucursal.setDireccion(dto.getDireccion());
            sucursal.setTelefono(dto.getTelefono());
            sucursal.setHorarioAtencion(dto.getHorarioAtencion());
            sucursal.setServiciosDisponibles(dto.getServiciosDisponibles());
            sucursal.setCiudad(dto.getCiudad());
            sucursal.setActiva(true);

            sucursal = sucursalRepository.save(sucursal);

            SucursalDTO response = mapearASucursalDTO(sucursal);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/sucursales")
    public ResponseEntity<?> obtenerTodasSucursales() {
        try {
            List<Sucursal> sucursales = sucursalRepository.findAll();
            List<SucursalDTO> sucursalesDTO = sucursales.stream()
                    .map(this::mapearASucursalDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(sucursalesDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @PutMapping("/sucursales/{id}")
    public ResponseEntity<?> actualizarSucursal(
            @PathVariable Long id,
            @RequestBody SucursalCreateDTO dto
    ) {
        try {
            Sucursal sucursal = sucursalRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

            sucursal.setNombre(dto.getNombre());
            sucursal.setDireccion(dto.getDireccion());
            sucursal.setTelefono(dto.getTelefono());
            sucursal.setHorarioAtencion(dto.getHorarioAtencion());
            sucursal.setServiciosDisponibles(dto.getServiciosDisponibles());
            sucursal.setCiudad(dto.getCiudad());

            sucursal = sucursalRepository.save(sucursal);

            SucursalDTO response = mapearASucursalDTO(sucursal);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/sucursales/{id}")
    public ResponseEntity<?> desactivarSucursal(@PathVariable Long id) {
        try {
            Sucursal sucursal = sucursalRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

            sucursal.setActiva(false);
            sucursalRepository.save(sucursal);

            return ResponseEntity.ok(new MessageResponse("Sucursal desactivada exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    private SucursalDTO mapearASucursalDTO(Sucursal s) {
        return SucursalDTO.builder()
                .id(s.getId())
                .nombre(s.getNombre())
                .direccion(s.getDireccion())
                .telefono(s.getTelefono())
                .horarioAtencion(s.getHorarioAtencion())
                .build();
    }
}