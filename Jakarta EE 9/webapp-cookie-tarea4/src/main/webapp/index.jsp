<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Tarea 4: cambiar el color de los textos</title>
    </head>
    <body>
        <h3 style="color: ${cookie.color != null ? cookie.color.getValue() : 'black'}">Tarea 4: cambiar el color de los textos</h3>
        <p style="color: ${cookie.color != null ? cookie.color.getValue() : 'black'}">Bienvenidos! Este es un texto que cambia de color según la opción elegida del siguiente formulario</p>
        <!-- Si no se indica en el formulario el tipo de método de la petición http, por defecto es de tipo "get" -->
        <form action="/webapp-cookie-tarea4/cambiar-color" method="get">
            <div>
                <label for="color">Color:</label>
                <div>
                    <select name="color" id="color">
                        <option value="blue">Azul</option>
                        <option value="red">Rojo</option>
                        <option value="green">Verde</option>
                        <option value="aqua">Aqua</option>
                        <option value="BlueViolet">Violeta</option>
                        <option value="Gray">Gris</option>
                        <option value="Cyan">Cyan</option>
                    </select>
                </div>
            </div>
            <div>
                <input type="submit" value="Cambiar">
            </div>
        </form>
    </body>
</html>