package com.pasteleria.pasteleria.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "DetallePersonalizacion")
@Data
public class DetallePersonalizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetallePersonalizacion;

    @ManyToOne
    @JoinColumn(name = "detalle_pedido_id", referencedColumnName = "idDetalle", nullable = false)
    private DetallePedido detallePedido;

    @ManyToOne
    @JoinColumn(name = "personalizacion_id", referencedColumnName = "idPersonalizacion", nullable = false)
    private Personalizacion personalizacion;
}
