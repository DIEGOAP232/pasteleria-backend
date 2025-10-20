package com.pasteleria.pasteleria.controller;

import com.pasteleria.pasteleria.config.JwtUtil;
import com.pasteleria.pasteleria.dto.LoginRequest;
import com.pasteleria.pasteleria.dto.RegistroRequest;
import com.pasteleria.pasteleria.model.Rol;
import com.pasteleria.pasteleria.model.Usuario;
import com.pasteleria.pasteleria.repository.RolRepository;
import com.pasteleria.pasteleria.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepo;
    private final RolRepository rolRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationController(AuthenticationManager authManager, JwtUtil jwtUtil,
                                    UsuarioRepository usuarioRepo, RolRepository rolRepo,
                                    PasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.usuarioRepo = usuarioRepo;
        this.rolRepo = rolRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest body) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword())
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }

        String token = jwtUtil.generatetoken(body.getUsername());
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistroRequest body) {

        if (usuarioRepo.existsByEmail(body.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuario ya existe");
        }

        Usuario u = new Usuario();
        u.setEmail(body.getUsername());
        u.setContrasena(passwordEncoder.encode(body.getPassword()));

        // Asignar rol por defecto "USER"
        Rol rolUsuario = rolRepo.findByNombre("USER")
                .orElseThrow(() -> new RuntimeException("Rol por defecto no encontrado"));
        u.setRol(rolUsuario);

        usuarioRepo.save(u);

        String token = jwtUtil.generatetoken(u.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "token", token,
                        "user", u.getEmail(),
                        "rol", rolUsuario.getNombre()
                ));
    }
}
