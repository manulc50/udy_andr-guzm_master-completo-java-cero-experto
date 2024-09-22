public class AutoboxingInteger {

    public static void main(String[] args) {

        // Array de enteros Wrapper. Se crea mediante Autoboxing ya que es la forma implicita
        Integer[] enteros = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};

        // Bucle for que suma los enteros primitivos pares de los enteros Wrapper almacenados en el array anterior
        // Forma explicita que implica realizar Unboxing
        int suma = 0;
        for(Integer i: enteros)
            if(i.intValue() % 2 == 0) // Verifica el resto de la división
                suma += i.intValue(); // Equivalente a suma = suma + i.intValue();

        System.out.println("suma = " + suma);

        // Forma implicita que implica realizar Autounboxing
        suma = 0;
        for(Integer i: enteros)
            if(i % 2 == 0) // Verifica el resto de la división
                suma += i; // Equivalente a suma = suma + i;

        System.out.println("suma = " + suma);
    }
}
