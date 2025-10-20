package com.pasteleria.pasteleria.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Pedido")
@Data
public class Pedido {

    private List<DetallePedido> detalles;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "idUsuario", nullable = false)
    private Usuario usuario; // Cliente que hizo el pedido

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    private Double total;

    @Column(nullable = false)
    private String estado; // Ej: "Pagado", "En Preparación", "Entregado"
    
    // Campos adicionales de tu tabla de BD
    private String direccionEntrega;
    private String telefonoContacto;
   public void setDetalles(List<DetallePedido> detallesDelPedido) {
        this.detalles = detallesDelPedido;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }
}
