public class WrapperOperadoresRelacionales {

    public static void main(String[] args) {
        Integer num1 = Integer.valueOf(1000); // Forma explicita - Boxing
        Integer num2 = num1; // El objeto "num2" tiene la misma referencia del objeto "num1"

        System.out.println("num1 = " + num1);
        System.out.println("num2 = " + num2);

        // Compara las referencias de los objetos y no los valores primitivos contenidos en ellos
        System.out.println("Son el mismo objeto? " + (num1 == num2));

        num2 = 1000; // Forma implicita - AutoBoxing

        System.out.println("num1 = " + num1);
        System.out.println("num2 = " + num2);

        // Nota: Java tiene una particularidad que consiste en que si los valores enteros primitivos de los objetos Wrapper son menores o iguales a 127, la comparación usando el operador == entre estos objetos se hace por valor y no por referencia.
        // Si superan ese valor, las comparaciones usando el operador == se hacen por referencia

        // Compara las referencias de los objetos y no los valores primitivos contenidos en ellos
        // Si los valores primitivos de los objetos Wrapper "num1" y "num2" son menores o iguales a 127, la comparación se raliza por valor y nopor referencia de objetos
        System.out.println("Son el mismo objeto? " + (num1 == num2));

        // Compara los valores primitivos contenidos en los objetos Wrapper
        System.out.println("Tienen el mismo valor? " + num1.equals(num2));

        // Compara los valores primitivos contenidos en los objetos Wrapper pero esta vez de manera explicita
        System.out.println("Tienen el mismo valor? " + (num1.intValue() == num2.intValue()));

        num2 = 2000; // Forma implicita - AutoBoxing

        boolean condicion1 = num1 > num2; // Realiza la comparación mediante AutoUnboxing ya que es la forma implicita
        System.out.println("condicion1 = " + condicion1);

        boolean condicion2 = num1.intValue() > num2.intValue(); // Realiza la comparación mediante Unboxing ya que es la forma explicita
        System.out.println("condicion2 = " + condicion2);

        boolean condicion3 = num1 < num2; // Realiza la comparación mediante AutoUnboxing ya que es la forma implicita
        System.out.println("condicion3 = " + condicion3);
    }
}
