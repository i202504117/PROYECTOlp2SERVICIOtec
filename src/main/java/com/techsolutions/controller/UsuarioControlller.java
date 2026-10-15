package com.techsolutions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.techsolutions.modelo.Usuario;
import com.techsolutions.service.AuthService;
import com.techsolutions.service.RolImplement;
import com.techsolutions.service.UsuarioImplement;

@Controller
public class UsuarioControlller {
	
	@Autowired
	private RolImplement rolImp;
	@Autowired
	private UsuarioImplement usuarioImp;
	@Autowired
	private AuthService authService;

	    @GetMapping("/nuevoUsuario")
	    public String nuevoUsuario(Model model) {
	        model.addAttribute("usuario", new Usuario());
	        model.addAttribute("roles", rolImp.listarRoles());

	        return "formularioUsuario"; // nombre de tu HTML
	    }

	    @PostMapping("/guardarUsuario")
	    public String guardarUsuario(Usuario usuario) {

	    	  authService.saveUser(usuario);
	        return "redirect:/listarUsuarios";
	    }

	    @GetMapping("/listarUsuarios")
	    public String listarUsuarios(Model model) {

	    	 model.addAttribute(
	    	            "usuarios",
	    	            usuarioImp.listarUsuarios());

	        return "usuarios";
	    }
	
}
