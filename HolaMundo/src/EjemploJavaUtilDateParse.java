import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class EjemploJavaUtilDateParse {

    public static void main(String[] args) {

        // Objeto para dar formato a una fecha
        // Creamos el formato de fecha "yyyy-MM-dd"
        // "yyyy" - Los 4 dígitos del año; "MM" - Los 2 dígitos del mes del año; "dd" - Los 2 dígitos del día del mes
        // Ver la documentación de la clase SimpleDateFormat para ver los distintos tipos de formatos o patrones de fechas
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Scanner s = new Scanner(System.in); // Para pedir al usuario que introduzca datos desde la línea de comandos

        System.out.println("Introduce una fecha con formato 'yyyy-MM-dd'");
        try {
            // Pasamos la fecha introducida por el usuario como cadena de texto a una fecha de tipo Date
            // La fecha tiene que respetar el formato o patrón indicado en el objeto de tipo SimpleDateFormat
            // El método "parse" lanza la exceptión "ParseException" cuando la fecha es incorrecta y no se puede parsear o convertir
            Date fecha = format.parse(s.next());
            System.out.println("fecha = " + fecha); // Muestra por consola la fecha sin formato
            System.out.println("format = " + format.format(fecha)); // Muestra por consola la fecha con formato

            Date fecha2 = new Date(); // Fecha y hora actual del sistema
            System.out.println("fecha2 = " + fecha2);

            // Una manera de comparar fechas
            // Verificamos si la fecha introducida por el usuario es posterior, anterior o igual a la fecha actual del sistema
            if(fecha.after(fecha2))
                System.out.println("La fecha del usuario es posterior a la fecha actual del sistema");
            else if(fecha.before(fecha2))
                System.out.println("La fecha del usuario es anterior a la fecha actual del sistema");
            else
                System.out.println("La fecha del usuario es igual a la fecha actual del sistema");

            // Otra manera de comparar fechas usando el método compareTo
            // Verificamos si la fecha introducida por el usuario es posterior, anterior o igual a la fecha actual del sistema
            int result = fecha.compareTo(fecha2);
            if(result > 0)
                System.out.println("La fecha del usuario es posterior a la fecha actual del sistema");
            else if(result < 0)
                System.out.println("La fecha del usuario es anterior a la fecha actual del sistema");
            else
                System.out.println("La fecha del usuario es igual a la fecha actual del sistema");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
