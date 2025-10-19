package com.pasteleria.pasteleria.dto;

import java.util.List;
import lombok.Data;

@Data
 
public class DetallePedidoDTO {
    private Long idProducto;
    private int cantidad;

    private List<DetallePersonalizacionDTO> personalizaciones;
    
}
