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
@Table(name = "citas")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario propietario;

    @ManyToOne
    @JoinColumn(name = "mascota_id")
    private Mascota mascota;

    private LocalDate fecha;
    private LocalTime hora;
    private String servicio;

    @ManyToOne
    @JoinColumn(name = "profesional_id")
    private Profesional profesional;

    @JsonProperty("usuarioId")
    public Long getUsuarioId() {
        return propietario != null ? propietario.getId() : null;
    }

    @JsonProperty("usuarioId")
    public void setUsuarioId(Long id) {
        if (id != null) {
            this.propietario = new Usuario();
            this.propietario.setId(id);
        }
    }

    @JsonProperty("mascotaId")
    public Long getMascotaId() {
        return mascota != null ? mascota.getId() : null;
    }

    @JsonProperty("mascotaId")
    public void setMascotaId(Long id) {
        if (id != null) {
            this.mascota = new Mascota();
            this.mascota.setId(id);
        }
    }

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
