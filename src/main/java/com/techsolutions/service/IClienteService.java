package com.techsolutions.service;

import java.util.List;
import java.util.Optional;

import com.techsolutions.modelo.Cliente;

public interface IClienteService {

	List<Cliente> getAllClientes();

	void saveCliente(Cliente cliente);

	void deleteCliente(Integer id);

	Optional<Cliente> buscarClientePorId(Integer id);

	Cliente buscarPorDni(String dni);

	List<Cliente> buscarPorDniContaining(String dni);

	List<Cliente> buscarPorNombre(String nombre);
}