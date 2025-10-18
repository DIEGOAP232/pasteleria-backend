package com.pasteleria.pasteleria.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "Pago")
@Data
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

    @OneToOne
    @JoinColumn(name = "pedido_id", referencedColumnName = "idPedido", nullable = false)
    private Pedido pedido;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false)
    private String metodo; // Ej: "Tarjeta", "Efectivo"
    
    @Column(nullable = false)
    private String estado; // Ej: "Completado", "Pendiente"

    @Column(nullable = false)
    private LocalDateTime fecha;
}
