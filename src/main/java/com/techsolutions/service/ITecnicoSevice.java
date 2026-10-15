package com.techsolutions.service;

import java.util.List;
import java.util.Optional;

import com.techsolutions.modelo.Tecnico;

public interface ITecnicoSevice {

	public List<Tecnico> getAllTecnico();

	public Tecnico createTecnico(Tecnico tecnico);

	public void deleteTecnico(int id);

	public Optional<Tecnico> buscarTecnico(int id);

	List<Tecnico> buscarNombre(String nombre);

	public Tecnico editarTecnico(Tecnico tecnico);

	Tecnico findByNombre(String nombre);
}