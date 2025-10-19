package com.pasteleria.pasteleria.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "detalle_personalizacion")
@EqualsAndHashCode(exclude = {"detallePedido"})
public class DetallePersonalizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación Many-to-One: Un detalle de pedido puede tener muchas personalizaciones
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detalle_pedido_id", nullable = false)
    private DetallePedido detallePedido;

    private String tipo;
    private String valor;
    // private Double costoAdicional; 
    public void setPersonalizacion(Personalizacion pers) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPersonalizacion'");
    }
}
