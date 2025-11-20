package com.example.api_desarrolladores.Repository;

import com.example.api_desarrolladores.Model.Mascota;
import com.example.api_desarrolladores.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {

    List<Mascota> findByUsuario(Usuario usuario);

    List<Mascota> findByUsuarioId(Long usuarioId);

    @Query("SELECT m FROM Mascota m WHERE m.id = :mascotaId AND m.usuario.id = :usuarioId")
    Optional<Mascota> findByIdAndUsuarioId(@Param("mascotaId") Long mascotaId, @Param("usuarioId") Long usuarioId);

    List<Mascota> findByRazaContainingIgnoreCase(String raza);

    List<Mascota> findByEspecieContainingIgnoreCase(String especie);

    long countByUsuarioId(Long usuarioId);
}