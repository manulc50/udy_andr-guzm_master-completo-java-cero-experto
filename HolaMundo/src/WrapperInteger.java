public class WrapperInteger {

    public static void main(String[] args) {
        // Nota: La creación de cualquier objeto Wrapper usando el operador new(Por ejemplo, new Integer(32760)) está obsoleto o deprecated

        // Pasar un entero primitivo a un entero Wrapper
        int intPrimitivo = 32768;
        Integer intObjeto = Integer.valueOf(intPrimitivo); // Esta manera explicita de crear objetos Wrapper a partir de primitivos se denomina Boxing
        Integer intObjeto2 = intPrimitivo; // Esta manera implicita de crear objetos Wrapper a partir de primitivos se denomina Autoboxing
        System.out.println("intObjeto = " + intObjeto);

        // Pasar un entero Wrapper a un entero primitivo
        int num = intObjeto.intValue(); // Esta manera explicita de crear primitivos a partir de objetos Wrapper se denomina Unboxing
        System.out.println("num = " + num);
        int num2 = intObjeto; // Esta manera implicita de crear primitivos a partir de objetos Wrapper se denomina Autounboxing
        System.out.println("num2 = " + num2);

        // Pasar un string que representa un entero a un entero Wrapper
        String valorTvLcd = "67000";
        Integer valor = Integer.valueOf(valorTvLcd);
        System.out.println("valor = " + valor);

        // Creación de un Wrapper de tipo Short a partir del valor de un Wrapper de tipo Integer
        // En este caso se produce una perdida de información porque el valor 32768 del entero Wrapper "intObjeto" es mayor al valor máximo que permite un Short
        // Si el valor del entero Wrapper está dentro del rango de valores permitido por un Short, en este caso no se pierde información
        Short shortObjecto = intObjeto.shortValue();
        System.out.println("shortObjecto = " + shortObjecto);

        // Creación de un Wrapper de tipo Byte a partir del valor de un Wrapper de tipo Integer
        // En este caso se produce una perdida de información porque el valor 32768 del entero Wrapper "intObjeto" es mayor al valor máximo que permite un Byte
        // Si el valor del entero Wrapper está dentro del rango de valores permitido por un Byte, en este caso no se pierde información
        Byte byteObjecto = intObjeto.byteValue();
        System.out.println("byteObjecto = " + byteObjecto);

        // Creación de un Wrapper de tipo Long a partir del valor de un Wrapper de tipo Integer
        // Como el rango de valores permitidos en un Long siempre es mayor a los valores de un Integer, en este caso nunca se produce perdida de información en la conversión
        Long longObjecto = intObjeto.longValue();
        System.out.println("longObjecto = " + longObjecto);
    }
}
