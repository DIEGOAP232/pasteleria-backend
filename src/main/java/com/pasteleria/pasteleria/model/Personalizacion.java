package com.pasteleria.pasteleria.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Personalizacion")
@Data
public class Personalizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPersonalizacion;

    @Column(nullable = false)
    private String nombre; // Ej: Sabor: Moka, Color: Rojo, Mensaje: "Feliz Cumpleaños"

    private String tipo; // Ej: Sabor, Color, Adicional, etc.
    
    @Column(nullable = false)
    private Double costoExtra; // El costo que se suma al precioBase del producto
    
    private String estado; // Para marcar si la personalización está disponible o no

    // Nota: Aunque esta entidad podría tener una relación con Producto, 
    // para simplificar el backend actual, la trataremos como una opción universal 
    // a menos que tu base de datos indique lo contrario.
}
