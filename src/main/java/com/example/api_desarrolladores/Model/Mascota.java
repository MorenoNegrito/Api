package com.example.api_desarrolladores.Model;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "mascotas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===== DATOS VISIBLES PARA USUARIO Y VETERINARIO =====
    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String especie; // Perro, Gato, etc.

    private String raza;
    private String edad;
    private Double peso;
    private String color;

    @Column(columnDefinition = "TEXT")
    private String vacunas; // JSON array como String

    @Column(columnDefinition = "TEXT")
    private String alergias;

    // ===== DATOS SOLO VISIBLES PARA VETERINARIO =====
    @Column(columnDefinition = "TEXT")
    private String historialMedico;

    @Column(columnDefinition = "TEXT")
    private String medicacionActual;

    @Column(columnDefinition = "TEXT")
    private String notasVeterinarias;

    // ===== RELACIONES =====
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "mascota")
    private List<Cita> citas;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
