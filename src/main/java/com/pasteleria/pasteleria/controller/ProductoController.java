package com.pasteleria.pasteleria.controller;

import com.pasteleria.pasteleria.model.Producto;
import com.pasteleria.pasteleria.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// Ruta pública para el catálogo
@RequestMapping("/api/productos") 
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // GET /api/productos : Obtener todo el catálogo
    @GetMapping 
    public ResponseEntity<List<Producto>> obtenerProductos() {
        return ResponseEntity.ok(productoService.obtenerTodosLosProductos());
    }

    // GET /api/productos/{id} : Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerProductoPorId(id));
    }
    
    // NOTA: POST, PUT, DELETE van en el controlador Admin
}
