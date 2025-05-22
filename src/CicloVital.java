import java.util.ArrayList;

// Interfaz que define el ciclo vital de los ciudadanos.

public interface CicloVital {
    int NATALIDAD_MAXIMA = 1;
    int VITALIDAD_MAXIMA = 2;

    void reproducir(ArrayList<Ciudadano> ciudadanos);
    void envejecer(ArrayList<Ciudadano> ciudadanos);
}
