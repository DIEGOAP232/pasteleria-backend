package com.pasteleria.pasteleria.dto;

import lombok.Data;

@Data
public class DetallePersonalizacionDTO {
    private String tipo;      // Ej: "Texto", "Color", "Forma"
    private String valor;
    public Long getIdPersonalizacion() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIdPersonalizacion'");
    }
    
}
