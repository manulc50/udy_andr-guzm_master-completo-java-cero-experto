class Persona{
    private String nombre;

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return this.nombre;
    }
}

public class PasarPorReferencia2 {

    // El paso de argumento por valor se realiza pasando como argumento de entrada a un método un valor primitivo(No modifica el valor)
    // El paso de argumento por referencia se realiza pasando como argumento de entrada a un método un objeto que no sea un Wrapper de Java(Modifica el valor)

    // Nota: Los objetos que son objetos Wrapper de Java(Integer, Long, String, etc...) también se pasan como argumento a un método por valor y no por referencia debido a que son objetos inmutables

    public static void main(String[] args) {
        // Creamos un objeto de nuestra clase personalizada Persona
        Persona persona = new Persona();
        persona.setNombre("Andrés");

        System.out.println("En el método main antes de invocar al método test");
        System.out.println("El nombre de la persona es " + persona.getNombre());

        // Paso de argumento por referenica - Modifica la propiedad "nombre" del objeto "persona"
        test(persona);
        System.out.println("En el método main después de invocar al método test");
        System.out.println("El nombre de la persona es " + persona.getNombre());

    }
    public static void test(Persona persona){
        System.out.println("Iniciamos el método test");
        persona.setNombre("Pepe");
        System.out.println("Finaliza el método test");
    }
}
