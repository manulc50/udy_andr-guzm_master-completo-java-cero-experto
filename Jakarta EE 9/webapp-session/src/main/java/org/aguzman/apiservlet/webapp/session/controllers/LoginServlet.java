package org.aguzman.apiservlet.webapp.session.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.aguzman.apiservlet.webapp.session.services.LoginService;
import org.aguzman.apiservlet.webapp.session.services.LoginServiceSessionImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet({"/login", "/login.html"}) // Rutas asociadas a este servlet
public class LoginServlet extends HttpServlet {

    private final static String USERNAME = "admin";
    private final static String PASSWORD = "12345";

    // Nota: Cada vez que un cliente, por ejemplo un navegador web, se conecta por primera vez a nuestra aplicación desplegada en un servidor mediante una petición http, de forma automática se crea una nueva sesión para ese cliente
    //       y se almacena en ese cliente una cookie con el identificador de esa sesión
    // Nota: A diferencia de las cookies, que sólo pueden almacenar datos de tipo String, las sesiones también pueden almacenar otros tipos de objetos
    // Nota: Los atributos de sesión que se creen permanecen almacenados para todas las peticiones http que se hagan desde un cliente


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginService loginService = new LoginServiceSessionImpl();
        Optional<String> optionalUsername = loginService.getUsername(req);

        // Si existe un atributo de sesión con valor "username", significa que el usuario ya está autenticado y mostramos este documento HTML en vez de la vista con el formulario de login
        if(optionalUsername.isPresent()) {
            String username = optionalUsername.get();

            resp.setContentType("text/html;charset=UTF-8");

            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("    <head>");
                out.println("        <meta charset=\"UTF-8\">");
                out.println("        <title>Hola " + username + "</title>");
                out.println("    </head>");
                out.println("    <body>");
                out.println("        <h1>Hola " + username + ", has iniciado sesión con éxito</h1>");
                out.println("        <p><a href=\"" + req.getContextPath() + "/index.html\">volver</a></p>");
                out.println("        <p><a href=\"" + req.getContextPath() + "/logout\">cerrar sesión</a></p>");
                out.println("    </body>");
                out.println("</html>");
            }
        }
        // En caso contrario, significa que el usuario no está autenticado actualmente y mostramos la vista con el formulario de login
        else {
            // Carga la vista "/login.jsp"
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    // Procesa el formulario de login
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username.equals(USERNAME) && password.equals(PASSWORD)) {
            // Obtenemos la sesión http del cliente
            HttpSession session = req.getSession();
            // En este caso, este atributo de sesión nos va a servir para saber posteriormente si un usuario se autenticó correctamente en el sistema a través del username
            session.setAttribute("username", username);

            // Redirige al servlet asociado a la ruta "/login.html", es decir, redirige a este servlet con una nueva petición http de tipo get
            resp.sendRedirect(req.getContextPath() + "/login.html");
        }
        else {
            // Envía una vista de error con el código de respuesta 401(UNAUTHORIZED)
            // El mensaje de error es opcional. Si no se especifica, se muestra uno por defecto
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Lo sentimos pero no está autenticado en el sistema");
        }
    }
}
