package com.example.api_desarrolladores.Controller;

import com.example.api_desarrolladores.Data.CitaDTOs.*;
import com.example.api_desarrolladores.Data.ResponsesGenerales.*;
import com.example.api_desarrolladores.Data.UsuarioVeterinarioDTOs.VeterinarioPerfilDTO;
import com.example.api_desarrolladores.Service.VeterinarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/veterinario")
@RequiredArgsConstructor
public class VeterinarioController {

    private final VeterinarioService veterinarioService;

    @GetMapping("/perfil")
    public ResponseEntity<?> obtenerPerfil(Authentication authentication) {
        try {
            String email = authentication.getName();
            VeterinarioPerfilDTO perfil = veterinarioService.obtenerPerfil(email);
            return ResponseEntity.ok(perfil);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/citas")
    public ResponseEntity<?> obtenerCitas(
            Authentication authentication,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha
    ) {
        try {
            String email = authentication.getName();
            VeterinarioPerfilDTO perfil = veterinarioService.obtenerPerfil(email);
            List<CitaVeterinarioDTO> citas = veterinarioService.obtenerCitas(perfil.getId(), fecha);

            CitasAgendasResponse response = new CitasAgendasResponse();
            response.setCitas(citas);
            response.setTotal(citas.size());

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/citas/{id}")
    public ResponseEntity<?> obtenerDetalleCita(
            Authentication authentication,
            @PathVariable Long id
    ) {
        try {
            String email = authentication.getName();
            VeterinarioPerfilDTO perfil = veterinarioService.obtenerPerfil(email);
            CitaResumenDTO detalle = veterinarioService.obtenerDetalleCita(perfil.getId(), id);
            return ResponseEntity.ok(detalle);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @PutMapping("/citas/{id}/atender")
    public ResponseEntity<?> atenderCita(
            Authentication authentication,
            @PathVariable Long id,
            @RequestBody AtenderCitaDTO dto
    ) {
        try {
            String email = authentication.getName();
            VeterinarioPerfilDTO perfil = veterinarioService.obtenerPerfil(email);
            CitaVeterinarioDTO cita = veterinarioService.atenderCita(perfil.getId(), id, dto);
            return ResponseEntity.ok(cita);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping("/citas/{id}/finalizar")
    public ResponseEntity<?> finalizarCita(
            Authentication authentication,
            @PathVariable Long id,
            @RequestBody FinalizarCitaDTO dto
    ) {
        try {
            String email = authentication.getName();
            VeterinarioPerfilDTO perfil = veterinarioService.obtenerPerfil(email);
            CitaVeterinarioDTO cita = veterinarioService.finalizarCita(perfil.getId(), id, dto);
            return ResponseEntity.ok(cita);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/resenas")
    public ResponseEntity<?> obtenerResenas(Authentication authentication) {
        try {
            String email = authentication.getName();
            VeterinarioPerfilDTO perfil = veterinarioService.obtenerPerfil(email);

            ResenasResponse response = veterinarioService.obtenerResenas(perfil.getId());

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/estadisticas")
    public ResponseEntity<?> obtenerEstadisticas(Authentication authentication) {
        try {
            String email = authentication.getName();
            VeterinarioPerfilDTO perfil = veterinarioService.obtenerPerfil(email);
            EstadisticasVeterinarioDTO estadisticas = veterinarioService.obtenerEstadisticas(perfil.getId());
            return ResponseEntity.ok(estadisticas);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }
}