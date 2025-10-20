package com.pasteleria.pasteleria.dto;

import lombok.Data;
import java.util.List;

@Data
public class CrearPedidoRequest {
    
    private Double montoTotal;
    private String metodoPago;
    private Long usuarioId;
    private String direccionEntrega;
    private List<DetallePedidoDTO> detalles;
}
