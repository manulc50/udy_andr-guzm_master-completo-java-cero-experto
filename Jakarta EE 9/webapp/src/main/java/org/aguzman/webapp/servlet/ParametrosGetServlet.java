package org.aguzman.webapp.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;


// Un Servlet es una clase que hereda la clase HttpServlet

@WebServlet("/parametros/url-get") // Con esta anotación mapeamos este servlet con la ruta "parametros/url-get"
public class ParametrosGetServlet extends HttpServlet {

    // No se debe sobrescribir el método "service" de la clase padre "HttpServlet"
    // El método "service" se encarga de invocar a los métodos "doGet", "doPost", "doPut", etc..., dependiendo del tipo de la petición http que llegue(Get, Post, Put, etc...)
    // Por lo tanto, sólo tenemos que sobrescribir estos métodos "doGet", "doPost", "doPut", etc..., de la clase padre "HttpServlet" para codificar nuestras implementaciones

	// Sirve un documento HTML
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Obtiene los parámetros "saludo" y "nombre" de la url de la petición http
        String saludo = req.getParameter("saludo");
        String nombre = req.getParameter("nombre");

        // Indicamos el tipo de contenido del cuerpo o body de la respuesta http
        resp.setContentType("text/html;charset=UTF-8");

        // Para poder añadir contenido al cuerpo o body de la respuesta
        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("    <head>");
        out.println("        <meta charset=\"UTF-8\">");
        out.println("        <title>Parámetros Get de la url</title>");
        out.println("    </head>");
        out.println("    <body>");
        out.println("        <h1>Parámetros Get de la url!</h1>");
        if(saludo != null && nombre != null)
            out.println("        <h2>El saludo enviado es: " + saludo + " " + nombre + "</h2>");
        else if(saludo != null)
            out.println("        <h2>El saludo enviado es: " + saludo + "</h2>");
        else if(nombre != null)
            out.println("        <h2>Hola mi nombre es: " + nombre + "</h2>");
        else
            out.println("        <h2>No se han pasado los parámetros \"saludo\" ni \"nombre\"</h2>");
        try {
            // Obtiene el parámetro "codigo" de la url de la petición http
            int codigo = Integer.parseInt(req.getParameter("codigo"));
            out.println("        <h3>El código enviado es: " + codigo + "</h3>");
        }
        catch(NumberFormatException e) {
            out.println("        <h3>El código no se ha enviado, es null</h3>");
        }
        out.println("    </body>");
        out.println("</html>");
		
		out.close();

    }
}
