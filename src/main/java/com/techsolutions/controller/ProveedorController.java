package com.techsolutions.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techsolutions.modelo.Proveedor;
import com.techsolutions.service.IProveedorService;

@Controller
public class ProveedorController {

	// Se inyecta la interfaz (antes se inyectaba la clase y la interfaz quedaba sin @Autowired, o sea null)
	@Autowired
	private IProveedorService proveedorService;

	@GetMapping("/listarProveedores")
	public String listar(Model model) {
		model.addAttribute("proveedores", proveedorService.getAllProveedor());
		return "proveedores";
	}

	@GetMapping("/nuevoProveedor")
	public String nuevo(Model model) {
		model.addAttribute("proveedor", new Proveedor());
		return "formularioProveedor";
	}

	@PostMapping("/guardarProveedor")
	public String guardar(Proveedor proveedor, RedirectAttributes redirectAttributes) {
		proveedorService.createProveedor(proveedor);
		redirectAttributes.addFlashAttribute("exito", "Proveedor guardado correctamente.");
		return "redirect:/listarProveedores";
	}

	@GetMapping("/editarProveedor/{id}")
	public String editar(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
		Optional<Proveedor> proveedor = proveedorService.buscarProveedor(id);

		if (proveedor.isPresent()) {
			model.addAttribute("proveedor", proveedor.get());
			return "formularioProveedor";
		}
		redirectAttributes.addFlashAttribute("error", "❌ El proveedor solicitado no existe.");
		return "redirect:/listarProveedores";
	}

	@GetMapping("/eliminarProveedor/{id}")
	public String eliminar(@PathVariable int id, RedirectAttributes redirectAttributes) {
		try {
			proveedorService.deleteProveedor(id);
			redirectAttributes.addFlashAttribute("exito", "Proveedor eliminado correctamente.");
		} catch (IllegalStateException e) {
			// Tiene repuestos asociados
			redirectAttributes.addFlashAttribute("error", "❌ " + e.getMessage());
		}
		return "redirect:/listarProveedores";
	}

	@GetMapping("/buscarProveedor")
	public String buscar(@RequestParam("nombre") String nombre, Model model) {
		model.addAttribute("proveedores", proveedorService.buscarProveedor(nombre));
		return "proveedores";
	}
}
