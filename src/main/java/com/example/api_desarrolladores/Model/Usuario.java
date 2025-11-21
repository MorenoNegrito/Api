package com.example.api_desarrolladores.Model;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)  // ← AGREGAR ESTO
    private String apellido;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String telefono;
    private String direccion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol = Rol.USUARIO;

    @Column(nullable = false)  // ← AGREGAR ESTO
    private Boolean activo = true;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Mascota> mascotas;

    @OneToMany(mappedBy = "usuario")
    private List<Cita> citas;

    @OneToMany(mappedBy = "usuario")  // ← AGREGAR ESTO
    private List<Resena> resenas;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}