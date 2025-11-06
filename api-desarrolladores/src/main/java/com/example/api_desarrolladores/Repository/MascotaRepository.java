package com.example.api_desarrolladores.Repository;

import com.example.api_desarrolladores.Model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    //Consultas Personalizadas en caso de
}
