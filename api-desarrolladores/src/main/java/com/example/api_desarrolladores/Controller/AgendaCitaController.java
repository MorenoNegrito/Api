package com.example.api_desarrolladores.Controller;


import com.example.api_desarrolladores.Model.AgendaCita;
import com.example.api_desarrolladores.Service.AgendaCitaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/agendas")
@CrossOrigin(origins = "*")
public class AgendaCitaController {

    private final AgendaCitaService agendaService;

    public AgendaCitaController(AgendaCitaService agendaService) {
        this.agendaService = agendaService;
    }

    @GetMapping
    public ResponseEntity<List<AgendaCita>> listar() {
        return ResponseEntity.ok(agendaService.listarAgendas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendaCita> obtenerPorId(@PathVariable Long id) {
        return agendaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<AgendaCita>> obtenerPorFecha(@PathVariable String fecha) {
        LocalDate fechaBuscada = LocalDate.parse(fecha);
        return ResponseEntity.ok(agendaService.buscarPorFecha(fechaBuscada));
    }

    @PostMapping
    public ResponseEntity<AgendaCita> crear(@RequestBody AgendaCita agenda) {
        return ResponseEntity.ok(agendaService.guardarAgenda(agenda));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendaCita> actualizar(@PathVariable Long id, @RequestBody AgendaCita agenda) {
        return ResponseEntity.ok(agendaService.actualizarAgenda(id, agenda));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        agendaService.eliminarAgenda(id);
        return ResponseEntity.noContent().build();
    }
}
