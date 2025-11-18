package com.example.api_desarrolladores.Model;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "citas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===== DATOS VISIBLES PARA TODOS =====
    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCita estado = EstadoCita.PENDIENTE;

    @Column(columnDefinition = "TEXT")
    private String motivoCita;

    @Column(columnDefinition = "TEXT")
    private String mensajeCliente; // Mensaje del usuario al veterinario

    // ===== DATOS SOLO PARA VETERINARIO Y ADMIN =====
    @Column(columnDefinition = "TEXT")
    private String diagnostico;

    @Column(columnDefinition = "TEXT")
    private String tratamiento;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    // ===== DATOS SOLO PARA ADMIN =====
    @Column(columnDefinition = "TEXT")
    private String resenaVeterinario; // Reseña del vet al admin

    // ===== RELACIONES =====
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;

    @ManyToOne
    @JoinColumn(name = "veterinario_id")
    private Veterinario veterinario;

    @ManyToOne
    @JoinColumn(name = "sucursal_id", nullable = false)
    private Sucursal sucursal;

    @OneToOne(mappedBy = "cita", cascade = CascadeType.ALL)
    private Resena resena;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}