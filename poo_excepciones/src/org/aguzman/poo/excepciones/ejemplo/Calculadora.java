package org.aguzman.poo.excepciones.ejemplo;

public class Calculadora {

    public double dividir(int numerador, int divisor) throws DivisionPorZeroException {
        // Crea y lanza una excepción de tipo "DivisionPorZeroException" que es "checked"
        // Nos obliga a manejarla con "try-catch" o a lanzarla a un nivel superior con "throws"(elegimos esta última opción)
        if(divisor == 0)
            throw new DivisionPorZeroException("no se puede dividir por cero!");

        return numerador/(double)divisor;
    }

    public double dividir(String numerador, String divisor) throws DivisionPorZeroException, FormatoNumeroException {
        // Manejo opcional de la excepción "NumberFormatException"
        // Es opcional "NumberFormatException" porque hereda o extiende de "RuntimeException" y, por lo tanto, es de tipo "unchecked"
        try {
            int num = Integer.parseInt(numerador);
            int div = Integer.parseInt(divisor);

            // Lanza una excepción de tipo "DivisionPorZeroException" que es "checked"
            // Nos obliga a manejarla con "try-catch" o a lanzarla a un nivel superior con "throws"(elegimos esta última opción)
            return this.dividir(num, div);
        }
        catch(NumberFormatException e) {
            // Crea y lanza una excepción de tipo "FormatoNumeroException" que es "checked"
            // Nos obliga a manejarla con "try-catch" o a lanzarla a un nivel superior con "throws"(elegimos esta última opción)
            throw new FormatoNumeroException("debe ingresar un dato numérico en el numerador y divisor");
        }

    }
}
