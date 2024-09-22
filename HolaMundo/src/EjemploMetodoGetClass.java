import java.lang.reflect.Method;

public class EjemploMetodoGetClass {

    public static void main(String[] args) {
        String texto = "Hola qué tal";

        // Con el método "getClass" de un objeto hacemos reflexión, es decir, descubrimos la estructura interna de la clase a la que pertenece ese objeto
        Class strClass = texto.getClass();
        // Mostramos por consola el nombre de la clase del objeto "texto" junto con el nombre del paquete al que pertenece esa clase
        System.out.println("strClass.getName() = " + strClass.getName());
        // Mostramos por consola el nombre de la clase del objeto "texto" sin el nombre del paquete al que pertenece esa clase
        System.out.println("strClass.getSimpleName() = " + strClass.getSimpleName());
        // Mostramos por consola el nombre del paquete al que pertenece la clase del objeto "texto"
        System.out.println("strClass.getPackageName() = " + strClass.getPackageName());
        // Muestra por consola el texto que devuelva en segundo plano el método "toString" del objeto "strClass"
        System.out.println("strClass = " + strClass);

        // Recorremos los métodos de la clase del objeto "texto" y mostramos su nombre por consola
        for(Method method: strClass.getMethods())
            System.out.println("method.getName() = " + method.getName());

        Integer num = 34;
        Class intClass = num.getClass();
        // Muestra por consola el texto que devuelva en segundo plano el método "toString" del objeto "intClass"
        System.out.println("intClass = " + intClass);
        // Mostramos por consola el nombre de la clase del objeto "intClass" junto con el nombre del paquete al que pertenece esa clase
        System.out.println("intClass.getName() = " + intClass.getName());
        // Mostramos por consola el nombre de la clase del objeto "intClass" sin el nombre del paquete al que pertenece esa clase
        System.out.println("intClass.getSimpleName() = " + intClass.getSimpleName());
        // Muestra por consola el texto que devuelva en segundo plano el método "toString" del objeto de tipo Class devuelto por el método "getPackage"
        System.out.println("intClass.getPackage() = " + intClass.getPackage());
        // Mostramos por consola el nombre del paquete al que pertenece la clase del objeto "intClass"
        System.out.println("intClass.getPackage().getName() = " + intClass.getPackage().getName());
        // Obtenemos la clase padre(Clase Object) de la clase padre(Clase Number) de la clase del objeto "intClass"
        Class objClass = intClass.getSuperclass().getSuperclass();
        // Muestra por consola el texto que devuelva en segundo plano el método "toString" del objeto de tipo Class de la clase padre del objeto "intClass"
        System.out.println("intClass.getSuperclass() = " + intClass.getSuperclass());
        // Muestra por consola el texto que devuelva en segundo plano el método "toString" del objeto de clase Object
        System.out.println("objClass = " + objClass);

        // Recorremos los métodos de la clase del objeto "objClass", que es la clase Object y mostramos su nombre por consola
        for(Method method: objClass.getMethods())
            System.out.println("method.getName() = " + method.getName());


    }
}
