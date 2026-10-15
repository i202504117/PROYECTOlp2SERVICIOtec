package com.techsolutions.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Autowired
	private LoginSuccessHandler successHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable())

				.authorizeHttpRequests(auth -> auth

						.requestMatchers("/", "/login", "/css/**", "/js/**", "/imagenes/**").permitAll()

						.requestMatchers("/index", "/listarRepuestos/**", "/nuevoRepuestos/**", "/guardarRepuestos/**",
								"/editarRepuestos/**", "/eliminarRepuestos/**", "/buscarRepuestos/**",
								"/listarProveedores/**", "/nuevoProveedor/**", "/guardarProveedor/**",
								"/editarProveedor/**", "/eliminarProveedor/**", "/buscarProveedor/**",
								"/listarUsuarios/**", "/nuevoUsuario/**", "/guardarUsuario/**", "/editarUsuario/**",
								"/eliminarUsuario/**", "/reportes/**")
						.hasRole("ADMINISTRADOR")

						.requestMatchers("/dashboard-tecnico", "/listarTecnicos/**", "/nuevoTecnico/**",
								"/guardarTecnico/**", "/editarTecnico/**", "/eliminarTecnico/**", "/buscarTecnico/**",
								"/reporte/**")
						.hasAnyRole("ADMINISTRADOR", "TECNICO")

						.requestMatchers("/listarClientes/**", "/nuevoCliente/**", "/guardarCliente/**",
								"/editarCliente/**", "/eliminarCliente/**", "/buscarCliente/**", "/listarOrdenes/**",
								"/nuevaOrden/**", "/guardarOrden/**", "/editarOrden/**", "/eliminarOrden/**")
						.hasAnyRole("ADMINISTRADOR", "TECNICO")

						.anyRequest().authenticated())

				.formLogin(form -> form.loginPage("/login").usernameParameter("correo").passwordParameter("password")
						.successHandler(successHandler))

				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout")
						.invalidateHttpSession(true).deleteCookies("JSESSIONID"));

		return http.build();
	}

}