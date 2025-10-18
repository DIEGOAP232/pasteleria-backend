package com.pasteleria.pasteleria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pasteleria.pasteleria.model.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long>{
    
}
