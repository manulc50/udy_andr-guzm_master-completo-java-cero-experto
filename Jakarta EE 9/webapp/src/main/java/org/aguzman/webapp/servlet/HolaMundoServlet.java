package org.aguzman.webapp.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;


// Un Servlet es una clase que hereda la clase HttpServlet

@WebServlet("/hola-mundo") // Con esta anotación mapeamos este servlet con la ruta "/hola-mundo"
public class HolaMundoServlet extends HttpServlet {

    // No se debe sobrescribir el método "service" de la clase padre "HttpServlet"
    // El método "service" se encarga de invocar a los métodos "doGet", "doPost", "doPut", etc..., dependiendo del tipo de la petición http que llegue(Get, Post, Put, etc...)
    // Por lo tanto, sólo tenemos que sobrescribir estos métodos "doGet", "doPost", "doPut", etc..., de la clase padre "HttpServlet" para codificar nuestras implementaciones

    // Sirve un documento HTML
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Indicamos el tipo de contenido del cuerpo o body de la respuesta http
        resp.setContentType("text/html;charset=UTF-8");

        // Para poder añadir contenido al cuerpo o body de la respuesta
        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("    <head>");
        out.println("        <meta charset=\"UTF-8\">");
        out.println("        <title>Hola Mundo Servlet</title>");
        out.println("    </head>");
        out.println("    <body>");
        out.println("        <h1>Hola Mundo Servlet!</h1>");
        out.println("    </body>");
        out.println("</html>");

        out.close();
    }
}
