package com.pasteleria.pasteleria.dto;

import lombok.Data;

@Data
public class DetallePersonalizacionDTO {
    private Long idPersonalizacion; // necesario para enlazar con la tabla
    private String tipo;      
    private String valor;
}
