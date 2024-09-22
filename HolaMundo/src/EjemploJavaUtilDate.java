import javax.lang.model.SourceVersion;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EjemploJavaUtilDate {

    public static void main(String[] args) {
        Date fecha = new Date(); // Fecha y hora actual del sistema

        // Muestra por consola la fecha y hora actual del sistema
        System.out.println("fecha = " + fecha);

        // Objeto para dar formato a una fecha
        // Creamos el formato de fecha "EEEE dd 'de' MMMM, yyyy"
        // "EEEE" - Nombre del día de la semana; "dd" - Número del día del mes con 2 dígitos; "MMMM" - Nombre del mes; "yyyy" - Los 4 dígitos del año
        // Ver la documentación de la clase SimpleDateFormat para ver los distintos tipos de formatos o patrones de fechas
        SimpleDateFormat df = new SimpleDateFormat("EEEE dd 'de' MMMM, yyyy");

        // Establecemos el formato anterior en la fecha
        String fechaStr = df.format(fecha);

        // Mostramos por consola la fecha actual del sistema formateada
        System.out.println("fechaStr = " + fechaStr);

        // Bucle for de ejemplo para calcular el tiempo de ejecución
        long j = 0;
        for(int i = 0; i < 10000000; i++)
            j += i;
        System.out.println("j = " + j);

        Date fecha2 = new Date(); // Fecha y hora actual del sistema
        // El método "getTime" nos da una fecha en milisegundos con respecto a la fecha January 1, 1970
        // Calculamos el tiempo de ejecución en milisegundos del bucle for anterior
        long tiempoFinal = fecha2.getTime() - fecha.getTime();
        System.out.println("Tiempor transcurrido en el bucle for = " + tiempoFinal);
    }
}
