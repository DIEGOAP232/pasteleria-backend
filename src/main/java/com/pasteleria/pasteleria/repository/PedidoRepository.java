package com.pasteleria.pasteleria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pasteleria.pasteleria.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
}
