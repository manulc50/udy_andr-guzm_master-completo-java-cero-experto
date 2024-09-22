package org.aguzman.apiservlet.webapp.headers.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;


@WebServlet("/cabeceras-request")
public class CabecerasHttpRequestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String methodHttp = req.getMethod();
        String requestUri = req.getRequestURI();
        String requestUrl = req.getRequestURL().toString();
        String contextPath = req.getContextPath(); // "/nombre_proyecto"
        String servletPath = req.getServletPath();
        String ipCliente = req.getRemoteAddr(); // IP del cliente que envía la petición http a este servlet
        String ip = req.getLocalAddr(); // IP de la máquina donde se ejecuta este servlet
        int port = req.getLocalPort();
        String scheme = req.getScheme(); // http/https
        String host = req.getHeader("host");
        String url = scheme + "://" + host + contextPath + servletPath;
        String url2 = scheme + "://" + ip + ":" + port + contextPath + servletPath;

        resp.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("    <head>");
            out.println("        <meta charset=\"UTF-8\">");
            out.println("        <title>Cabeceras HTTP Request</title>");
            out.println("    </head>");
            out.println("    <body>");
            out.println("        <h1>Cabeceras HTTP Request</h1>");
            out.println("        <ul>");
            out.println("            <li>Método Http: " + methodHttp + "</li>");
            out.println("            <li>Request Uri: " + requestUri + "</li>");
            out.println("            <li>Request Url: " + requestUrl + "</li>");
            out.println("            <li>Context Path: " + contextPath + "</li>");
            out.println("            <li>Servlet path: " + servletPath + "</li>");
            out.println("            <li>Local IP: " + ip + "</li>");
            out.println("            <li>Client IP: " + ipCliente + "</li>");
            out.println("            <li>Local Port: " + port + "</li>");
            out.println("            <li>Scheme(http/https): " + scheme + "</li>");
            out.println("            <li>Host: " + host + "</li>");
            out.println("            <li>Url: " + url + "</li>");
            out.println("            <li>Url2: " + url2 + "</li>");
            out.println("            <li>Cebeceras de la petición http: <ul>");

            Enumeration<String> headerNames = req.getHeaderNames();
            while(headerNames.hasMoreElements()) {
                String cabecera = headerNames.nextElement();
                out.println("                <li>" + cabecera + ": " + req.getHeader(cabecera) + "</li>");
            }
            out.println("            </ul></li>");
            out.println("        </ul>");
            out.println("    </body>");
            out.println("</html>");
        }
    }
}
