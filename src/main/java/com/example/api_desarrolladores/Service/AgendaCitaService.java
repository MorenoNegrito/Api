package com.example.api_desarrolladores.Service;

import com.example.api_desarrolladores.Model.AgendaCita;
import com.example.api_desarrolladores.Repository.AgendaCitaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AgendaCitaService {

    private final AgendaCitaRepository agendaRepository;

    public AgendaCitaService(AgendaCitaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public List<AgendaCita> listarAgendas() {
        return agendaRepository.findAll();
    }

    public Optional<AgendaCita> buscarPorId(Long id) {
        return agendaRepository.findById(id);
    }

    public List<AgendaCita> buscarPorFecha(LocalDate fecha) {
        return agendaRepository.findByFecha(fecha);
    }

    public AgendaCita guardarAgenda(AgendaCita agenda) {
        return agendaRepository.save(agenda);
    }

    public AgendaCita actualizarAgenda(Long id, AgendaCita nueva) {
        return agendaRepository.findById(id).map(agenda -> {
            agenda.setFecha(nueva.getFecha());
            agenda.setHoraInicio(nueva.getHoraInicio());
            agenda.setHoraFin(nueva.getHoraFin());
            agenda.setDisponible(nueva.isDisponible());
            agenda.setObservacion(nueva.getObservacion());
            agenda.setProfesional(nueva.getProfesional());
            return agendaRepository.save(agenda);
        }).orElseThrow(() -> new RuntimeException("Agenda no encontrada con id " + id));
    }

    public void eliminarAgenda(Long id) {
        agendaRepository.deleteById(id);
    }
}