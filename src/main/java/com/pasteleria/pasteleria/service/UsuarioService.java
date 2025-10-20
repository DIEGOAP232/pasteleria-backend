package com.pasteleria.pasteleria.service;

import com.pasteleria.pasteleria.model.Usuario;
import com.pasteleria.pasteleria.model.Rol;
import com.pasteleria.pasteleria.repository.UsuarioRepository;
import com.pasteleria.pasteleria.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    // Obtener todos los usuarios (solo admin)
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    // Obtener un usuario por email
    public Usuario obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + email));
    }

    // Actualizar rol de un usuario (solo admin)
    @Transactional
    public Usuario actualizarRolUsuario(Long usuarioId, String nombreRol) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Rol rol = rolRepository.findByNombre(nombreRol)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + nombreRol));

        usuario.setRol(rol);
        return usuarioRepository.save(usuario);
    }

    // Actualizar datos básicos de usuario (cliente)
    @Transactional
    public Usuario actualizarDatosUsuario(Long usuarioId, String nombre, String apellido, String direccion, String telefono) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setDireccion(direccion);
        usuario.setTelefono(telefono);

        return usuarioRepository.save(usuario);
    }
}
