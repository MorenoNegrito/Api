package com.example.api_desarrolladores.Service;

import com.example.api_desarrolladores.Data.MascotaDTOs.MascotaCreateDTO;
import com.example.api_desarrolladores.Data.MascotaDTOs.MascotaUsuarioDTO;
import com.example.api_desarrolladores.Model.Mascota;
import com.example.api_desarrolladores.Model.Usuario;
import com.example.api_desarrolladores.Repository.MascotaRepository;
import com.example.api_desarrolladores.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MascotaService {

    private final MascotaRepository mascotaRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public MascotaUsuarioDTO registrarMascota(String emailUsuario, MascotaCreateDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Mascota mascota = new Mascota();
        mascota.setNombre(dto.getNombre());
        mascota.setEspecie(dto.getEspecie());
        mascota.setRaza(dto.getRaza());
        mascota.setEdad(dto.getEdad());
        mascota.setPeso(dto.getPeso());
        mascota.setColor(dto.getColor());
        mascota.setVacunas(dto.getVacunas());
        mascota.setAlergias(dto.getAlergias());
        mascota.setUsuario(usuario);

        mascota = mascotaRepository.save(mascota);

        return mapearAMascotaUsuarioDTO(mascota);
    }

    @Transactional(readOnly = true)
    public List<MascotaUsuarioDTO> obtenerMascotas(String emailUsuario) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Mascota> mascotas = mascotaRepository.findByUsuarioId(usuario.getId());

        return mascotas.stream()
                .map(this::mapearAMascotaUsuarioDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MascotaUsuarioDTO obtenerMascota(String emailUsuario, Long mascotaId) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Mascota mascota = mascotaRepository.findByIdAndUsuarioId(mascotaId, usuario.getId())
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        return mapearAMascotaUsuarioDTO(mascota);
    }

    @Transactional
    public MascotaUsuarioDTO actualizarMascota(String emailUsuario, Long mascotaId, MascotaCreateDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Mascota mascota = mascotaRepository.findByIdAndUsuarioId(mascotaId, usuario.getId())
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        if (dto.getNombre() != null) mascota.setNombre(dto.getNombre());
        if (dto.getEspecie() != null) mascota.setEspecie(dto.getEspecie());
        if (dto.getRaza() != null) mascota.setRaza(dto.getRaza());
        if (dto.getEdad() != null) mascota.setEdad(dto.getEdad());
        if (dto.getPeso() != null) mascota.setPeso(dto.getPeso());
        if (dto.getColor() != null) mascota.setColor(dto.getColor());
        if (dto.getVacunas() != null) mascota.setVacunas(dto.getVacunas());
        if (dto.getAlergias() != null) mascota.setAlergias(dto.getAlergias());

        mascota = mascotaRepository.save(mascota);

        return mapearAMascotaUsuarioDTO(mascota);
    }

    @Transactional
    public void eliminarMascota(String emailUsuario, Long mascotaId) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Mascota mascota = mascotaRepository.findByIdAndUsuarioId(mascotaId, usuario.getId())
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        mascotaRepository.delete(mascota);
    }

    private MascotaUsuarioDTO mapearAMascotaUsuarioDTO(Mascota m) {
        MascotaUsuarioDTO dto = new MascotaUsuarioDTO();
        dto.setId(m.getId());
        dto.setNombre(m.getNombre());
        dto.setEspecie(m.getEspecie());
        dto.setRaza(m.getRaza());
        dto.setEdad(m.getEdad());
        dto.setPeso(m.getPeso());
        dto.setColor(m.getColor());
        dto.setVacunas(m.getVacunas());
        dto.setAlergias(m.getAlergias());
        return dto;
    }
}