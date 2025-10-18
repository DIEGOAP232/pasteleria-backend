package com.pasteleria.pasteleria.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Rol")

public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long idRol;
    @Column(nullable = false, unique = true)
    private String nombre;
    public String getNombre() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNombre'");
    }

    // Getters y Setters
    
    
}
