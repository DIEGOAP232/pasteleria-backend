package com.pasteleria.pasteleria.service;

import com.pasteleria.pasteleria.model.Producto;
import com.pasteleria.pasteleria.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // ----------------------------------------------------
    // C - CREAR PRODUCTO
    // ----------------------------------------------------
    public Producto crearProducto(Producto producto) {
        // Aquí podrías añadir lógica de validación antes de guardar
        return productoRepository.save(producto);
    }

    // ----------------------------------------------------
    // R - LEER TODOS (Catálogo Público)
    // ----------------------------------------------------
    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }
    
    // ----------------------------------------------------
    // R - LEER POR ID
    // ----------------------------------------------------
    public Producto obtenerProductoPorId(Long id) {
        // Usamos orElseThrow para lanzar una excepción si no se encuentra
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }

    // ----------------------------------------------------
    // U - ACTUALIZAR
    // ----------------------------------------------------
    public Producto actualizarProducto(Long id, Producto detallesProducto) {
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        
        // Actualizar los campos: debes mapear los campos relevantes aquí.
        // Ejemplo simple (asumiendo que tu entidad Producto tiene getters/setters):
        productoExistente.setNombre(detallesProducto.getNombre());
        productoExistente.setPrecioBase(detallesProducto.getPrecioBase());
        productoExistente.setStock(detallesProducto.getStock());
        // ... (Añadir más campos como descripción, categoría, imagen, etc.)
        
        return productoRepository.save(productoExistente);
    }

    // ----------------------------------------------------
    // D - ELIMINAR
    // ----------------------------------------------------
    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }
        productoRepository.deleteById(id);
    }
}
