package org.aguzman.poo.excepciones.ejemplo;

// Nota: Las excepciones de tipo "unchecked" son aquellas que extienden(o heredan) de la excepción "RuntimeException"
// Estas excepciones no obligan a que sean manejadas o tratadas con bloques "try-catch" y tampoco obligan a que sean lanzadas hacia un nivel superior con "throw" y "throws"

// Nota: Una excepción que hereda o extiende de "Exception" es una excepción de tipo "checked"
// Estas excepciones obligan a manejarlas con bloques "try-catch" o a lanzarlas a un nivel superior usando "throw" y "throws"

// Nota: Si una excepción llega al nivel de la clase "main"(clase que contiene el método principal "main") y no se maneja y se lanza,
//       esa excepción será tratada por la JVM(Java Virtual Machine)

import javax.swing.*;

public class EjemploExcepcionesCalculadora {

    public static void main(String[] args) {

        Calculadora cal = new Calculadora();
        String numeradorStr = JOptionPane.showInputDialog("Ingrese un entero para el numerador:");
        String divisorStr = JOptionPane.showInputDialog("Ingrese un entero para el divisor:");

        // Manejo obligatorio de las excepciones "DivisionPorZeroException" y "FormatoNumeroException"
        // Son obligatorias porque heredan o extienden de "Exception" y, por lo tanto, son de tipo "checked"
        try {
            double division = cal.dividir(numeradorStr, divisorStr);
            System.out.println("division = " + division);
        }
        catch(FormatoNumeroException e) { // Por si el usuario introduce un numerador o un divisor que no son datos numéricos
            System.out.println("Se detecto una excepción, por favor ingrese un número válido: " + e.getMessage());
            e.printStackTrace();
        }
        catch(DivisionPorZeroException e) { // Por si el usario introduce el valor numérico 0
            System.out.println("Capturamos la excepción en tiempo de ejecución: " + e.getMessage());
            e.printStackTrace();
        }
        finally { // Es una parte opcional del bloque "try-catch" y se ejecuta siempre, tanto si ha ido bien como si ha ido mal
            System.out.println("Es opcional y se ejecuta simepre, con o sin excepción");
        }
        System.out.println("Continuamos con el flujo de nuestra aplicación!");
    }
}
