package org.mlorenzo.apiservlet.webapp.session.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/guardar-session")
public class GuardarNombreSessionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nombre = req.getParameter("nombre");
        HttpSession session = req.getSession();
        session.setAttribute("nombre", nombre);
        // Redirige al servlet asociado a la ruta "/perfil-usuario" mediante una nueva petición http de tipo Get
        resp.sendRedirect(req.getContextPath() + "/perfil-usuario");
    }
}
