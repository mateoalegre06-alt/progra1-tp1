package juego;
import java.awt.Color;
import entorno.Entorno;

public class Mapa {
    public static final int ANCHO = 800, ALTO = 600, TOPE = 60; 
    private static final int CALLE = 40, MANZ_W = 150, MANZ_H = 85, N = 4;

    private Manzana[] manzanas = new Manzana[N * N]; 

    public Mapa() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int bx = CALLE + i * (MANZ_W + CALLE);
                int by = TOPE + CALLE + j * (MANZ_H + CALLE);
                manzanas[i * N + j] = new Manzana(bx, by, MANZ_W, MANZ_H);
            }
        }
    }

    
    public boolean puedeOcupar(int x, int y, int mitad) {
        if (x - mitad < 0 || x + mitad > ANCHO || y - mitad < TOPE || y + mitad > ALTO) return false;
        for (int i = 0; i < manzanas.length; i++) {
            if (manzanas[i].colisiona(x, y, mitad)) return false;
        }
        return true;
    }

    public Edificio getCasaAleatoria() {
        return manzanas[(int)(Math.random() * manzanas.length)].getCasaAleatoria();
    }

    public Edificio getNegocioAleatorio() {
        return manzanas[(int)(Math.random() * manzanas.length)].getNegocio();
    }

    public void dibujar(Entorno e) {
        
        e.dibujarRectangulo(ANCHO / 2, TOPE + (ALTO - TOPE) / 2, ANCHO, ALTO - TOPE, 0, Color.DARK_GRAY);
        for (int i = 0; i < manzanas.length; i++) {
            manzanas[i].dibujar(e);
        }
    }
}
