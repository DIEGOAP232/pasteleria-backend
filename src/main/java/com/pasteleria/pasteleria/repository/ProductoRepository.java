package com.pasteleria.pasteleria.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.pasteleria.pasteleria.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
}
