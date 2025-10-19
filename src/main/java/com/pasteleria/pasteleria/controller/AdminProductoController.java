package com.pasteleria.pasteleria.controller;

import com.pasteleria.pasteleria.model.Producto;
import com.pasteleria.pasteleria.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// 💡 Si usas Spring Security con @PreAuthorize, añádelo a nivel de clase o método
// @PreAuthorize("hasAuthority('Administrador')") 
@RestController
@RequestMapping("/api/admin/productos") // Ruta protegida por la configuración de seguridad
public class AdminProductoController {

    @Autowired
    private ProductoService productoService;

    // POST /api/admin/productos : Crear producto
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.crearProducto(producto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    // PUT /api/admin/productos/{id} : Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Producto productoActualizado = productoService.actualizarProducto(id, producto);
        return ResponseEntity.ok(productoActualizado);
    }

    // DELETE /api/admin/productos/{id} : Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}