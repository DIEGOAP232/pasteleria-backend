package com.pasteleria.pasteleria.controller;

import com.pasteleria.pasteleria.dto.CrearPedidoRequest;
import com.pasteleria.pasteleria.model.Pedido;
import com.pasteleria.pasteleria.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // ------------------------------------------------------------------
    // ENDPOINT 1: CREAR PEDIDO
    // Rutas: /api/pedidos
    // Acceso: Cliente o Administrador (usa @PreAuthorize en lugar de SecurityConfig)
    // ------------------------------------------------------------------
    @PostMapping("/pedidos")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('Cliente') or hasAuthority('Administrador')")
    public ResponseEntity<Pedido> crearPedido(@RequestBody CrearPedidoRequest request) {
        try {
            Pedido nuevoPedido = pedidoService.crearPedido(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
        } catch (RuntimeException e) {
            // Manejo de errores de validación (ej: producto no encontrado)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // ------------------------------------------------------------------
    // ENDPOINT 2: ACTUALIZAR ESTADO
    // Rutas: /api/admin/pedidos/{id}/estado
    // Acceso: SOLO Administrador
    // ------------------------------------------------------------------
    @PutMapping("/admin/pedidos/{id}/estado")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<Pedido> actualizarEstado(
            @PathVariable Long id, 
            @RequestParam String nuevoEstado) {
        try {
            Pedido pedidoActualizado = pedidoService.actualizarEstado(id, nuevoEstado);
            return ResponseEntity.ok(pedidoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
    // Aquí irían otros endpoints CRUD (GET /pedidos, GET /pedidos/{id})
}
