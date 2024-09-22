package org.mlorenzo.webapp.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

// Un Servlet es una clase que hereda la clase HttpServlet

@WebServlet("/index.html") // Con esta anotación mapeamos este servlet con la ruta "/index.html"
public class IndexServlet extends HttpServlet {

    // No se debe sobrescribir el método "service" de la clase padre "HttpServlet"
    // El método "service" se encarga de invocar a los métodos "doGet", "doPost", "doPut", etc..., dependiendo del tipo de la petición http que llegue(Get, Post, Put, etc...)
    // Por lo tanto, sólo tenemos que sobrescribir estos métodos "doGet", "doPost", "doPut", etc..., de la clase padre "HttpServlet" para codificar nuestras implementaciones

    // Sirve un documento HTML
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Obtiene los parámetros "nombre " y "apellido" de la url de la petición http
        String nombre  = req.getParameter("nombre");
        String apellido = req.getParameter("apellido");

        // Indicamos el tipo de contenido del cuerpo o body de la respuesta http
		resp.setContentType("text/html;charset=UTF-8");

        // Para poder añadir contenido al cuerpo o body de la respuesta
        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("    <head>");
        out.println("        <meta charset=\"UTF-8\">");
        out.println("        <title>Tarea 1: Servlet y envío de parámetros</title>");
        out.println("    </head>");
        out.println("    <body>");
        out.println("        <h1>Tarea 1: Servlet y envío de parámetros</h1>");
        if(nombre != null && apellido != null)
            out.println("        <h2>Hola mi nombre es: " + nombre + " " + apellido + "</h2>");
        else
            out.println("        <h2>No se han enviado los parámetros obligatorios \"nombre\" y \"apellido\"</h2>");
        out.println("        <h3>La fecha actual es: " + LocalDate.now()
                .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)) + "</h3>");
        out.println("    </body>");
        out.println("</html>");

        out.close();
    }
}
