import java.util.ArrayList;
import java.util.Random;

// Clase principal que simula el juego "El Pueblo Dormido"
public class ElPuebloDormido {
    // Constantes para la población mínima y máxima inicial
    private static final int POBLACION_MINIMA = 5;
    private static final int POBLACION_MAXIMA = 20;
    // Lista de ciudadanos que habitan el pueblo
    private static ArrayList<Ciudadano> ciudadanos = new ArrayList<>();
    // Generador de números aleatorios
    private static final Random ALEATORIO = new Random();

    // Método principal: inicia el juego y controla el ciclo principal
    public static void main(String[] args) {
        System.out.println("=== BIENVENIDO A EL PUEBLO DORMIDO ===");
        generarPoblacionAleatoria();

        boolean continuar = true;
        while (continuar) {
            try {
                continuar = mostrarMenu(continuar);
                verificarPoblacion();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // Muestra el menú principal del juego y gestiona la opción seleccionada por el usuario.
    public static boolean mostrarMenu(boolean continuar) throws Exception {
        System.out.println("=== MENÚ ===");
        System.out.println("1. Mostrar Censo");
        System.out.println("2. Pasar un año");
        System.out.println("3. Salir del programa");
        System.out.println("Seleccione una opción");

        int opcion = new java.util.Scanner(System.in).nextInt();

        if (opcion == 1) {
            // Muestra el censo de ciudadanos y las poblaciones totales por tipo
            Ciudadano.censar(ciudadanos);
            Ciudadano.poblacionesTotales(ciudadanos);
        } else if (opcion == 2) {
            // Simula el paso de un año en el pueblo
            pasarAnyo();
        } else if (opcion == 3) {
            // Sale del programa
            System.out.println("Saliendo del programa...");
            continuar = false;
        } else {
            // Opción no válida
            System.out.println("Opción no válida.");
        }
        return continuar;
    }

    // Genera una población inicial aleatoria de ciudadanos
    public static void generarPoblacionAleatoria() {
        int poblacionInicial = ALEATORIO.nextInt(POBLACION_MAXIMA - POBLACION_MINIMA + 1) + POBLACION_MINIMA;

        for (int i = 0; i < poblacionInicial; i++) {
            ciudadanos.add(obtenerCiudadanoAleatorio());
        }

        System.out.println("Se ha generado una población inicial de " + poblacionInicial + " ciudadanos.");

    }

    // Devuelve un ciudadano aleatorio (Humano, Lobo o Vampiro)
    public static Ciudadano obtenerCiudadanoAleatorio() {
        int tipo = ALEATORIO.nextInt(3);
        switch (tipo) {
            case 0:
                return new Humano();
            case 1:
                return new Lobo();
            default:
                return new Vampiro();
        }
    }

    // Simula el paso de un año: cada ciudadano interactúa con otro y envejece si corresponde
    public static void pasarAnyo() throws Exception {
        if (ciudadanos.isEmpty()) {
            throw new IllegalArgumentException("No hay ciudadanos disponibles.");
        }

        System.out.println("=== PASA UN AÑO ===");

        // Copio la lista para evitar problemas al modificarla durante la iteración
        ArrayList<Ciudadano> copia = new ArrayList<>(ciudadanos);

        for (int i = 0; i < copia.size(); i++) {
            Ciudadano ciudadano = copia.get(i);
            Ciudadano oponente = obtenerOponenteAleatorio(ciudadanos.indexOf(ciudadano));
            realizarAccion(ciudadano, oponente);
            actualizarEdad(ciudadano);
        }
    }

    // Obtiene un oponente aleatorio distinto al ciudadano actual
    public static Ciudadano obtenerOponenteAleatorio(int actual) {
        if (ciudadanos.size() <= 1) {
            throw new IllegalArgumentException("No hay ningún oponente disponible.");
        }

        int oponente;
        do {
            oponente = ALEATORIO.nextInt(ciudadanos.size());
        } while (oponente == actual);

        return ciudadanos.get(oponente);
    }

    // Realiza la acción entre dos ciudadanos: combate o reproducción según el caso
    public static void realizarAccion(Ciudadano ciudadano1, Ciudadano ciudadano2) {
        if (ciudadano1 == null || ciudadano2 == null) {
            throw new IllegalArgumentException("Los oponentes no pudn ser null.");
        }

        // Si son del mismo tipo, intentan reproducirse (excepto vampiros)
        if (ciudadano1.getClass() == ciudadano2.getClass()) {
            if (ciudadano1 instanceof Humano) {
                ((Humano) ciudadano1).reproducir(ciudadanos);
            } else if (ciudadano1 instanceof Lobo) {
                ((Lobo) ciudadano1).reproducir(ciudadanos);
            }
            // Los Vampiros no se reproducen
        } else {
            // Si son de distinto tipo, combaten
            Ciudadano perdedor = ciudadano1.combate(ciudadano2);
            if (perdedor instanceof Vampiro) {
                // Si el perdedor es un vampiro, se reemplaza en la lista (puede ser convertido)
                int index = ciudadanos.indexOf(perdedor);
                if (index != -1) {
                    ciudadanos.set(index, perdedor);
                }
            } else {
                // Si el perdedor no es vampiro, muere y se elimina de la lista
                perdedor.morir(ciudadanos);
            }
        }
    }

    // Actualiza la edad de un ciudadano (solo humanos y lobos envejecen)
    public static void actualizarEdad(Ciudadano ciudadano) {
        if (ciudadano instanceof Humano) {
            ((Humano) ciudadano).envejecer(ciudadanos);
        } else if (ciudadano instanceof Lobo) {
            ((Lobo) ciudadano).envejecer(ciudadanos);
        }
        // Los vampiros no envejecen
    }

    // Verifica si solo queda un tipo de ciudadano y muestra el mensaje de fin de juego
    public static void verificarPoblacion() {
        int humanos = 0;
        int lobos = 0;
        int vampiros = 0;

        for (Ciudadano c : ciudadanos) {
            if (c instanceof Humano) humanos++;
            else if (c instanceof Lobo) lobos++;
            else if (c instanceof Vampiro) vampiros++;
        }

        int tiposPresentes = 0;
        if (humanos > 0) tiposPresentes++;
        if (lobos > 0) tiposPresentes++;
        if (vampiros > 0) tiposPresentes++;

        if (tiposPresentes == 1) {
            System.out.println("=== FIN DEL JUEGO ===");
            System.out.println("Solo queda un tipo de ser en el pueblo:");

            if (humanos > 0) {
                System.out.println("Humanos han dominado el pueblo!");
            } else if (lobos > 0) {
                System.out.println("Lobos han dominado el pueblo!");
            } else {
                System.out.println("Vampiros han dominado el pueblo!");
            }
        }
    }
}
