package com.example.api_desarrolladores.Model;



import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

// ============================================
// USUARIO (Dueño de mascotas)
// ============================================
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

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String telefono;
    private String direccion;

    @Enumerated(EnumType.STRING)
    private Rol rol = Rol.USUARIO;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Mascota> mascotas;

    @OneToMany(mappedBy = "usuario")
    private List<Cita> citas;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean activo = true;  // Por defecto activo al crear

}
