package com.example.api_desarrolladores.Controller;


import com.example.api_desarrolladores.Data.MascotaDTOs.MascotaCreateDTO;
import com.example.api_desarrolladores.Data.MascotaDTOs.MascotaUsuarioDTO;
import com.example.api_desarrolladores.Data.ResponsesGenerales.MessageResponse;
import com.example.api_desarrolladores.Service.MascotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
@RequiredArgsConstructor
public class MascotaController {

    private final MascotaService mascotaService;

    @PostMapping
    public ResponseEntity<?> registrarMascota(
            Authentication authentication,
            @RequestBody MascotaCreateDTO dto
    ) {
        try {
            String email = authentication.getName();
            MascotaUsuarioDTO mascota = mascotaService.registrarMascota(email, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(mascota);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> obtenerMascotas(Authentication authentication) {
        try {
            String email = authentication.getName();
            List<MascotaUsuarioDTO> mascotas = mascotaService.obtenerMascotas(email);
            return ResponseEntity.ok(mascotas);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerMascota(
            Authentication authentication,
            @PathVariable Long id
    ) {
        try {
            String email = authentication.getName();
            MascotaUsuarioDTO mascota = mascotaService.obtenerMascota(email, id);
            return ResponseEntity.ok(mascota);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarMascota(
            Authentication authentication,
            @PathVariable Long id,
            @RequestBody MascotaCreateDTO dto
    ) {
        try {
            String email = authentication.getName();
            MascotaUsuarioDTO mascota = mascotaService.actualizarMascota(email, id, dto);
            return ResponseEntity.ok(mascota);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMascota(
            Authentication authentication,
            @PathVariable Long id
    ) {
        try {
            String email = authentication.getName();
            mascotaService.eliminarMascota(email, id);
            return ResponseEntity.ok(new MessageResponse("Mascota eliminada exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }
}