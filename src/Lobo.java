import java.util.ArrayList;
import java.util.Random;

// Clase que representa a un Lobo en el pueblo
public class Lobo extends Ciudadano implements Batalla, CicloVital {
    private static int totalLobos = 0; // Total de lobos vivos
    private static int ultimoLobo = 0; // Para asignar nombres únicos
    private static final Random ALEATORIO = new Random();

    private final Vulnerable VULNERABLE = Vulnerable.HUMANO; // Vulnerabilidad del lobo
    private int vida; // Vida actual del lobo

    // Constructor: asigna nombre único, vida aleatoria y aumenta el contador de lobos
    public Lobo() {
        super("LOBO" + (++ultimoLobo));
        this.vida = ALEATORIO.nextInt(VITALIDAD_MAXIMA * 2) + 1;
        totalLobos++;
    }

    // Devuelve el total de lobos vivos
    public static int getPoblacion() {
        return totalLobos;
    }

    // Permite establecer el total de lobos vivos
    public static void setPoblacion(int numero) {
        totalLobos = numero;
    }

    // Lógica de combate: si el oponente es el vulnerable, pierde; si no, gana
    @Override
    public Ciudadano combate(Ciudadano oponente) {
        System.out.println(nombre + " (Lobo) combate contra " + oponente.getNombre());

        if (oponente.getVulnerable() == VULNERABLE) {
            System.out.println(nombre + " es vulnerable a " + oponente.getNombre() + " y pierde el combate!");
            return this;
        }

        System.out.println(nombre + " gana el combate contra " + oponente.getNombre());
        return oponente;
    }

    // Reproducción: genera entre 1 y 2 cachorros y los añade a la lista
    @Override
    public void reproducir(ArrayList<Ciudadano> ciudadanos) {
        int hijos = ALEATORIO.nextInt(2) + 1;

        for (int i = 0; i < hijos; i++) {
            Lobo cachorro = new Lobo();
            ciudadanos.add(cachorro);
            System.out.println(nombre + " ha tenido un cachorro: " + cachorro.getNombre());
        }
    }

    // Elimina al lobo de la lista y actualiza los contadores
    @Override
    public void morir(ArrayList<Ciudadano> ciudadanos) {
        System.out.println(nombre + " (Lobo) ha muerto.");
        ciudadanos.remove(this);
        totalLobos--;
        poblacion--;
    }

    // Envejece al lobo, reduce su vida y lo elimina si llega a 0
    @Override
    public void envejecer(ArrayList<Ciudadano> ciudadanos) {
        vida--;
        System.out.println(nombre + " envejece. Vida restante: " + vida);
        if (vida <= 0) {
            morir(ciudadanos);
        }
    }

    // Devuelve a qué es vulnerable el lobo
    @Override
    public Vulnerable getVulnerable() {
        return VULNERABLE;
    }

    @Override
    public String toString() {
        return super.toString() + "  (Lobo) - Vida:" + vida + " - Vulnerable a: " + VULNERABLE;
    }
}
