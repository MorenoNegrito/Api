package com.example.api_desarrolladores.Repository;


import com.example.api_desarrolladores.Model.Sucursal;
import com.example.api_desarrolladores.Model.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {

    Optional<Veterinario> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Veterinario> findBySucursal(Sucursal sucursal);

    List<Veterinario> findBySucursalId(Long sucursalId);

    @Query("SELECT v FROM Veterinario v WHERE v.sucursal.id = :sucursalId")
    List<Veterinario> findActivosBySucursalId(@Param("sucursalId") Long sucursalId);

    Optional<Veterinario> findByEmailAndPassword(String email, String password);
}