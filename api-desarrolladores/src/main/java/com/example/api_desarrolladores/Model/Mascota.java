package com.example.api_desarrolladores.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "mascotas")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private int edad;
    private String raza;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario propietario;

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
}
