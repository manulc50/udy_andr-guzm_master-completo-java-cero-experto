package org.aguzman.apiservlet.webapp.session.listeners;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.aguzman.apiservlet.webapp.session.models.Carro;

// Un listener que implementa la interfaz "ServletContextListener" afecta globalmente a toda la aplicación
// Un listener que implementa la interfaz "ServletRequestListener" afecta a cada petición http que se haga
// Un listener que implementa la interfaz "HttpSessionListener" afecta a la sesión http

@WebListener // Anotación que hace que una clase de Java sea un Listener del API Servlet. Opcionalmente, se le puede dar un nombre al listener a través de esta anotación
public class AplicacionListener implements ServletContextListener, ServletRequestListener, HttpSessionListener {

    private ServletContext servletContext;

    // Cada vez que se despliega la aplicación en el servidor(deploy), se ejecuta este método
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        servletContext = sce.getServletContext();
        servletContext.log("inicializando la aplicación");
        // Este atributo puede ser accedido de forma global en toda la aplicación, es decir, se crea un singleton que puede ser usado en cualquier parte de la aplicación
        servletContext.setAttribute("mensaje", "algún valor global de la app!");
    }

    // Cada vez que se elimina la aplicación del servidor(undeply), se ejecuta este método
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //sce.getServletContext().log("destruyendo la aplicación");
        servletContext.log("destruyendo la aplicación");
    }

    // Cuando se inicia una petición http, se ejecuta este método
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        //sre.getServletContext().log("inicializando el request");
        servletContext.log("inicializando el request");
        // Este atributo sólo es visible para cada petición http que se cree, es decir, el atributo se crea y se destruye por cada petición http
        sre.getServletRequest().setAttribute("mensaje", "guardando algún valor para el request!");
    }

    // Cuando finaliza una petición http, se ejecuta este método
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        //sre.getServletContext().log("destruyendo el request");
        servletContext.log("destruyendo el request");
    }

    // Cuando se crea la sesión http, se ejecuta este método
    // Nota: Un objeto de la clase "HttpSessionEvent" no puede acceder al contexto del servlet mediante el método "getServletContext". Sólo puede acceder a la sesión http
    // Por esta razón, si se necesita acceder al contexto del servlet en alguno de estos métodos de abajo, podemos usar una propiedad de la clase
    // que obtenga el contexto del servlet desde alguno de los métodos anteriores, ya que los argumentos de entrada de esos métodos sí pueden
    // acceder al contexto del servlet usando el método "getServletContext"
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        servletContext.log("inicializando la sesión http");
        // Cada vez que se cree una nueva sesión, se creará un atributo de esa sesión con el carro de la compra
        Carro carro = new Carro();
        HttpSession session = se.getSession();
        session.setAttribute("carro", carro);
    }

    // Cuando se elimina la sesión http, se ejecuta este método
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        servletContext.log("destruyendo la sesión http");
    }
}
