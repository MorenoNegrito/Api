package com.example.api_desarrolladores.Service;

import com.example.api_desarrolladores.Data.CitaDTOs.AtenderCitaDTO;
import com.example.api_desarrolladores.Data.CitaDTOs.CitaDetalleDTO;
import com.example.api_desarrolladores.Data.CitaDTOs.CitaVeterinarioDTO;
import com.example.api_desarrolladores.Data.CitaDTOs.FinalizarCitaDTO;
import com.example.api_desarrolladores.Data.ResenaDTO.ResenaDTO;
import com.example.api_desarrolladores.Data.ResponsesGenerales.EstadisticasVeterinarioDTO;
import com.example.api_desarrolladores.Data.UsuarioVeterinarioDTOs.VeterinarioPerfilDTO;
import com.example.api_desarrolladores.Model.*;
import com.example.api_desarrolladores.Repository.CitaRepository;
import com.example.api_desarrolladores.Repository.ResenaRepository;
import com.example.api_desarrolladores.Repository.VeterinarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;
    private final CitaRepository citaRepository;
    private final ResenaRepository resenaRepository;

    @Transactional(readOnly = true)
    public VeterinarioPerfilDTO obtenerPerfil(String email) {
        Veterinario veterinario = veterinarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Veterinario no encontrado"));

        return mapearAPerfilDTO(veterinario);
    }

    @Transactional(readOnly = true)
    public List<CitaVeterinarioDTO> obtenerCitas(Long veterinarioId, LocalDate fecha) {
        List<Cita> citas;

        if (fecha != null) {
            citas = citaRepository.findByVeterinarioIdAndFecha(veterinarioId, fecha);
        } else {
            citas = citaRepository.findByVeterinarioId(veterinarioId);
        }

        return citas.stream()
                .map(this::mapearACitaVeterinarioDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CitaDetalleDTO obtenerDetalleCita(Long veterinarioId, Long citaId) {
        Cita cita = citaRepository.findByIdAndVeterinarioId(citaId, veterinarioId)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada o no tiene permiso"));

        return mapearACitaDetalleDTO(cita);
    }

    @Transactional
    public CitaVeterinarioDTO atenderCita(Long veterinarioId, Long citaId, AtenderCitaDTO dto) {
        Cita cita = citaRepository.findByIdAndVeterinarioId(citaId, veterinarioId)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada o no tiene permiso"));

        if (cita.getEstado() != EstadoCita.PENDIENTE && cita.getEstado() != EstadoCita.CONFIRMADA) {
            throw new RuntimeException("La cita no está en estado PENDIENTE o CONFIRMADA");
        }

        cita.setEstado(EstadoCita.EN_CURSO);
        cita.setDiagnostico(dto.getDiagnostico());
        cita.setTratamiento(dto.getTratamiento());
        cita.setObservaciones(dto.getObservaciones());
        cita.setUpdatedAt(LocalDateTime.now());

        cita = citaRepository.save(cita);

        return mapearACitaVeterinarioDTO(cita);
    }

    @Transactional
    public CitaVeterinarioDTO finalizarCita(Long veterinarioId, Long citaId, FinalizarCitaDTO dto) {
        Cita cita = citaRepository.findByIdAndVeterinarioId(citaId, veterinarioId)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada o no tiene permiso"));

        if (cita.getEstado() != EstadoCita.EN_CURSO) {
            throw new RuntimeException("La cita no está en curso");
        }

        cita.setEstado(EstadoCita.COMPLETADA);
        cita.setDiagnostico(dto.getDiagnostico());
        cita.setTratamiento(dto.getTratamiento());
        cita.setObservaciones(dto.getObservaciones());
        cita.setUpdatedAt(LocalDateTime.now());

        // Actualizar historial médico de la mascota
        Mascota mascota = cita.getMascota();
        String nuevoHistorial = String.format(
                "[%s] Diagnóstico: %s | Tratamiento: %s",
                LocalDateTime.now(),
                dto.getDiagnostico(),
                dto.getTratamiento()
        );

        if (mascota.getHistorialMedico() != null && !mascota.getHistorialMedico().isEmpty()) {
            mascota.setHistorialMedico(mascota.getHistorialMedico() + "\n" + nuevoHistorial);
        } else {
            mascota.setHistorialMedico(nuevoHistorial);
        }

        // Actualizar estadísticas del veterinario
        Veterinario veterinario = cita.getVeterinario();
        veterinario.setServiciosCompletados(veterinario.getServiciosCompletados() + 1);
        veterinarioRepository.save(veterinario);

        cita = citaRepository.save(cita);

        return mapearACitaVeterinarioDTO(cita);
    }

    @Transactional(readOnly = true)
    public List<ResenaDTO> obtenerResenas(Long veterinarioId) {
        List<Resena> resenas = resenaRepository.findByVeterinarioIdOrderByFechaDesc(veterinarioId);

        return resenas.stream()
                .map(this::mapearAResenaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EstadisticasVeterinarioDTO obtenerEstadisticas(Long veterinarioId) {
        long totalCitas = citaRepository.countByVeterinarioIdAndEstado(veterinarioId, EstadoCita.COMPLETADA);
        long citasPendientes = citaRepository.countByVeterinarioIdAndEstado(veterinarioId, EstadoCita.PENDIENTE);
        long citasHoy = citaRepository.countByVeterinarioIdAndFecha(veterinarioId, LocalDate.now());

        Double promedioCalificacion = resenaRepository.getPromedioCalificacionByVeterinarioId(veterinarioId);
        long totalResenas = resenaRepository.countByVeterinarioId(veterinarioId);

        return EstadisticasVeterinarioDTO.builder()
                .totalCitasCompletadas(totalCitas)
                .citasPendientes(citasPendientes)
                .citasHoy(citasHoy)
                .promedioCalificacion(promedioCalificacion != null ? promedioCalificacion : 0.0)
                .totalResenas(totalResenas)
                .build();
    }

    // Métodos de mapeo
    private VeterinarioPerfilDTO mapearAPerfilDTO(Veterinario v) {
        return VeterinarioPerfilDTO.builder()
                .id(v.getId())
                .nombre(v.getNombre())
                .email(v.getEmail())
                .telefono(v.getTelefono())
                .especialidad(v.getEspecialidad())
                .licencia(v.getLicencia())
                .sucursalNombre(v.getSucursal() != null ? v.getSucursal().getNombre() : null)
                .activo(true)
                .build();
    }

    private CitaVeterinarioDTO mapearACitaVeterinarioDTO(Cita c) {
        return CitaVeterinarioDTO.builder()
                .id(c.getId())
                .fechaHora(c.getFechaHora())
                .motivo(c.getMotivoCita())
                .estado(c.getEstado().name())
                .diagnostico(c.getDiagnostico())
                .tratamiento(c.getTratamiento())
                .observaciones(c.getObservaciones())
                .mascotaNombre(c.getMascota().getNombre())
                .mascotaRaza(c.getMascota().getRaza())
                .mascotaEdad(c.getMascota().getEdad())
                .duenoNombre(c.getUsuario().getNombre())
                .duenoTelefono(c.getUsuario().getTelefono())
                .build();
    }

    private CitaDetalleDTO mapearACitaDetalleDTO(Cita c) {
        Mascota m = c.getMascota();

        return CitaDetalleDTO.builder()
                .id(c.getId())
                .fechaHora(c.getFechaHora())
                .motivo(c.getMotivoCita())
                .estado(c.getEstado().name())
                .diagnostico(c.getDiagnostico())
                .tratamiento(c.getTratamiento())
                .observaciones(c.getObservaciones())
                .mascotaId(m.getId())
                .mascotaNombre(m.getNombre())
                .mascotaRaza(m.getRaza())
                .mascotaEdad(m.getEdad())
                .mascotaSexo(m.getColor()) // Ajusta según tu modelo
                .mascotaPeso(m.getPeso())
                .mascotaVacunas(m.getVacunas())
                .mascotaHistorialMedico(m.getHistorialMedico())
                .mascotaMedicacionActual(m.getMedicacionActual())
                .mascotaNotasVeterinarias(m.getNotasVeterinarias())
                .duenoNombre(m.getUsuario().getNombre())
                .duenoTelefono(m.getUsuario().getTelefono())
                .duenoEmail(m.getUsuario().getEmail())
                .build();
    }

    private ResenaDTO mapearAResenaDTO(Resena r) {
        return ResenaDTO.builder()
                .id(r.getId())
                .calificacion(r.getEstrellas())
                .comentario(r.getComentario())
                .fecha(r.getCreatedAt())
                .nombreCliente(r.getUsuario().getNombre())
                .nombreMascota(r.getCita().getMascota().getNombre())
                .build();
    }
}