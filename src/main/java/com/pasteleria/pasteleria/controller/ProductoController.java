package com.pasteleria.pasteleria.controller;

import com.pasteleria.pasteleria.model.Producto;
import com.pasteleria.pasteleria.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }
    

    // GET /api/productos -> Todos los productos
    @GetMapping
    public ResponseEntity<List<Producto>> obtenerProductos() {
        return ResponseEntity.ok(productoService.obtenerTodosLosProductos());
    }

    // GET /api/productos/{id} -> Producto por ID usando Optional
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        return productoService.obtenerProductoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/crear")
        public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
            Producto nuevoProducto = productoService.guardarProducto(producto);
                return ResponseEntity.ok(nuevoProducto);
    }
}
