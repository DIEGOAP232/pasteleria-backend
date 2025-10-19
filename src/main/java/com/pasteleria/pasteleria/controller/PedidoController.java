package com.pasteleria.pasteleria.controller;

import com.pasteleria.pasteleria.dto.CrearPedidoRequest;
import com.pasteleria.pasteleria.model.Pedido;
import com.pasteleria.pasteleria.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // ----------------------------------------------------------------------
    // RUTAS DE CLIENTE
    // ----------------------------------------------------------------------

    // POST /api/pedidos
    // Crea un nuevo pedido. Requiere JWT (Cliente/Admin).
    @PostMapping("/pedidos")
    @PreAuthorize("hasAnyAuthority('Cliente', 'Administrador')")
    public ResponseEntity<Pedido> crearPedido(
            @RequestBody CrearPedidoRequest request,
            Authentication authentication) {
        
        // 💡 EXTRAEMOS EL EMAIL DEL TOKEN JWT DE FORMA SEGURA
        String emailCliente = authentication.getName(); 
        
        Pedido nuevoPedido = pedidoService.crearPedido(request, emailCliente);
        return new ResponseEntity<>(nuevoPedido, HttpStatus.CREATED);
    }
    
    // Asumo que tienes un método GET para obtener los pedidos del usuario autenticado aquí


    // ----------------------------------------------------------------------
    // RUTAS DE ADMINISTRADOR
    // ----------------------------------------------------------------------
    
    // PUT /api/admin/pedidos/{id}/estado
    @PutMapping("/admin/pedidos/{idPedido}/estado")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<Pedido> actualizarEstadoPedido(
            @PathVariable Long idPedido,
            @RequestBody String nuevoEstado) { 
        
        Pedido pedidoActualizado = pedidoService.actualizarEstado(idPedido, nuevoEstado);
        return ResponseEntity.ok(pedidoActualizado);
    }
    
    // Asumo que tienes un método GET para obtener todos los pedidos aquí
}