package com.pasteleria.pasteleria.dto;

import lombok.Data;
import java.util.List;

@Data
public class CrearPedidoRequest {
    
    // La lista de todos los productos y sus personalizaciones
    private List<DetallePedidoDTO> detalles;
    
    // Información de envío/recogida
    private String direccionEntrega;

    public Double getMontoTotal() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMontoTotal'");
    }

    public String getMmetodopago() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMmetodopago'");
    }

    public Long getIdUsuario() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIdUsuario'");
    }
}
