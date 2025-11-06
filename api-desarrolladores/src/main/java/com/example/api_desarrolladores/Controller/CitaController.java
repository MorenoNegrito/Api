package com.example.api_desarrolladores.Controller;

import com.example.api_desarrolladores.Model.Cita;
import com.example.api_desarrolladores.Service.CitaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "*") // Permitir conexión desde Android
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    public ResponseEntity<List<Cita>> listar() {
        return ResponseEntity.ok(citaService.listarCitas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cita> obtenerPorId(@PathVariable Long id) {
        return citaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cita> crear(@RequestBody Cita cita) {
        return ResponseEntity.ok(citaService.guardarCita(cita));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cita> actualizar(
            @PathVariable Long id,
            @RequestBody Cita citaActualizada
    ) {
        return ResponseEntity.ok(citaService.actualizarCita(id, citaActualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        citaService.eliminarCita(id);
        return ResponseEntity.noContent().build();
    }
}