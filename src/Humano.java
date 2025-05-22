import java.util.ArrayList;
import java.util.Random;

// Clase final que representa a un Humano en el pueblo
public final class Humano extends Ciudadano implements Batalla, CicloVital {
    private static int totalHumanos = 0; // Total de humanos vivos
    private static int ultimoHumano = 0; // Para asignar nombres únicos
    private static final Random ALEATORIO = new Random();
    private static final int VITALIDAD_MAXIMA = 2; // Vida máxima posible
    private Vulnerable VULNERABLE = Vulnerable.VAMPIRO; // Vulnerabilidad del humano
    private int vida; // Vida actual del humano

    // Constructor: asigna nombre único, vida aleatoria y aumenta el contador de humanos
    public Humano() {
        super("Humano" + (++ultimoHumano));
        this.vida = ALEATORIO.nextInt(VITALIDAD_MAXIMA) + 1;
        totalHumanos++;
    }

    // Devuelve el total de humanos vivos
    public static int getPoblacion() {
        return totalHumanos;
    }

    // Permite establecer el total de humanos vivos
    public static void setPoblacion(int numero) {
        totalHumanos = numero;
    }

    // Lógica de combate: si el oponente es el vulnerable, pierde; si es vampiro, se convierte; si no, gana
    @Override
    public Ciudadano combate(Ciudadano oponente) {
        System.out.println(nombre + " (HUMANO) combate contra " + oponente.getNombre());

        if (oponente.getVulnerable() == VULNERABLE) {
            System.out.println(nombre + " es vulnerable a " + oponente.getNombre() + " y pierde el combate!");
            return this;
        } else if (oponente instanceof Vampiro) {
            System.out.println(nombre + " ha sido convertido en vampiro por " + oponente.getNombre());

            Vampiro nuevoVampiro = new Vampiro();
            nuevoVampiro.setNombre("EX-" + nombre);
            return nuevoVampiro;
        }

        System.out.println(nombre + " gana el combate contra " + oponente.getNombre());
        return oponente;
    }

    // Reproducción: con probabilidad 50%, crea un nuevo humano y lo añade a la lista
    @Override
    public void reproducir(ArrayList<Ciudadano> ciudadanos) {
        if (ALEATORIO.nextBoolean()) {
            Humano hijo = new Humano();
            ciudadanos.add(hijo);
            System.out.println(nombre + " ha tenido un hijo: " + hijo.getNombre());
        }
    }

    // Elimina al humano de la lista y actualiza los contadores
    @Override
    public void morir(ArrayList<Ciudadano> ciudadanos) {
        System.out.println(nombre + " (HUMANO) ha muerto.");
        ciudadanos.remove(this);
        totalHumanos--;
        poblacion--;
    }

    // Envejece al humano, reduce su vida y lo elimina si llega a 0
    @Override
    public void envejecer(ArrayList<Ciudadano> ciudadanos) {
        vida--;
        System.out.println(nombre + " envejece. Vida restante: " + vida);
        if (vida <= 0) {
            morir(ciudadanos);
        }
    }

    // Devuelve a qué es vulnerable el humano
    @Override
    public Vulnerable getVulnerable() {
        return VULNERABLE;
    }

    @Override
    public String toString() {
        return super.toString() + "(Humano) - Vida:" + vida + " - Vulnerable a =" + VULNERABLE;
    }
}