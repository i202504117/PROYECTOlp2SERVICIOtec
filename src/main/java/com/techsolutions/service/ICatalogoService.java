package com.techsolutions.service;

import java.util.List;
import java.util.Optional;

import com.techsolutions.modelo.Equipo;
import com.techsolutions.modelo.Especialidad;
import com.techsolutions.modelo.EstadoServicio;
import com.techsolutions.modelo.Marca;

public interface ICatalogoService {
    List<Especialidad> listarEspecialidades();
    List<Marca> listarMarcas();
    List<EstadoServicio> listarEstados();
    List<Equipo> listarEquiposPorCliente(Integer idCliente);
    Equipo guardarEquipo(Equipo equipo);
    List<Equipo> listarEquipos();
    Optional<Equipo> buscarEquipo(Integer id);
    void eliminarEquipo(Integer id);
}