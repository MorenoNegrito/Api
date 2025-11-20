package com.example.api_desarrolladores.Repository;

import com.example.api_desarrolladores.Model.Cita;
import com.example.api_desarrolladores.Model.EstadoCita;
import com.example.api_desarrolladores.Model.Mascota;
import com.example.api_desarrolladores.Model.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    // Citas por mascota
    List<Cita> findByMascota(Mascota mascota);

    List<Cita> findByMascotaId(Long mascotaId);

    // Citas por veterinario
    List<Cita> findByVeterinario(Veterinario veterinario);

    List<Cita> findByVeterinarioId(Long veterinarioId);

    // Citas por usuario (dueño de mascota)
    List<Cita> findByUsuarioId(Long usuarioId);

    @Query("SELECT c FROM Cita c WHERE c.usuario.id = :usuarioId ORDER BY c.fechaHora DESC")
    List<Cita> findByUsuarioIdOrderByFechaHoraDesc(@Param("usuarioId") Long usuarioId);

    // Citas por veterinario y fecha
    @Query("SELECT c FROM Cita c WHERE c.veterinario.id = :veterinarioId " +
            "AND DATE(c.fechaHora) = :fecha " +
            "ORDER BY c.fechaHora ASC")
    List<Cita> findByVeterinarioIdAndFecha(
            @Param("veterinarioId") Long veterinarioId,
            @Param("fecha") LocalDate fecha
    );

    // Citas por veterinario y rango de fechas
    @Query("SELECT c FROM Cita c WHERE c.veterinario.id = :veterinarioId " +
            "AND c.fechaHora BETWEEN :fechaInicio AND :fechaFin " +
            "ORDER BY c.fechaHora ASC")
    List<Cita> findByVeterinarioIdAndFechaHoraBetween(
            @Param("veterinarioId") Long veterinarioId,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );

    // Citas por estado
    List<Cita> findByEstado(EstadoCita estado);

    List<Cita> findByVeterinarioIdAndEstado(Long veterinarioId, EstadoCita estado);

    // Verificar disponibilidad de cita
    @Query("SELECT COUNT(c) > 0 FROM Cita c WHERE c.veterinario.id = :veterinarioId " +
            "AND c.fechaHora = :fechaHora " +
            "AND c.estado != 'CANCELADA'")
    boolean existsByVeterinarioIdAndFechaHora(
            @Param("veterinarioId") Long veterinarioId,
            @Param("fechaHora") LocalDateTime fechaHora
    );

    // Estadísticas del veterinario
    @Query("SELECT COUNT(c) FROM Cita c WHERE c.veterinario.id = :veterinarioId AND c.estado = :estado")
    long countByVeterinarioIdAndEstado(@Param("veterinarioId") Long veterinarioId, @Param("estado") EstadoCita estado);

    @Query("SELECT COUNT(c) FROM Cita c WHERE c.veterinario.id = :veterinarioId " +
            "AND DATE(c.fechaHora) = :fecha")
    long countByVeterinarioIdAndFecha(
            @Param("veterinarioId") Long veterinarioId,
            @Param("fecha") LocalDate fecha
    );

    // Cita específica verificando permisos
    @Query("SELECT c FROM Cita c WHERE c.id = :citaId AND c.veterinario.id = :veterinarioId")
    Optional<Cita> findByIdAndVeterinarioId(@Param("citaId") Long citaId, @Param("veterinarioId") Long veterinarioId);

    @Query("SELECT c FROM Cita c WHERE c.id = :citaId AND c.usuario.id = :usuarioId")
    Optional<Cita> findByIdAndUsuarioId(@Param("citaId") Long citaId, @Param("usuarioId") Long usuarioId);
}