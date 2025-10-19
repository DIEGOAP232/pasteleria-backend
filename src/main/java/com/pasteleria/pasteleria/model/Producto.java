package com.pasteleria.pasteleria.model;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data; // Importación correcta de Lombok

@Entity
@Table(name = "Producto")
@Data // Esto genera automáticamente TODOS los getters y setters
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto; // Es mejor usar solo 'id' para simplificar

    @Column(nullable = false)
    private String nombre;
    private String descripcion;

    @Column(nullable = false)
    private Double precioBase; // Usamos este campo en lugar de solo 'precio'

    @Column(nullable = false)
    private Integer stock; // 💡 ¡Añade este campo para el inventario!

    private String estado;
    
    // ❌ ¡Eliminar los métodos getStock() y setStock() con el error 'UnsupportedOperationException'!
    // Lombok se encarga de generarlos.
}