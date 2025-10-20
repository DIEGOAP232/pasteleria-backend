package com.pasteleria.pasteleria.controller;

import com.pasteleria.pasteleria.model.Producto;
import com.pasteleria.pasteleria.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin/productos")
@PreAuthorize("hasAuthority('Administrador')")
public class AdminProductoController {

    private final ProductoService productoService;

    public AdminProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // POST /api/admin/productos -> Crear un producto nuevo
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.guardarProducto(producto);
        return ResponseEntity.ok(nuevoProducto);
    }

    // PUT /api/admin/productos/{id} -> Actualizar un producto existente
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Optional<Producto> prodExistente = productoService.obtenerProductoPorId(id);
        if (prodExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Producto p = prodExistente.get();
        p.setNombre(producto.getNombre());
        p.setDescripcion(producto.getDescripcion());
        p.setPrecioBase(producto.getPrecioBase());
        p.setStock(producto.getStock());
        p.setEstado(producto.getEstado());

        Producto actualizado = productoService.guardarProducto(p);
        return ResponseEntity.ok(actualizado);
    }

    // DELETE /api/admin/productos/{id} -> Eliminar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        Optional<Producto> prodExistente = productoService.obtenerProductoPorId(id);
        if (prodExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
