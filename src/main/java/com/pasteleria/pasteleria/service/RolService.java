package com.pasteleria.pasteleria.service;

import com.pasteleria.pasteleria.model.Rol;
import com.pasteleria.pasteleria.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public Rol crearRol(Rol rol) {
        return rolRepository.save(rol);
    }

    public Optional<Rol> obtenerPorNombre(String nombre) {
        return rolRepository.findByNombre(nombre);
    }

    public Optional<Rol> obtenerPorId(Long id) {
        return rolRepository.findById(id);
    }

    public boolean existePorNombre(String nombre) {
        return rolRepository.findByNombre(nombre).isPresent();
    }
}

