package com.example.api_desarrolladores.Service;


import com.example.api_desarrolladores.Model.Cita;
import com.example.api_desarrolladores.Repository.CitaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitaService {

    private final CitaRepository citaRepository;

    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    public List<Cita> listarCitas() {
        return citaRepository.findAll();
    }

    public Optional<Cita> buscarPorId(Long id) {
        return citaRepository.findById(id);
    }

    public Cita guardarCita(Cita cita) {
        return citaRepository.save(cita);
    }

    public Cita actualizarCita(Long id, Cita citaActualizada) {
        return citaRepository.findById(id).map(cita -> {
            cita.setPropietario(citaActualizada.getPropietario());
            cita.setMascota(citaActualizada.getMascota());
            cita.setFecha(citaActualizada.getFecha());
            cita.setHora(citaActualizada.getHora());
            cita.setServicio(citaActualizada.getServicio());
            return citaRepository.save(cita);
        }).orElseThrow(() -> new RuntimeException("Cita no encontrada con id " + id));
    }

    public void eliminarCita(Long id) {
        citaRepository.deleteById(id);
    }
}