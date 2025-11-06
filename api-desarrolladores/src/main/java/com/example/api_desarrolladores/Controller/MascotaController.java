package com.example.api_desarrolladores.Controller;


import com.example.api_desarrolladores.Model.Mascota;
import com.example.api_desarrolladores.Service.MascotaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@CrossOrigin(origins = "*") //llamadas de android studio :)
public class MascotaController {

    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService){
        this.mascotaService = mascotaService;
    }
    //Obtener todas las mascotas
    @GetMapping
    public ResponseEntity<List<Mascota>> obtenerTodas(){
        return ResponseEntity.ok(mascotaService.listarMascota());
    }

    //Obtener mascota por id
    @GetMapping("/{id}")
    public  ResponseEntity<Mascota> obtenerPorid(@PathVariable Long id){
        return mascotaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Creaer nueva mascota



}
