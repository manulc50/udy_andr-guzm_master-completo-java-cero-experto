package org.aguzman.apiservlet.webapp.session.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aguzman.apiservlet.webapp.session.services.LoginService;
import org.aguzman.apiservlet.webapp.session.services.LoginServiceSessionImpl;

import java.io.IOException;
import java.util.Optional;

// Los filtros son parecidos a los listeners pero únicamente se enfocan en las peticiones http
// Otra diferencia de los filtros con respecto a los listeners es que los filtros pueden ser mapeados a rutas asociadas a servlets
// Sin embargo, los listeners basados en peticiones http afectan de forma global a todas las peticiones http que se hagan

// En este caso en concreto, la idea de este filtro es hacer privado el acceso a la rutas que coincidan con la expresiones "/carro/*", "/productos/form/*" o "/productos/eliminar/*,
// es decir, requerir que el usuario esté autenticado para poder acceder a esas rutas de la aplicación

// Este filtro se aplica a las peticiones http cuyas rutas coincidan con las expresiones "/carro/*", "/productos/form/*" o "/productos/eliminar/*
@WebFilter({"/carro/*", "/productos/form/*", "/productos/eliminar/*"})
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LoginService loginService = new LoginServiceSessionImpl();
        Optional<String> username = loginService.getUsername((HttpServletRequest)servletRequest);
        // Si el usuario está autenticado, continuamos la ejecución de los siguientes filtros de la cadena de filtros hasta llegar al servlet asociado a alguna de las rutas indicadas en la anotación "@WebFilter"
        if(username.isPresent())
            filterChain.doFilter(servletRequest, servletResponse);
        // En caso contrario, muestra una vista de error con el código de respuesta 401(UNAUTHORIZED)
        else {
            // Envía una vista de error con el código de respuesta 401(UNAUTHORIZED)
            // El mensaje de error es opcional. Si no se especifica, se muestra uno por defecto
            ((HttpServletResponse)servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Lo sentimos pero esta página requiere estar autenticado en el sistema");
        }
    }
}
