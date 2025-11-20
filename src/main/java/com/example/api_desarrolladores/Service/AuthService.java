package com.example.api_desarrolladores.Service;



import com.example.api_desarrolladores.Config.JwtUtil;
import com.example.api_desarrolladores.Data.AuthDTOs.LoginRequest;
import com.example.api_desarrolladores.Data.AuthDTOs.LoginResponse;
import com.example.api_desarrolladores.Data.AuthDTOs.RegisterRequest;
import com.example.api_desarrolladores.Model.Rol;
import com.example.api_desarrolladores.Model.Usuario;
import com.example.api_desarrolladores.Model.Veterinario;
import com.example.api_desarrolladores.Repository.UsuarioRepository;
import com.example.api_desarrolladores.Repository.VeterinarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final VeterinarioRepository veterinarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public LoginResponse registrarUsuario(RegisterRequest request) {
        // Validar que el email no exista
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Crear usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setTelefono(request.getTelefono());
        usuario.setDireccion(request.getDireccion());
        usuario.setRol(Rol.USUARIO);

        usuario = usuarioRepository.save(usuario);

        // Generar token
        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol().name());

        return LoginResponse.builder()
                .token(token)
                .userId(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .rol(usuario.getRol().name())
                .build();
    }

    @Transactional(readOnly = true)
    public LoginResponse loginUsuario(LoginRequest request) {
        // Buscar usuario
        Usuario usuario = usuarioRepository.findByEmailAndRol(request.getEmail(), Rol.USUARIO)
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        // Validar contraseña
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        // Generar token
        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol().name());

        return LoginResponse.builder()
                .token(token)
                .userId(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .rol(usuario.getRol().name())
                .build();
    }

    @Transactional(readOnly = true)
    public LoginResponse loginVeterinario(LoginRequest request) {
        // Buscar veterinario directamente (tiene email y password propios)
        Veterinario veterinario = veterinarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        // Validar contraseña
        if (!passwordEncoder.matches(request.getPassword(), veterinario.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        // Generar token
        String token = jwtUtil.generateToken(veterinario.getEmail(), veterinario.getRol().name());

        return LoginResponse.builder()
                .token(token)
                .userId(veterinario.getId())
                .veterinarioId(veterinario.getId())
                .nombre(veterinario.getNombre())
                .email(veterinario.getEmail())
                .rol(veterinario.getRol().name())
                .especialidad(veterinario.getEspecialidad())
                .build();
    }
}