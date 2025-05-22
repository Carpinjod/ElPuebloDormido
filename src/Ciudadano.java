import java.util.ArrayList;

// Clase abstracta base para los ciudadanos del pueblo
public abstract class Ciudadano {
    protected String nombre; // Nombre del ciudadano
    protected static int poblacion = 0; // Población total

    // Constructor: asigna nombre e incrementa la población
    public Ciudadano(String nombre) {
        this.nombre = nombre;
        poblacion++;
    }

    // Devuelve la población total
    public static int getPoblacion() {
        return poblacion;
    }

    // Permite establecer la población total
    public static void setPoblacion(int numero) {
        Ciudadano.poblacion = numero;
    }

    // Devuelve el nombre del ciudadano
    public String getNombre() {
        return nombre;
    }

    // Cambia el nombre del ciudadano
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Nombre del ciudadano: " + nombre;
    }

    // Muestra el censo de ciudadanos y la población total
    public static void censar(ArrayList<Ciudadano> ciudadanos) {
        System.out.println("--- CENSO ---");
        for (Ciudadano c : ciudadanos) {
            System.out.println(c);
        }
        System.out.println("Población total: " + getPoblacion());
    }

    // Muestra el total de ciudadanos por tipo
    public static void poblacionesTotales(ArrayList<Ciudadano> ciudadanos) {
        int humanos = 0;
        int lobos = 0;
        int vampiros = 0;

        for (Ciudadano c : ciudadanos) {
            if (c instanceof Humano) humanos++;
            else if (c instanceof Lobo) lobos++;
            else if (c instanceof Vampiro) vampiros++;
        }
        System.out.println("Total ciudadanos: " + ciudadanos.size());
        System.out.println("Humanos: " + humanos + ", Lobos: " + lobos + ", Vampiros: " + vampiros);
    }

    // Métodos abstractos a implementar por las subclases
    public abstract void morir(ArrayList<Ciudadano> ciudadanos);
    public abstract Ciudadano combate(Ciudadano oponente);
    public abstract Vulnerable getVulnerable();
}