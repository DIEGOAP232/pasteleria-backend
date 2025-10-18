package com.pasteleria.pasteleria.service;


import com.pasteleria.pasteleria.dto.CrearPedidoRequest;
import com.pasteleria.pasteleria.dto.DetallePedidoDTO;
import com.pasteleria.pasteleria.dto.DetallePersonalizacionDTO;
import com.pasteleria.pasteleria.model.*;
import com.pasteleria.pasteleria.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PedidoService {

    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private DetallePedidoRepository detallePedidoRepository;
    @Autowired private DetallePersonalizacionRepository detallePersonalizacionRepository;
    @Autowired private PagoRepository pagoRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PersonalizacionRepository personalizacionRepository;
    @Autowired private ProductoRepository productoRepository; 

    @Transactional 
    public Pedido crearPedido(CrearPedidoRequest request) {
        // 1. Validar el usuario
        Usuario cliente = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        
        // 2. Crear la cabecera del Pedido
        Pedido pedido = new Pedido();
        pedido.setUsuario(cliente);
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado("Pagado"); // Asumimos que el pago es inmediato
        pedido.setTotal(request.getMontoTotal()); 
        // Puedes añadir aquí la validación del montoTotal vs. el calculado en el backend

        pedido = pedidoRepository.save(pedido); // Guarda la cabecera para obtener el ID

        Double totalCalculadoBackend = 0.0;
        
        // 3. Procesar los Detalles (Productos y Personalizaciones)
        for (DetallePedidoDTO detalleDTO : request.getDetalles()) {
            Producto producto = productoRepository.findById(detalleDTO.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + detalleDTO.getIdProducto()));
            
            // a. Calcular el precio unitario final, incluyendo personalizaciones
            Double precioUnitarioFinal = producto.getPrecioBase();
            for (DetallePersonalizacionDTO persDTO : detalleDTO.getPersonalizaciones()) {
                Personalizacion pers = personalizacionRepository.findById(persDTO.getIdPersonalizacion())
                        .orElseThrow(() -> new RuntimeException("Personalización no encontrada"));
                precioUnitarioFinal += pers.getCostoExtra();
            }

            // b. Crear el DetallePedido
            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioUnitario(precioUnitarioFinal);
            detalle.setSubtotal(precioUnitarioFinal * detalleDTO.getCantidad());
            
            detalle = detallePedidoRepository.save(detalle);
            
            totalCalculadoBackend += detalle.getSubtotal();

            // c. Crear los DetallePersonalizacion
            for (DetallePersonalizacionDTO persDTO : detalleDTO.getPersonalizaciones()) {
                Personalizacion pers = personalizacionRepository.findById(persDTO.getIdPersonalizacion()).get();
                DetallePersonalizacion dPers = new DetallePersonalizacion();
                dPers.setDetallePedido(detalle);
                dPers.setPersonalizacion(pers);
                detallePersonalizacionRepository.save(dPers);
            }
        }
        
        // 4. Crear el registro de Pago
        Pago pago = new Pago();
        pago.setPedido(pedido);
        pago.setMonto(request.getMontoTotal());
        pago.setMetodo(request.getMmetodopago());
        pago.setEstado("Completado");
        pago.setFecha(LocalDateTime.now());
        pagoRepository.save(pago);
        
        return pedido;
    }


    @Transactional
    public Pedido actualizarEstado(Long pedidoId, String nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        
        
        pedido.setEstado(nuevoEstado);
        return pedidoRepository.save(pedido);
    }
}
