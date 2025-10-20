package com.pasteleria.pasteleria.service;

import com.pasteleria.pasteleria.model.Producto;
import com.pasteleria.pasteleria.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    // Constructor explícito (inyección por constructor)
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // Obtener todos los productos
    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    // Obtener un producto por ID
    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    // Guardar o actualizar un producto
    public Producto guardarProducto(Producto producto) {
        // Si el precio base o el stock vienen nulos, los inicializamos
        if (producto.getPrecioBase() == null) {
            producto.setPrecioBase(0.0);
        }
        if (producto.getStock() == null) {
            producto.setStock(0);
        }
        if (producto.getEstado() == null || producto.getEstado().isBlank()) {
            producto.setEstado("Disponible");
        }
        return productoRepository.save(producto);
    }

    // Eliminar un producto
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
