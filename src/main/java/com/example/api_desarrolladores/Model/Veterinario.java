package com.example.api_desarrolladores.Model;



import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "veterinarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Veterinario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String telefono;
    private String especialidad;

    @Column(unique = true)
    private String licencia;

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;

    @Enumerated(EnumType.STRING)
    private Rol rol = Rol.VETERINARIO;

    @Column(name = "calificacion")
    private Double calificacion = 0.0;

    @Column(name = "servicios_completados")
    private Integer serviciosCompletados = 0;

    @Column(name = "servicios_cancelados")
    private Integer serviciosCancelados = 0;

    @OneToMany(mappedBy = "veterinario")
    private List<Cita> citas;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}