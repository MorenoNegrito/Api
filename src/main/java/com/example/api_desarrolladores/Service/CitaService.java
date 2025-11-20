package com.example.api_desarrolladores.Service;

import com.example.api_desarrolladores.Data.CitaDTOs.CitaCreateDTO;
import com.example.api_desarrolladores.Data.CitaDTOs.CitaUsuarioDTO;
import com.example.api_desarrolladores.Model.*;
import com.example.api_desarrolladores.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CitaService {

    private final CitaRepository citaRepository;
    private final MascotaRepository mascotaRepository;
    private final VeterinarioRepository veterinarioRepository;
    private final SucursalRepository sucursalRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public CitaUsuarioDTO crearCita(String emailUsuario, CitaCreateDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Mascota mascota = mascotaRepository.findByIdAndUsuarioId(dto.getMascotaId(), usuario.getId())
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        Veterinario veterinario = veterinarioRepository.findById(dto.getVeterinarioId())
                .orElseThrow(() -> new RuntimeException("Veterinario no encontrado"));

        Sucursal sucursal = sucursalRepository.findById(dto.getSucursalId())
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

        if (!sucursal.getActiva()) {
            throw new RuntimeException("Sucursal no disponible");
        }

        if (citaRepository.existsByVeterinarioIdAndFechaHora(dto.getVeterinarioId(), dto.getFechaHora())) {
            throw new RuntimeException("El horario ya está ocupado");
        }

        if (dto.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("La fecha de la cita debe ser futura");
        }

        Cita cita = new Cita();
        cita.setUsuario(usuario);
        cita.setMascota(mascota);
        cita.setVeterinario(veterinario);
        cita.setSucursal(sucursal);
        cita.setFechaHora(dto.getFechaHora());
        cita.setMotivoCita(dto.getMotivoCita());
        cita.setMensajeCliente(dto.getMensajeCliente());
        cita.setEstado(EstadoCita.PENDIENTE);

        cita = citaRepository.save(cita);

        return mapearACitaUsuarioDTO(cita);
    }

    @Transactional(readOnly = true)
    public List<CitaUsuarioDTO> obtenerCitasUsuario(String emailUsuario) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Cita> citas = citaRepository.findByUsuarioIdOrderByFechaHoraDesc(usuario.getId());

        return citas.stream()
                .map(this::mapearACitaUsuarioDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CitaUsuarioDTO obtenerCita(String emailUsuario, Long citaId) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Cita cita = citaRepository.findByIdAndUsuarioId(citaId, usuario.getId())
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        return mapearACitaUsuarioDTO(cita);
    }

    @Transactional
    public void cancelarCita(String emailUsuario, Long citaId) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Cita cita = citaRepository.findByIdAndUsuarioId(citaId, usuario.getId())
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        if (cita.getEstado() == EstadoCita.COMPLETADA) {
            throw new RuntimeException("No se puede cancelar una cita completada");
        }

        if (cita.getEstado() == EstadoCita.CANCELADA) {
            throw new RuntimeException("La cita ya está cancelada");
        }

        cita.setEstado(EstadoCita.CANCELADA);
        cita.setUpdatedAt(LocalDateTime.now());

        Veterinario veterinario = cita.getVeterinario();
        if (veterinario != null) {
            veterinario.setServiciosCancelados(veterinario.getServiciosCancelados() + 1);
            veterinarioRepository.save(veterinario);
        }

        citaRepository.save(cita);
    }

    private CitaUsuarioDTO mapearACitaUsuarioDTO(Cita c) {
        CitaUsuarioDTO dto = new CitaUsuarioDTO();
        dto.setId(c.getId());
        dto.setFechaHora(c.getFechaHora());
        dto.setMotivo(c.getMotivoCita());
        dto.setEstado(c.getEstado().name());
        dto.setMascotaNombre(c.getMascota().getNombre());
        dto.setVeterinarioNombre(c.getVeterinario() != null ? c.getVeterinario().getNombre() : "Sin asignar");
        dto.setVeterinarioEspecialidad(c.getVeterinario() != null ? c.getVeterinario().getEspecialidad() : "");
        dto.setSucursalNombre(c.getSucursal().getNombre());
        return dto;
    }
}