package com.pasteleria.pasteleria.controller;

import com.pasteleria.pasteleria.dto.LoginRequest;
import com.pasteleria.pasteleria.model.Usuario;
import com.pasteleria.pasteleria.repository.RolRepository;
import com.pasteleria.pasteleria.repository.UsuarioRepository;
import com.pasteleria.pasteleria.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationController(
            AuthenticationManager authenticationManager, 
            JwtService jwtService, 
            UsuarioRepository usuarioRepository, 
            RolRepository rolRepository, 
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody LoginRequest request) {
        // 1. Verificar credenciales
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getContraseña())
        );
        
        // 2. Generar el token JWT si la verificación fue exitosa
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtService.generateToken(userDetails);
        
        return ResponseEntity.ok(jwtToken); // Retorna el JWT al frontend
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Usuario nuevoUsuario) {
        if (usuarioRepository.findByEmail(nuevoUsuario.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El email ya está registrado.");
        }
        
        String contrasenaCifrada = passwordEncoder.encode(nuevoUsuario.getContraseña());
        nuevoUsuario.setContraseña(contrasenaCifrada);

        rolRepository.findById(1L).ifPresent(nuevoUsuario::setRol);
        
        usuarioRepository.save(nuevoUsuario);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado con éxito.");
    }
}
