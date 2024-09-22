public class PasarPorValor {

    // El paso de argumento por valor se realiza pasando como argumento de entrada a un método un valor primitivo(No modifica el valor)
    // El paso de argumento por referencia se realiza pasando como argumento de entrada a un método un objeto que no sea un Wrapper de Java(Modifica el valor)

    // Nota: Los objetos que son objetos Wrapper de Java(Integer, Long, String, etc...) también se pasan como argumento a un método por valor y no por referencia debido a que son objetos inmutables

    public static void main(String[] args) {
        int i = 10;
        Integer x = 10;

        System.out.println("En el método main antes de invocar al método test con i = " + i);
        // Paso de argumento por valor - No modifica el valor de "i"
        test(i);
        System.out.println("En el método main después de invocar al método test con i(se mantiene igual)  = " + i);
        System.out.println("En el método main antes de invocar al método test con x = " + x);
        // Aunque "x" es un objeto, el valor se pasa al método por valor y no por referencia porque se trata de un objeto de una clase Wrapper de Java y los objetos Wrapper son inmutables
        test(x);
        System.out.println("En el método main después de invocar al método test con x(se mantiene igual) = " + x);
    }

    public static void test(int i){
        System.out.println("Iniciamos el método test con i = " + i);
        i = 35;
        System.out.println("Finaliza el método test con i = " + i);
    }
}
