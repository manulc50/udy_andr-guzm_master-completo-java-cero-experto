package org.aguzman.poo.excepciones.ejemplo;

// Excepción de tipo "checked" porque hereda de "Exception"

public class FormatoNumeroException extends Exception {

    public FormatoNumeroException(String mensaje) {
        super(mensaje);
    }
}
