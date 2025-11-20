package com.example.api_desarrolladores.Repository;


import com.example.api_desarrolladores.Model.Resena;
import com.example.api_desarrolladores.Model.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long> {

    List<Resena> findByVeterinario(Veterinario veterinario);

    List<Resena> findByVeterinarioId(Long veterinarioId);

    @Query("SELECT r FROM Resena r WHERE r.veterinario.id = :veterinarioId ORDER BY r.createdAt DESC")
    List<Resena> findByVeterinarioIdOrderByFechaDesc(@Param("veterinarioId") Long veterinarioId);

    Optional<Resena> findByCitaId(Long citaId);

    boolean existsByCitaId(Long citaId);

    @Query("SELECT AVG(r.estrellas) FROM Resena r WHERE r.veterinario.id = :veterinarioId")
    Double getPromedioCalificacionByVeterinarioId(@Param("veterinarioId") Long veterinarioId);

    @Query("SELECT COUNT(r) FROM Resena r WHERE r.veterinario.id = :veterinarioId")
    long countByVeterinarioId(@Param("veterinarioId") Long veterinarioId);

    @Query("SELECT r FROM Resena r WHERE r.veterinario.id = :veterinarioId AND r.estrellas >= :estrellasMinimas ORDER BY r.createdAt DESC")
    List<Resena> findByVeterinarioIdAndEstrellasGreaterThanEqual(
            @Param("veterinarioId") Long veterinarioId,
            @Param("estrellasMinimas") int estrellasMinimas
    );
}