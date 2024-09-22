package org.aguzman.apiservlet.webapp.session.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;
import java.util.logging.Logger;

@WebFilter("/*") // La expresión "/*" es para que este filtro se aplique a todas las rutas de las peticiones http de la aplicación
public class TiempoTranscurridoFilter implements Filter {

    private static final Logger logger =  Logger.getLogger("TiempoTranscurridoFilter");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final long tiempoInicial = System.currentTimeMillis();
        filterChain.doFilter(servletRequest, servletResponse); // Cedemos el control a los siguientes filtros de la cadena de filtros para que se ejecute la petición http
        final long tiempoFinal = System.currentTimeMillis() - tiempoInicial;

        logger.info(String.format("El tiempo de carga de la página es de %s milisegundos", tiempoFinal));
    }
}
