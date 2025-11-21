package com.example.api_desarrolladores.Controller;
import com.example.api_desarrolladores.Data.AuthDTOs.LoginRequest;
import com.example.api_desarrolladores.Data.AuthDTOs.LoginResponse;
import com.example.api_desarrolladores.Data.AuthDTOs.RegisterRequest;
import com.example.api_desarrolladores.Data.AuthDTOs.VeterinarioRegisterRequest;
import com.example.api_desarrolladores.Data.ResponsesGenerales.MessageResponse;
import com.example.api_desarrolladores.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            LoginResponse response = authService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping("/veterinario/login")
    public ResponseEntity<?> loginVeterinario(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse(e.getMessage()));
        }
    }


    @PostMapping("/veterinario/register")
    public ResponseEntity<?> registerVeterinario(@RequestBody VeterinarioRegisterRequest request) {
        try {
            LoginResponse response = authService.registerVeterinario(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }
}