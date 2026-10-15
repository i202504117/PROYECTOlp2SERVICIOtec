package com.techsolutions.service;

import java.util.List;
import java.util.Optional;

import com.techsolutions.modelo.Proveedor;

public interface IProveedorService {
	public List<Proveedor> getAllProveedor();

	public Proveedor createProveedor(Proveedor proveedor);

	public void deleteProveedor(int id);

	public  Optional<Proveedor> buscarProveedor(int id);
	public   List<Proveedor> buscarProveedor(String nombre);
	  public List<Proveedor> listarTodos();
}
