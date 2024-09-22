package org.aguzman.apiservlet.webapp.cookie.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aguzman.apiservlet.webapp.cookie.services.LoginService;
import org.aguzman.apiservlet.webapp.cookie.services.LoginServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet({"/login", "/login.html"}) // Rutas asociadas a este servlet
public class LoginServlet extends HttpServlet {

    private final static String USERNAME = "admin";
    private final static String PASSWORD = "12345";

    // Nota: Las cookies sólo pueden almacenar datos de tipo String
    // Nota: Jamás se debe guardar información sensible, como contraseñas, en una cookie
    // Nota: Las cookies que se crean y van en las respuestas de las peticiones http se guardan en la parte del cliente, como por ejemplo el navegador web, y se almacenan por dominios
    // Por ejemplo, el dominio "localhost" puede tener cookies asociadas, el dominio "miweb" puede tener otras, ...
    // Nota: De forma automática, las cookies almacenadas asociadas a un dominio viajan en las peticiones http(requests) que se hagan desde ese dominio


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginService loginService = new LoginServiceImpl();
        Optional<String> optionalUsername = loginService.getUsername(req);

        // Si existe una cookie con valor "username", significa que el usuario ya está autenticado y mostramos este documento HTML en vez de la vista con el formulario de login
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
            // En este caso, esta cookie nos va a servir para saber posteriormente si un usuario se autenticó correctamente en el sistema a través del username
            Cookie cookie = new Cookie("username", username);
            // Añadimos la cookie creada en la respuesta de la petición http
            resp.addCookie(cookie);

            // Redirige al servlet asociado a la ruta "/login.html", es decir, redirige a este servlet con una nueva petición http de tipo get
            resp.sendRedirect(req.getContextPath() + "/login.html");
        }
        else {
            // El mensaje de error es opcional. Si no se especifica, se muestra uno por defecto
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Lo sentimos pero no está autenticado en el sistema"); // Código 401
        }
    }
}
