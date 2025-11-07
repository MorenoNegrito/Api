package com.example.api_desarrolladores.Controller;

import com.example.api_desarrolladores.Model.Profesional;
import com.example.api_desarrolladores.Service.ProfesionalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profesionales")
@CrossOrigin(origins = "*")
public class ProfesionalController {

    private final ProfesionalService service;

    public ProfesionalController(ProfesionalService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Profesional> crearProfesional(@RequestBody Profesional profesional) {
        Profesional creado = service.crearProfesional(profesional);
        return ResponseEntity.ok(creado);
    }


    @GetMapping
    public ResponseEntity<List<Profesional>> listarProfesionales() {
        return ResponseEntity.ok(service.listarProfesionales());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profesional> actualizarProfesional(
            @PathVariable Long id,
            @RequestBody Profesional profesionalActualizado) {

        try {
            Profesional actualizado = service.actualizarProfesional(id, profesionalActualizado);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Profesional> obtenerProfesional(@PathVariable Long id) {
        return service.obtenerProfesionalPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProfesional(@PathVariable Long id) {
        service.eliminarProfesional(id);
        return ResponseEntity.noContent().build();
    }


}