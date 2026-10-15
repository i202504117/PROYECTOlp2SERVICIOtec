
package com.techsolutions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.techsolutions.service.IClienteService;
import com.techsolutions.service.ITecnicoSevice;
import com.techsolutions.service.IUsuarioService;
import com.techsolutions.service.IRepuestoService;
import com.techsolutions.service.IProveedorService;
import com.techsolutions.service.IOrdenServicioService;

@Controller
public class HomeController {

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private ITecnicoSevice tecnicoService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IRepuestoService repuestoService;

    @Autowired
    private IProveedorService proveedorService;

    @Autowired
    private IOrdenServicioService ordenServicioService;

    // Redirección raíz hacia el login
    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    // Inicio de sesión
    @GetMapping("/login")
    public String login(
            @RequestParam(required = false) String error,
            Model model) {

        if (error != null) {
            model.addAttribute(
                "mensajeError",
                "❌ Usuario o contraseña incorrectos en TechSolutions"
            );
        }

        return "login";
    }

    // Panel principal
    @GetMapping("/index")
    public String mostrarIndex(Model model, Authentication auth) {

        if (auth != null) {
            model.addAttribute("nombreUsuario", auth.getName());
        }

        // Estadísticas de las seis tarjetas
        model.addAttribute(
            "totalClientes",
            clienteService.getAllClientes().size()
        );

        model.addAttribute(
            "totalTecnicos",
            tecnicoService.getAllTecnico().size()
        );

        model.addAttribute(
            "totalUsuarios",
            usuarioService.listarUsuarios().size()
        );

        model.addAttribute(
            "totalProductos",
            repuestoService.getAllRepuesto().size()
        );

        model.addAttribute(
            "totalProveedores",
            proveedorService.getAllProveedor().size()
        );

        model.addAttribute(
            "totalOrdenes",
            ordenServicioService.getAllOrdenes().size()
        );

        return "index";
    }

    // Panel específico para técnicos
    @GetMapping("/dashboard-tecnico")
    public String dashboardTecnico(
            Model model,
            Authentication auth) {

        if (auth != null) {
            model.addAttribute("nombreUsuario", auth.getName());
        }

        model.addAttribute(
            "totalClientes",
            clienteService.getAllClientes().size()
        );

        return "dashboard-tecnico";
    }

    // Panel específico para clientes
    @GetMapping("/dashboard-cliente")
    public String dashboardCliente(
            Model model,
            Authentication auth) {

        if (auth != null) {
            model.addAttribute("nombreUsuario", auth.getName());
        }

        return "dashboard-cliente";
    }
}
