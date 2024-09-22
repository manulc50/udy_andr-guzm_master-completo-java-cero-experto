package org.aguzman.apiservlet.webapp.session.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.aguzman.apiservlet.webapp.session.exceptions.ServiceJdbcException;
import org.aguzman.apiservlet.webapp.session.util.ConexionBaseDatos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


// Filtro que establece una nueva conexión a la base de datos para cada petición http que llega a la aplicación

@WebFilter("/*") // Filtra cualquier ruta de la aplicación
public class ConexionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // Como este recurso implementa la interfaz "AutoCloseable", podemos usar un bloque "try-with-resources" para cerrar este recurso automáticamente
        try(Connection conn = ConexionBaseDatos.getInstance()) {

            // Para poder realizar transacciones, tenemos que manejar de forma manual los commits que se hacen a la base de datos
            // Para ello, tenemos que establecer la propiedad "autoCommit" de la conexión a false(por defecto tiene el valor true)
            if(conn.getAutoCommit())
                conn.setAutoCommit(false);

            // Bloque try-catch para la transacción
            try {
                // Creamos el atributo "conn" en la petición http con la conexión a la base de datos para que pueda ser usada en los métodos de la capa de servicio afectados por esa petición http
                servletRequest.setAttribute("conn", conn);

                //  Aquí se ejecuta la petición http
                filterChain.doFilter(servletRequest, servletResponse);

                // Después de ejecutarse la petición http, hacemos un commit para que las modificaciones sobre la base de datos que ha hecho esa petición http queden reflejadas
                conn.commit();
            }
            catch(SQLException |ServiceJdbcException e) {
                // Hacemos un rollback en caso de que falle la transacción
                conn.rollback();
                // Envía una vista de error con el código de respuesta 500(INTERNAL_SERVER_ERROR)
                // El mensaje de error es opcional. Si no se especifica, se muestra uno por defecto
                ((HttpServletResponse)servletResponse).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                e.printStackTrace();
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
