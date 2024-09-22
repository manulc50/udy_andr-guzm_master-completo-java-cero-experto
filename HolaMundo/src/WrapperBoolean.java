public class WrapperBoolean {

    public static void main(String[] args) {
        Integer num1, num2;
        num1 = 1; // Forma implicita - Autoboxing
        num2 = 2; // Forma implicita - Autoboxing

        boolean primBoolean = num1 > num2; // Realiza la comparación mediante AutoUnboxing ya que es la forma implicita
        Boolean boolObjecto1 = Boolean.valueOf(primBoolean); // Forma explicita - Boxing
        Boolean boolObjecto2 = Boolean.valueOf("false"); // Forma explicita - Boxing
        Boolean boolObjecto3 = true; // Forma implicita - AutoBoxing

        System.out.println("primBoolean = " + primBoolean);
        System.out.println("boolObjecto1 = " + boolObjecto1);
        System.out.println("boolObjecto2 = " + boolObjecto2);

        // A diferencia de los objetos numéricos, los objetos boolean se comparan siempre por valor
        System.out.println("Comparando dos objeto boolean = " + (boolObjecto1 == boolObjecto2));
        // Si la comparación se realiza usando el método "equals", los objetos boolean también se comparan por valor
        System.out.println("Comparando dos objeto boolean = " + (boolObjecto1 == boolObjecto2));
        System.out.println("Comparando dos objeto boolean = " + (boolObjecto2 == boolObjecto3));
        System.out.println("Comparando dos objeto boolean = " + (boolObjecto1 == boolObjecto3));

        // Pasar un objeto Wrapper de tipo Boolean a un boolean primitivo
        boolean primBoolean2 = boolObjecto2.booleanValue(); // Forma explicita - Unboxing
        boolean primBoolean3 = boolObjecto3; // Forma implicita - AutoUnboxing

        System.out.println("primBoolean2 = " + primBoolean2);
        System.out.println("primBoolean3 = " + primBoolean3);
    }
}
