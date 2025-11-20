package com.example.api_desarrolladores.Repository;

import com.example.api_desarrolladores.Model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long> {

    List<Sucursal> findByActivaTrue();  // <-- NOMBRE CORRECTO

    List<Sucursal> findByCiudad(String ciudad);

    List<Sucursal> findByNombreContainingIgnoreCase(String nombre);
}
