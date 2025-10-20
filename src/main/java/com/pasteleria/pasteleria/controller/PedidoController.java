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

import java.util.List;

@RestController
@RequestMapping("/api")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // -------------------- RUTAS DE CLIENTE --------------------

    // Crear pedido
    @PostMapping("/pedidos")
    @PreAuthorize("hasAnyAuthority('Cliente', 'Administrador')")
    public ResponseEntity<Pedido> crearPedido(
            @RequestBody CrearPedidoRequest request,
            Authentication authentication) {

        String emailCliente = authentication.getName();
        Pedido nuevoPedido = pedidoService.crearPedido(request, emailCliente);
        return new ResponseEntity<>(nuevoPedido, HttpStatus.CREATED);
    }

    // Obtener pedidos del usuario autenticado
    @GetMapping("/pedidos/mios")
    @PreAuthorize("hasAnyAuthority('Cliente', 'Administrador')")
    public ResponseEntity<List<Pedido>> obtenerMisPedidos(Authentication authentication) {
        String emailCliente = authentication.getName();
        List<Pedido> pedidos = pedidoService.obtenerPedidosPorUsuario(emailCliente);
        return ResponseEntity.ok(pedidos);
    }

    // -------------------- RUTAS DE ADMIN --------------------

    // Obtener todos los pedidos
    @GetMapping("/admin/pedidos")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<List<Pedido>> obtenerTodosLosPedidos() {
        return ResponseEntity.ok(pedidoService.obtenerTodosLosPedidos());
    }

    // Actualizar estado de un pedido
    @PutMapping("/admin/pedidos/{idPedido}/estado")
    @PreAuthorize("hasAuthority('Administrador')")
    public ResponseEntity<Pedido> actualizarEstadoPedido(
            @PathVariable Long idPedido,
            @RequestBody String nuevoEstado) {

        Pedido pedidoActualizado = pedidoService.actualizarEstado(idPedido, nuevoEstado);
        return ResponseEntity.ok(pedidoActualizado);
    }
}
