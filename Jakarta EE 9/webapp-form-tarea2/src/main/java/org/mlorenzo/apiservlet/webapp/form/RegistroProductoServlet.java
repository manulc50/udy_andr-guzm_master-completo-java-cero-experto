package org.mlorenzo.apiservlet.webapp.form;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

// Un Servlet es una clase que hereda la clase HttpServlet

@WebServlet("/crear") // Con esta anotación mapeamos este servlet con la ruta "/crear"
public class RegistroProductoServlet extends HttpServlet {

    // No se debe sobrescribir el método "service" de la clase padre "HttpServlet"
    // El método "service" se encarga de invocar a los métodos "doGet", "doPost", "doPut", etc..., dependiendo del tipo de la petición http que llegue(Get, Post, Put, etc...)
    // Por lo tanto, sólo tenemos que sobrescribir estos métodos "doGet", "doPost", "doPut", etc..., de la clase padre "HttpServlet" para codificar nuestras implementaciones


    // Despacha o redirige(forward) a la vista "form.jsp"
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/form.jsp").forward(req, resp);
    }

    // Procesa el formulario y sirve un documento HTML
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Obtiene los parámetros de los campos del formulario que viajan en el cuerpo o body de la petición http
        String nombre = req.getParameter("nombre");
        String fabricante = req.getParameter("fabricante");
        String categoria = req.getParameter("categoria");

        // Validación de los parámetros de los campos del formulario
        Map<String, String> errores = new HashMap<>();

        if(nombre == null || nombre.isBlank())
            errores.put("nombre","El nombre es requerido");

        int precio = 0;
        try {
            precio = Integer.parseInt(req.getParameter("precio"));
        }
        catch(NumberFormatException e) {
            errores.put("precio","El precio es requerido y debe tener un formato de número entero");
        }

        if(fabricante == null || fabricante.length() < 4 || fabricante.length() > 10)
            errores.put("fabricante","El fabricante es requerido y debe tener entre 4 y 10 caracteres");

        if(categoria == null || categoria.equals(""))
            errores.put("categoria","La categoría es requerida");

        if(errores.isEmpty()) {
			// Indicamos el tipo de contenido del cuerpo o body de la respuesta http
			resp.setContentType("text/html;charset=UTF-8");
			
            // Para poder añadir contenido al cuerpo o body de la respuesta
            // En vez de cerrar el PrintWriter de forma manual invocando a su método "close", como esta clase "PrintWriter" implementa la interfaz "Closeable",
            // podemos usar esta clase junto con un bloque "try-with-resources" para que de forma automática se cierre al final del bloque
            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("    <head>");
                out.println("        <meta charset=\"UTF-8\">");
                out.println("        <title>Resultado form</title>");
                out.println("    </head>");
                out.println("    <body>");
                out.println("        <h1>Resultado form</h1>");
                out.println("        <ul>");
                out.println("            <li>Nombre: " + nombre + "</li>");
                out.println("            <li>Precio: " + precio + "</li>");
                out.println("            <li>Fabricante: " + fabricante + "</li>");
                out.println("            <li>Categoría: " + categoria + "</li>");
                out.println("        </ul>");
                out.println("        <p><a href=\"/webapp-form-tarea2/form.jsp\">Volver</a></p>");
                out.println("    </body>");
                out.println("</html>");
            }
        }
        else{
            // Creamos el atributo "errores" en la petición http para usarlo en la vista "form.jsp" con los errores de la validación del formulario
            // Nota: Ahora, la vista "form" tiene que ser un JSP en vez de un HTML para que pueda soportar código Java y, de esta forma, podamos mostrar los errores en esa vista
            req.setAttribute("errores", errores);
            // Despacha o redirige(forward) a la vista "form.jsp"
            getServletContext().getRequestDispatcher("/form.jsp").forward(req, resp);
        }
    }
}
