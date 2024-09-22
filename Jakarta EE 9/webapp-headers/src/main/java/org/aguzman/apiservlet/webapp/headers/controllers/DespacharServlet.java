package org.aguzman.apiservlet.webapp.headers.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/despachar")
public class DespacharServlet extends HttpServlet {

    // Nota: Despachar un servlet es similar a una redirección con la diferencia de que en una redirección se reinicia la petición http, es decir, se realiza una nueva petición http y cambia la ruta,
    // sin embargo, despachar un servlet utiliza la misma petición http actual y la une a la petición del nuevo servlet, es decir, siempre se trata de la misma petición http y se mantiene la ruta

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/productos.html").forward(req, resp); // Despacha al nuevo servlet de la ruta "/productos.html"
    }
}
