package com.techsolutions.config;

import java.io.IOException;
import java.util.Collection;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities =
                authentication.getAuthorities();

        String redirectUrl = "/login?error=role";

        for (GrantedAuthority auth : authorities) {

            String role = auth.getAuthority();

            if (role.equals("ROLE_ADMINISTRADOR")) {
                redirectUrl = "/index";
                break;
            }
            else if (role.equals("ROLE_TECNICO")) {
                redirectUrl = "/dashboard-tecnico";
                break;
            }
        }

        response.sendRedirect(redirectUrl);
    }
}