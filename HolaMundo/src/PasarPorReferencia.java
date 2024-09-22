public class PasarPorReferencia {

    // El paso de argumento por valor se realiza pasando como argumento de entrada a un método un valor primitivo(No modifica el valor)
    // El paso de argumento por referencia se realiza pasando como argumento de entrada a un método un objeto que no sea un Wrapper de Java(Modifica el valor)

    // Nota: Los objetos que son objetos Wrapper de Java(Integer, Long, String, etc...) también se pasan como argumento a un método por valor y no por referencia debido a que son objetos inmutables

    public static void main(String[] args) {
        // "edades" es un array y un array es un objeto mutable en Java
        int[] edades = {10, 11, 12};

        System.out.println("En el método main antes de invocar al método test");
        for(int i = 0; i < edades.length; i++)
            System.out.println("edades[i] = " + edades[i]);
        // Paso de argumento por referenica - Modifica el contenido de "edades"
        test(edades);
        System.out.println("En el método main después de invocar al método test");
        for(int i = 0; i < edades.length; i++)
            System.out.println("edades[i] = " + edades[i]);

    }
    public static void test(int[] edadArr){
        System.out.println("Iniciamos el método test");
        for(int i = 0; i < edadArr.length; i++)
            edadArr[i] += 20; // Equivalente a edadArr[i] = edadArr[i] + 20;
        System.out.println("Finaliza el método test");
    }
}
