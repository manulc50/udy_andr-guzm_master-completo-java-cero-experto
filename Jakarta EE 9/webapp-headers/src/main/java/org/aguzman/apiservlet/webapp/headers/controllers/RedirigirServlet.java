package org.aguzman.apiservlet.webapp.headers.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/redirigir")
public class RedirigirServlet extends HttpServlet {

    // Nota: Cuando hacemos una redirección, se reinicia la petición http, es decir, realiza una nueva petición http y, por lo tanto, cambia la ruta

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 2 formas de redirigir
        // Primera forma
        //resp.setHeader("Location", req.getContextPath() + "/productos.html"); // Redirige al servlet de la ruta "/productos.html"
        //resp.setStatus(HttpServletResponse.SC_FOUND); // Código 302
        // Segunda forma
        resp.sendRedirect(req.getContextPath() + "/productos.html"); // Redirige al servlet de la ruta "/productos.html"
    }
}
