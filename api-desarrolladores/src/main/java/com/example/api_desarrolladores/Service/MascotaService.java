package com.example.api_desarrolladores.Service;


import com.example.api_desarrolladores.Model.Mascota;
import com.example.api_desarrolladores.Repository.MascotaRepository;
import jakarta.persistence.Id;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;

    public MascotaService(MascotaRepository mascotaRepository){
        this.mascotaRepository = mascotaRepository;
    }

    //Todas las mosctas
    public List<Mascota> listarMascota(){
        return mascotaRepository.findAll();
    }
    //Buscar mascota por id
    public Optional<Mascota> buscarPorId(Long id){
        return mascotaRepository.findById(id);
    }

    public Mascota guardarMascota( Mascota mascota){
        return mascotaRepository.save(mascota);
    }

    public Mascota actualizarMascota(Long id, Mascota mascotaActualizada){
        return mascotaRepository.findById(id).map(mascota -> {
            mascota.setNombre(mascotaActualizada.getNombre());
            mascota.setEdad(mascotaActualizada.getEdad());
            mascota.setRaza(mascotaActualizada.getRaza());
            mascota.setPropietario(mascotaActualizada.getPropietario());
            return mascotaRepository.save(mascota);
        }).orElseThrow(() -> new RuntimeException("Mascota no encontrada con id" + id));
    }

    public void eliminarMascota(Long id){
        mascotaRepository.deleteById(id);
    }





}
