package com.example.api_desarrolladores.Controller;

import com.example.api_desarrolladores.Data.ResenaDTO.ResenaCreateDTO;
import com.example.api_desarrolladores.Data.ResenaDTO.ResenaDTO;
import com.example.api_desarrolladores.Data.ResponsesGenerales.MessageResponse;
import com.example.api_desarrolladores.Service.ResenaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resenas")
@RequiredArgsConstructor
public class ResenaController {

    private final ResenaService resenaService;

    @PostMapping
    public ResponseEntity<?> crearResena(
            Authentication authentication,
            @RequestBody ResenaCreateDTO dto
    ) {
        try {
            String email = authentication.getName();
            ResenaDTO resena = resenaService.crearResena(email, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(resena);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/veterinario/{veterinarioId}")
    public ResponseEntity<?> obtenerResenasPorVeterinario(@PathVariable Long veterinarioId) {
        try {
            List<ResenaDTO> resenas = resenaService.obtenerResenasPorVeterinario(veterinarioId);
            return ResponseEntity.ok(resenas);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }
}