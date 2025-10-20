package com.pasteleria.pasteleria.controller;

import com.pasteleria.pasteleria.model.Usuario;
import com.pasteleria.pasteleria.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // -----------------------------
    // RUTAS PARA ADMIN
    // -----------------------------

    // GET /api/usuarios : obtener todos los usuarios
    @GetMapping
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<List<Usuario>> obtenerTodosLosUsuarios() {
        return ResponseEntity.ok(usuarioService.obtenerTodosLosUsuarios());
    }

    // PUT /api/usuarios/{id}/rol : actualizar rol de un usuario
    @PutMapping("/{id}/rol")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<Usuario> actualizarRol(
            @PathVariable Long id,
            @RequestBody String nuevoRol) {
        Usuario usuarioActualizado = usuarioService.actualizarRolUsuario(id, nuevoRol);
        return ResponseEntity.ok(usuarioActualizado);
    }

    // -----------------------------
    // RUTAS PARA USUARIO (CLIENTE)
    // -----------------------------

    // GET /api/usuarios/me : obtener perfil del usuario autenticado
    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('Cliente', 'Administrador')")
    public ResponseEntity<Usuario> obtenerMiPerfil(Authentication authentication) {
        String email = authentication.getName();
        Usuario usuario = usuarioService.obtenerUsuarioPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

    // PUT /api/usuarios/me : actualizar datos básicos del usuario autenticado
    @PutMapping("/me")
    @PreAuthorize("hasAnyAuthority('Cliente', 'Administrador')")
    public ResponseEntity<Usuario> actualizarMisDatos(
            Authentication authentication,
            @RequestBody Usuario datosActualizados) {

        String email = authentication.getName();
        Usuario usuario = usuarioService.obtenerUsuarioPorEmail(email);

        Usuario actualizado = usuarioService.actualizarDatosUsuario(
                usuario.getIdUsuario(),
                datosActualizados.getNombre(),
                datosActualizados.getApellido(),
                datosActualizados.getDireccion(),
                datosActualizados.getTelefono()
        );

        return ResponseEntity.ok(actualizado);
    }
}

