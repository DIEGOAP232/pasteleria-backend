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
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private DetallePedidoRepository detallePedidoRepository;
    @Autowired private DetallePersonalizacionRepository detallePersonalizacionRepository;
    @Autowired private PagoRepository pagoRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PersonalizacionRepository personalizacionRepository;
    @Autowired private ProductoRepository productoRepository;

    // 💡 CORRECCIÓN 1: Recibe el emailCliente del JWT, no un ID del Request.
    @Transactional 
    public Pedido crearPedido(CrearPedidoRequest request, String emailCliente) { 
        
        // 1. Obtener el cliente de forma segura usando el email del token
        Usuario cliente = usuarioRepository.findByEmail(emailCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado: " + emailCliente));
        
        // 2. Crear la cabecera del Pedido
        Pedido pedido = new Pedido();
        pedido.setUsuario(cliente);
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado("PENDIENTE_PAGO"); // Estado inicial antes de la confirmación final
        pedido.setDireccionEntrega(request.getDireccionEntrega()); // Asumiendo que existe este campo en Pedido
        
        // Guarda la cabecera para obtener el ID y asociar los detalles
        pedido = pedidoRepository.save(pedido); 

        Double totalCalculadoBackend = 0.0;
        List<DetallePedido> detallesDelPedido = new ArrayList<>();
        
        // 3. Procesar los Detalles (Productos y Personalizaciones)
        for (DetallePedidoDTO detalleDTO : request.getDetalles()) {
            Producto producto = productoRepository.findById(detalleDTO.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + detalleDTO.getIdProducto()));
            
            // 💡 CORRECCIÓN 2a: Verificar Stock
            if (producto.getStock() < detalleDTO.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }

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
            
            detalle = detallePedidoRepository.save(detalle); // Guardamos el detalle
            
            totalCalculadoBackend += detalle.getSubtotal();

            // 💡 CORRECCIÓN 2b: Reducir y guardar el stock
            producto.setStock(producto.getStock() - detalleDTO.getCantidad());
            productoRepository.save(producto);
            
            // c. Crear los DetallePersonalizacion
            for (DetallePersonalizacionDTO persDTO : detalleDTO.getPersonalizaciones()) {
                Personalizacion pers = personalizacionRepository.findById(persDTO.getIdPersonalizacion())
                        .orElseThrow(() -> new RuntimeException("Personalización no encontrada"));
                DetallePersonalizacion dPers = new DetallePersonalizacion();
                dPers.setDetallePedido(detalle);
                dPers.setPersonalizacion(pers);
                detallePersonalizacionRepository.save(dPers);
            }
            detallesDelPedido.add(detalle);
        }
        
        // 💡 CORRECCIÓN 3: El total del pedido debe ser el calculado en el backend
        pedido.setTotal(totalCalculadoBackend); 
        pedido.setDetalles(detallesDelPedido);
        pedidoRepository.save(pedido); // Actualiza el total en la cabecera
        
        // 4. Crear el registro de Pago (usando el total calculado)
        Pago pago = new Pago();
        pago.setPedido(pedido);
        pago.setMonto(totalCalculadoBackend); // Usamos el total calculado en el backend
        pago.setMetodo(request.getMmetodopago()); // Asumo que el campo es 'metodoPago'
        pago.setEstado("Completado");
        pago.setFecha(LocalDateTime.now());
        pagoRepository.save(pago);
        
        return pedido;
    }


    // El método actualizarEstado está bien, solo usa el PedidoRepository y Pedido
    @Transactional
    public Pedido actualizarEstado(Long pedidoId, String nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        
        pedido.setEstado(nuevoEstado);
        return pedidoRepository.save(pedido);
    }
}
