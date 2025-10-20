package com.pasteleria.pasteleria.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    // Definimos el filtro principal de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desactiva protección CSRF (para pruebas con Postman)
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Permite acceso libre a todas las rutas
            )
            .formLogin(form -> form.disable()) // Desactiva el formulario de login por defecto
            .httpBasic(basic -> basic.disable()); // Desactiva autenticación básica (opcional)

        return http.build();
    }

    // Encoder de contraseñas (aunque no uses autenticación aún)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager para futuras autenticaciones personalizadas
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
