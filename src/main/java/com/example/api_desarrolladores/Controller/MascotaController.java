package com.example.api_desarrolladores.Controller;


import com.example.api_desarrolladores.Model.Mascota;
import com.example.api_desarrolladores.Model.Usuario;
import com.example.api_desarrolladores.Repository.UsuarioRepository;
import com.example.api_desarrolladores.Service.MascotaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/mascotas")
@CrossOrigin(origins = "*")
public class MascotaController {

    private final MascotaService mascotaService;
    private final UsuarioRepository usuarioRepository;

    public MascotaController(MascotaService mascotaService, UsuarioRepository usuarioRepository) {
        this.mascotaService = mascotaService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mascota> obtenerMascotaPorId(@PathVariable Long id) {
        return mascotaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Mascota> crearMascota(@RequestBody Mascota mascota) {
        // Buscar usuario por ID
        if (mascota.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(mascota.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            mascota.setPropietario(usuario);
        }

        Mascota nueva = mascotaService.guardarMascota(mascota);
        return ResponseEntity.ok(nueva);
    }

    @GetMapping
    public List<Mascota> listarMascotas() {
        return mascotaService.obtenerTodas();
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMascota(@PathVariable Long id) {
        mascotaService.eliminarMascota(id);
        return ResponseEntity.noContent().build();
    }


    @RestController
    public class HealthController {
        @GetMapping("/health")
        public String health() {
            return "API funcionando!";
        }
    }

}
