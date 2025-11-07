package com.example.api_desarrolladores.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "agenda_citas")
public class AgendaCita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private boolean disponible = true;
    private String observacion;

    @ManyToOne
    @JoinColumn(name = "profesional_id")
    private Profesional profesional;

    @JsonProperty("profesionalId")
    public Long getProfesionalId() {
        return profesional != null ? profesional.getId() : null;
    }

    @JsonProperty("profesionalId")
    public void setProfesionalId(Long id) {
        if (id != null) {
            this.profesional = new Profesional();
            this.profesional.setId(id);
        }
    }
}
