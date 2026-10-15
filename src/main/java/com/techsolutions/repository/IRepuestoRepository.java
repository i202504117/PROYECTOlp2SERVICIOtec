package com.techsolutions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techsolutions.modelo.Repuesto;

public interface IRepuestoRepository extends JpaRepository<Repuesto, Integer> {

    @Query("SELECT p FROM Repuesto p WHERE p.nombre LIKE :nombre ESCAPE '\\'")
    List<Repuesto> findByNombreContaining(String nombre);

    @Query("SELECT r FROM Repuesto r WHERE r.stock < :minimo ORDER BY r.stock ASC")
    List<Repuesto> findStockBajo(@Param("minimo") int minimo);

    @Override
    @EntityGraph(attributePaths = { "marca", "proveedor" })
    List<Repuesto> findAll();
}