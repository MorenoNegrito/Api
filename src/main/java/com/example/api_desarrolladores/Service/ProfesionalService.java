package com.example.api_desarrolladores.Service;

import com.example.api_desarrolladores.Model.Profesional;
import com.example.api_desarrolladores.Repository.ProfesionalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfesionalService {

    private final ProfesionalRepository repository;

    public ProfesionalService(ProfesionalRepository repository) {
        this.repository = repository;
    }

    public Profesional crearProfesional(Profesional profesional) {
        return repository.save(profesional);
    }

    public List<Profesional> listarProfesionales() {
        return repository.findAll();
    }
    public Profesional actualizarProfesional(Long id, Profesional profesionalActualizado) {
        return repository.findById(id)
                .map(profesional -> {
                    profesional.setNombre(profesionalActualizado.getNombre());
                    profesional.setCorreo(profesionalActualizado.getCorreo());
                    profesional.setTelefono(profesionalActualizado.getTelefono());
                    profesional.setEspecialidad(profesionalActualizado.getEspecialidad());
                    return repository.save(profesional);
                })
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado con id " + id));
    }

    public Optional<Profesional> obtenerProfesionalPorId(Long id) {
        return repository.findById(id);
    }

    public void eliminarProfesional(Long id) {
        repository.deleteById(id);
    }
}