package com.pasteleria.pasteleria.dto;

import lombok.Data;

@Data
public class PagoRequestDTO {
    private Long pedidoId;       // El pedido al que corresponde el pago
    private String metodoPago;   // Ej: "Tarjeta", "Efectivo"
    private Double monto;        // Monto a pagar
}