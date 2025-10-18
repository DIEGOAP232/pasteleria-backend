package com.pasteleria.pasteleria.dto;

import java.util.List;
import lombok.Data;

@Data
public class CrearPedidoRequest {

    private Long idUsuario;
    private String direccionEntrega;
    private String telefonoContacto;

    private List<DetallePedidoDTO> detalles;
    
    private String mmetodopago;
    private Double montoTotal;
}
