package org.aguzman.poo.excepciones.ejemplo;

// Excepción de tipo "checked" porque hereda de "Exception"

public class DivisionPorZeroException extends Exception {

    public DivisionPorZeroException(String mensaje) {
        super(mensaje);
    }
}
