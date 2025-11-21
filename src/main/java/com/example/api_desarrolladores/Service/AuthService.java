package com.example.api_desarrolladores.Service;



import com.example.api_desarrolladores.Config.JwtUtil;
import com.example.api_desarrolladores.Data.AuthDTOs.*;
import com.example.api_desarrolladores.Model.*;
import com.example.api_desarrolladores.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final VeterinarioRepository veterinarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final SucursalRepository sucursalRepository;

    @Transactional
    public LoginResponse register(RegisterRequest request) {
        // Validar si el email ya existe
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Crear usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());  // ← AGREGAR ESTO
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setTelefono(request.getTelefono());
        usuario.setDireccion(request.getDireccion());
        usuario.setRol(Rol.USUARIO);
        usuario.setActivo(true);  // ← AGREGAR ESTO
        usuario.setCreatedAt(LocalDateTime.now());

        usuario = usuarioRepository.save(usuario);

        // Generar token
        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol().name());

        return LoginResponse.builder()
                .token(token)
                .userId(usuario.getId())
                .veterinarioId(null)
                .nombre(usuario.getNombre() + " " + usuario.getApellido())  // ← CAMBIAR ESTO
                .email(usuario.getEmail())
                .rol(usuario.getRol().name())
                .especialidad(null)
                .build();
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        // Intentar buscar como usuario
        var usuarioOpt = usuarioRepository.findByEmail(request.getEmail());
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
                throw new RuntimeException("Credenciales inválidas");
            }

            if (!usuario.getActivo()) {
                throw new RuntimeException("Usuario inactivo");
            }

            String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol().name());

            return LoginResponse.builder()
                    .token(token)
                    .userId(usuario.getId())
                    .veterinarioId(null)
                    .nombre(usuario.getNombre() + " " + usuario.getApellido())
                    .email(usuario.getEmail())
                    .rol(usuario.getRol().name())
                    .especialidad(null)
                    .build();
        }

        // Intentar buscar como veterinario
        var veterinarioOpt = veterinarioRepository.findByEmail(request.getEmail());
        if (veterinarioOpt.isPresent()) {
            Veterinario veterinario = veterinarioOpt.get();

            if (!passwordEncoder.matches(request.getPassword(), veterinario.getPassword())) {
                throw new RuntimeException("Credenciales inválidas");
            }

            String token = jwtUtil.generateToken(veterinario.getEmail(), veterinario.getRol().name());

            return LoginResponse.builder()
                    .token(token)
                    .userId(null)
                    .veterinarioId(veterinario.getId())
                    .nombre(veterinario.getNombre())
                    .email(veterinario.getEmail())
                    .rol(veterinario.getRol().name())
                    .especialidad(veterinario.getEspecialidad())
                    .build();
        }

        throw new RuntimeException("Usuario no encontrado");
    }

    @Transactional
    public LoginResponse registerVeterinario(VeterinarioRegisterRequest request) {
        // Validar si el email ya existe
        if (veterinarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }

        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado como usuario");
        }

        // Buscar sucursal si se proporcionó
        Sucursal sucursal = null;
        if (request.getSucursalId() != null) {
            sucursal = sucursalRepository.findById(request.getSucursalId())
                    .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
        }

        // Crear veterinario
        Veterinario veterinario = new Veterinario();
        veterinario.setNombre(request.getNombre());
        veterinario.setEmail(request.getEmail());
        veterinario.setPassword(passwordEncoder.encode(request.getPassword()));
        veterinario.setTelefono(request.getTelefono());
        veterinario.setEspecialidad(request.getEspecialidad());
        veterinario.setLicencia(request.getLicencia());
        veterinario.setSucursal(sucursal);
        veterinario.setRol(Rol.VETERINARIO);
        veterinario.setCalificacion(0.0);
        veterinario.setServiciosCompletados(0);
        veterinario.setServiciosCancelados(0);
        veterinario.setCreatedAt(LocalDateTime.now());

        veterinario = veterinarioRepository.save(veterinario);

        // Generar token
        String token = jwtUtil.generateToken(veterinario.getEmail(), veterinario.getRol().name());

        return LoginResponse.builder()
                .token(token)
                .userId(null)
                .veterinarioId(veterinario.getId())
                .nombre(veterinario.getNombre())
                .email(veterinario.getEmail())
                .rol(veterinario.getRol().name())
                .especialidad(veterinario.getEspecialidad())
                .build();
    }
}