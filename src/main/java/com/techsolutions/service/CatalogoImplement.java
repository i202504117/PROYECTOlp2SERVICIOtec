package com.techsolutions.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techsolutions.modelo.Equipo;
import com.techsolutions.modelo.Especialidad;
import com.techsolutions.modelo.EstadoServicio;
import com.techsolutions.modelo.Marca;
import com.techsolutions.repository.IEquipoRepository;
import com.techsolutions.repository.IEspecialidadRepository;
import com.techsolutions.repository.IEstadoServicioRepository;
import com.techsolutions.repository.IMarcaRepository;

@Service
public class CatalogoImplement implements ICatalogoService {

	@Autowired
	private IEspecialidadRepository especialidadRepo;
	@Autowired
	private IMarcaRepository marcaRepo;
	@Autowired
	private IEstadoServicioRepository estadoRepo;
	@Autowired
	private IEquipoRepository equipoRepo;

	@Override
	public List<Especialidad> listarEspecialidades() {
		return especialidadRepo.findAll();
	}

	@Override
	public List<Marca> listarMarcas() {
		return marcaRepo.findAll();
	}

	@Override
	public List<EstadoServicio> listarEstados() {
		return estadoRepo.findAll();
	}

	@Override
	public List<Equipo> listarEquiposPorCliente(Integer idCliente) {
		return equipoRepo.findByClienteIdCliente(idCliente);
	}

	@Override
	public Equipo guardarEquipo(Equipo equipo) {
		return equipoRepo.save(equipo);
	}
	@Override
	public List<Equipo> listarEquipos() {
	    return equipoRepo.findAll();
	}

	@Override
	public Optional<Equipo> buscarEquipo(Integer id) {
	    return equipoRepo.findById(id);
	}

	@Override
	public void eliminarEquipo(Integer id) {
		equipoRepo.deleteById(id);
	}
}