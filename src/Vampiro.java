import java.util.ArrayList;

// Clase que representa a un Vampiro en el pueblo
public class Vampiro extends Ciudadano implements Batalla {
    private static int totalVampiros = 0; // Total de vampiros vivos
    private static int ultimoVampiro = 0; // Para asignar nombres únicos

    private final Vulnerable VULNERABLE = Vulnerable.LOBO; // Vulnerabilidad del vampiro

    // Constructor: asigna nombre único y aumenta el contador de vampiros
    public Vampiro() {
        super("VAMPIRO" + (++ultimoVampiro));
        totalVampiros++;
    }

    // Devuelve el total de vampiros vivos
    public static int getPoblacion() {
        return totalVampiros;
    }

    // Permite establecer el total de vampiros vivos
    public static void setPoblacion(int numero) {
        totalVampiros = numero;
    }

    // Lógica de combate: si el oponente es el vulnerable, pierde; si es humano, lo convierte; si no, gana
    @Override
    public Ciudadano combate(Ciudadano oponente) {
        System.out.println(nombre + " (Vampiro) se enfrenta a " + oponente.getNombre());

        if (oponente.getVulnerable() == VULNERABLE) {
            System.out.println(nombre + " es vulnerable a " + oponente.getNombre() + " y pierde el combate!");
            return this;
        } else if (oponente instanceof Humano) {
            System.out.println(oponente.getNombre() + " ha sido convertido en Vampiro por " + nombre);
            Vampiro nuevoVampiro = new Vampiro();
            nuevoVampiro.setNombre("EX-" + oponente.getNombre());
            return nuevoVampiro;
        }

        System.out.println(nombre + " gana el combate contra " + oponente.getNombre());
        return oponente;
    }

    // Elimina al vampiro de la lista y actualiza los contadores
    @Override
    public void morir(ArrayList<Ciudadano> ciudadanos) {
        System.out.println(nombre + " (Vampiro) ha muerto.");
        ciudadanos.remove(this);
        totalVampiros--;
        poblacion--;
    }

    // Devuelve a qué es vulnerable el vampiro
    @Override
    public Vulnerable getVulnerable() {
        return VULNERABLE;
    }

    @Override
    public String toString() {
        return super.toString() + " (Vampiro) - Vulnerable: " + VULNERABLE;
    }
}