package com.example.api_desarrolladores.Repository;

import com.example.api_desarrolladores.Model.Profesional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesionalRepository extends JpaRepository<Profesional, Long> {
}