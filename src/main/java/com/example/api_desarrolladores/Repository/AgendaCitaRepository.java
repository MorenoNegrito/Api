package com.example.api_desarrolladores.Repository;

import com.example.api_desarrolladores.Model.AgendaCita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AgendaCitaRepository extends JpaRepository<AgendaCita,Long> {
    List<AgendaCita> findByFecha(LocalDate fecha);

}
