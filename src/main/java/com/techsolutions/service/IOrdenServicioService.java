package com.techsolutions.service;

import java.util.List;
import java.util.Optional;

import com.techsolutions.dto.ItemOrden;
import com.techsolutions.modelo.OrdenServicio;

public interface IOrdenServicioService {

    List<OrdenServicio> getAllOrdenes();

    OrdenServicio saveOrden(OrdenServicio orden);

    OrdenServicio registrarOrden(OrdenServicio orden, List<ItemOrden> items);

    void deleteOrden(Integer id);

    Optional<OrdenServicio> buscarOrdenPorId(Integer id);

    List<OrdenServicio> getOrdenesPorTecnico(Integer idtecnico);

    List<OrdenServicio> listarPorClienteId(Integer idCliente);

    List<Object[]> contarOrdenesPorTecnico();

    List<Object[]> contarPorEstado(long minimo);
}