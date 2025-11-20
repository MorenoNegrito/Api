package com.example.api_desarrolladores.Controller;

import com.example.api_desarrolladores.Data.CitaDTOs.CitaCreateDTO;
import com.example.api_desarrolladores.Data.CitaDTOs.CitaUsuarioDTO;
import com.example.api_desarrolladores.Data.ResponsesGenerales.MessageResponse;
import com.example.api_desarrolladores.Service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;

    @PostMapping
    public ResponseEntity<?> crearCita(
            Authentication authentication,
            @RequestBody CitaCreateDTO dto
    ) {
        try {
            String email = authentication.getName();
            CitaUsuarioDTO cita = citaService.crearCita(email, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(cita);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> obtenerCitas(Authentication authentication) {
        try {
            String email = authentication.getName();
            List<CitaUsuarioDTO> citas = citaService.obtenerCitasUsuario(email);
            return ResponseEntity.ok(citas);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCita(
            Authentication authentication,
            @PathVariable Long id
    ) {
        try {
            String email = authentication.getName();
            CitaUsuarioDTO cita = citaService.obtenerCita(email, id);
            return ResponseEntity.ok(cita);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelarCita(
            Authentication authentication,
            @PathVariable Long id
    ) {
        try {
            String email = authentication.getName();
            citaService.cancelarCita(email, id);
            return ResponseEntity.ok(new MessageResponse("Cita cancelada exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }
}