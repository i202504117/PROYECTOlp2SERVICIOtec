package com.techsolutions.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techsolutions.modelo.OrdenServicio;

public interface IOrdenServicioRepository extends JpaRepository<OrdenServicio, Integer> {

    List<OrdenServicio> findByClienteIdCliente(Integer idCliente);

    List<OrdenServicio> findByTecnicoIdtecnico(Integer idtecnico);

    @Query("SELECT o FROM OrdenServicio o WHERE o.fecha BETWEEN :desde AND :hasta")
    List<OrdenServicio> findByFechaBetween(Date desde, Date hasta);

    @Query("SELECT o FROM OrdenServicio o WHERE MONTH(o.fecha) = :mes AND YEAR(o.fecha) = :anio")
    List<OrdenServicio> findByMesAnio(@Param("mes") int mes, @Param("anio") int anio);

    @Query("SELECT o FROM OrdenServicio o WHERE YEAR(o.fecha) = :anio")
    List<OrdenServicio> findByAnio(@Param("anio") int anio);

    @Query("SELECT o FROM OrdenServicio o JOIN FETCH o.cliente JOIN FETCH o.tecnico "
         + "JOIN FETCH o.estado ORDER BY o.fecha DESC")
    List<OrdenServicio> listarConDetalle();

    @Query("SELECT t.nombre, COUNT(o) FROM OrdenServicio o JOIN o.tecnico t "
         + "GROUP BY t.nombre ORDER BY COUNT(o) DESC")
    List<Object[]> contarOrdenesPorTecnico();

    @Query("SELECT e.nombre, COUNT(o) FROM OrdenServicio o JOIN o.estado e "
         + "GROUP BY e.nombre HAVING COUNT(o) >= :minimo")
    List<Object[]> contarPorEstado(@Param("minimo") long minimo);
}