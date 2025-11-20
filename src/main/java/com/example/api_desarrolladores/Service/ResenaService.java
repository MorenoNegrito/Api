package com.example.api_desarrolladores.Service;

import com.example.api_desarrolladores.Data.ResenaDTO.ResenaCreateDTO;
import com.example.api_desarrolladores.Data.ResenaDTO.ResenaDTO;
import com.example.api_desarrolladores.Model.*;
import com.example.api_desarrolladores.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResenaService {

    private final ResenaRepository resenaRepository;
    private final CitaRepository citaRepository;
    private final UsuarioRepository usuarioRepository;
    private final VeterinarioRepository veterinarioRepository;

    @Transactional
    public ResenaDTO crearResena(String emailUsuario, ResenaCreateDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Cita cita = citaRepository.findByIdAndUsuarioId(dto.getCitaId(), usuario.getId())
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        if (cita.getEstado() != EstadoCita.COMPLETADA) {
            throw new RuntimeException("Solo se pueden reseñar citas completadas");
        }

        if (resenaRepository.existsByCitaId(cita.getId())) {
            throw new RuntimeException("Esta cita ya tiene una reseña");
        }

        if (dto.getEstrellas() < 1 || dto.getEstrellas() > 5) {
            throw new RuntimeException("La calificación debe estar entre 1 y 5 estrellas");
        }

        Resena resena = new Resena();
        resena.setCita(cita);
        resena.setUsuario(usuario);
        resena.setVeterinario(cita.getVeterinario());
        resena.setEstrellas(dto.getEstrellas());
        resena.setComentario(dto.getComentario());

        resena = resenaRepository.save(resena);

        actualizarCalificacionVeterinario(cita.getVeterinario().getId());

        return mapearAResenaDTO(resena);
    }

    @Transactional(readOnly = true)
    public List<ResenaDTO> obtenerResenasPorVeterinario(Long veterinarioId) {
        List<Resena> resenas = resenaRepository.findByVeterinarioIdOrderByFechaDesc(veterinarioId);

        return resenas.stream()
                .map(this::mapearAResenaDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    private void actualizarCalificacionVeterinario(Long veterinarioId) {
        Double promedioCalificacion = resenaRepository.getPromedioCalificacionByVeterinarioId(veterinarioId);

        if (promedioCalificacion != null) {
            Veterinario veterinario = veterinarioRepository.findById(veterinarioId)
                    .orElseThrow(() -> new RuntimeException("Veterinario no encontrado"));

            veterinario.setCalificacion(promedioCalificacion);
            veterinarioRepository.save(veterinario);
        }
    }

    private ResenaDTO mapearAResenaDTO(Resena r) {
        ResenaDTO dto = new ResenaDTO();
        dto.setId(r.getId());
        dto.setCalificacion(r.getEstrellas());
        dto.setComentario(r.getComentario());
        dto.setFecha(r.getCreatedAt());
        dto.setNombreCliente(r.getUsuario().getNombre());
        dto.setNombreMascota(r.getCita().getMascota().getNombre());
        return dto;
    }
}