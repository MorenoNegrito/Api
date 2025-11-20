package com.example.api_desarrolladores.Model;



import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sucursales")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String direccion;

    private String telefono;
    private String horarioAtencion;

    @Column(columnDefinition = "TEXT")
    private String serviciosDisponibles;

    @OneToMany(mappedBy = "sucursal")
    private List<Veterinario> veterinarios;

    @OneToMany(mappedBy = "sucursal")
    private List<Cita> citas;

    private Boolean activa = true;


    @Column(nullable = false)
    private String ciudad;

}