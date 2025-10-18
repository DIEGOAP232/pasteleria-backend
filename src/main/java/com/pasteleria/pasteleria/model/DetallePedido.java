package com.pasteleria.pasteleria.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "DetallePedido")
@Data
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalle;

    @ManyToOne
    @JoinColumn(name = "pedido_id", referencedColumnName = "idPedido", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "producto_id", referencedColumnName = "idProducto", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private Double precioUnitario; // Precio base + costo de personalización
    
    @Column(nullable = false)
    private Double subtotal; // cantidad * precioUnitario
}