package com.techsolutions.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techsolutions.modelo.Cliente;
import com.techsolutions.service.IClienteService;
import com.techsolutions.util.ErroresForm;

import jakarta.validation.Valid;

@Controller
public class ClienteController {

	@Autowired
	private IClienteService clienteService;

	@GetMapping("/listarClientes")
	public String listar(Model model) {
		model.addAttribute("clientes", clienteService.getAllClientes());
		return "clientes";
	}

	@GetMapping("/nuevoCliente")
	public String nuevo(Model model) {
		model.addAttribute("cliente", new Cliente());
		return "formularioCliente";
	}

	@PostMapping("/guardarCliente")
	public String guardar(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult result,
			RedirectAttributes redirectAttributes, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("error", "❌ " + ErroresForm.primerError(result));
			return "formularioCliente";
		}

		try {
			clienteService.saveCliente(cliente);
			redirectAttributes.addFlashAttribute("exito", "Cliente guardado exitosamente en TechSolutions.");
			return "redirect:/listarClientes";

		} catch (DataIntegrityViolationException e) {
			model.addAttribute("error", "❌ No se pudo guardar: el DNI ya está registrado.");
		} catch (RuntimeException e) {
			model.addAttribute("error", "❌ " + e.getMessage());
		}

		model.addAttribute("cliente", cliente);
		return "formularioCliente";
	}

	@GetMapping("/editarCliente/{id}")
	public String editar(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
		Optional<Cliente> optCliente = clienteService.buscarClientePorId(id);

		if (optCliente.isPresent()) {
			model.addAttribute("cliente", optCliente.get());
			return "formularioCliente";
		}
		redirectAttributes.addFlashAttribute("error", "❌ El cliente solicitado no existe.");
		return "redirect:/listarClientes";
	}

	@GetMapping("/eliminarCliente/{id}")
	public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		clienteService.deleteCliente(id);
		redirectAttributes.addFlashAttribute("exito", "Cliente desactivado correctamente.");
		return "redirect:/listarClientes";
	}

	@GetMapping("/buscarCliente")
	public String buscarCliente(@RequestParam String dni, Model model) {
		model.addAttribute("clientes", clienteService.buscarPorDniContaining(dni));
		return "clientes";
	}
}