package com.pasteleria.pasteleria.service;

import com.pasteleria.pasteleria.model.Producto;
import com.pasteleria.pasteleria.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepo;

    public ProductoService(ProductoRepository productoRepo) {
        this.productoRepo = productoRepo;
    }

    // Obtener todos los productos
    public List<Producto> obtenerTodosLosProductos() {
        return productoRepo.findAll();
    }

    // Obtener un producto por ID usando Optional
    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productoRepo.findById(id);
    }

    // Guardar o actualizar un producto
public Producto guardarProducto(Producto producto) {
    return productoRepo.save(producto);
}

// Eliminar producto
public void eliminarProducto(Long id) {
    productoRepo.deleteById(id);
}

}
