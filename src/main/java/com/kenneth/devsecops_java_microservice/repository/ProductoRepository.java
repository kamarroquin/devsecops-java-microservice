package com.kenneth.devsecops_java_microservice.repository;

import com.kenneth.devsecops_java_microservice.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}