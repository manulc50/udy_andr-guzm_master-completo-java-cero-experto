package org.aguzman.apiservlet.webapp.headers.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/hora-actualizada")
public class HoraActualizadaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.setHeader("refresh", "1"); // Hace que se actualice el documento HTML cada segundo enviando en este periodo de tiempo una petición http de tipo Get a este servlet

        LocalTime time = LocalTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("hh:mm:ss");

        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("    <head>");
            out.println("        <meta charset=\"UTF-8\">");
            out.println("        <title>Hora actualizada</title>");
            out.println("    </head>");
            out.println("    <body>");
            out.println("        <h1>Hora actualizada</h1>");
            out.println("        <h3>" + time.format(df) + "</h3>");
            out.println("    </body>");
            out.println("</html>");
        }
    }
}
