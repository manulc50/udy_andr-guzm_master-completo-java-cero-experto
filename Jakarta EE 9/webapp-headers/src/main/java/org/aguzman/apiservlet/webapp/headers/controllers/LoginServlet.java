package org.aguzman.apiservlet.webapp.headers.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final static String USERNAME = "admin";
    private final static String PASSWORD = "12345";

    // Procesa el formulario de login
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username.equals(USERNAME) && password.equals(PASSWORD)) {
            resp.setContentType("text/html;charset=UTF-8");

            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("    <head>");
                out.println("        <meta charset=\"UTF-8\">");
                out.println("        <title>Login correcto</title>");
                out.println("    </head>");
                out.println("    <body>");
                out.println("        <h1>Login correcto</h1>");
                out.println("        <h3>Hola " + username + " has iniciado sesión con éxtio</h3>");
                out.println("    </body>");
                out.println("</html>");
            }
        } else {
            // El mensaje de error es opcional. Si no se especifica, se muestra uno por defecto
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Lo sentimos pero no está autenticado en el sistema"); // Código 401
        }
    }
}
