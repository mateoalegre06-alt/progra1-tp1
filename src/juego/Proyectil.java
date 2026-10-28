package juego;
import java.awt.Color;
import entorno.Entorno;
import java.lang.Math;
public class Proyectil {
    private int x, y;
    private int dirX, dirY;
    private int velocidad = 4;
    private int mitad = 4;

    public Proyectil(int x, int y, int destinoX, int destinoY) {
        this.x = x;
        this.y = y;

        double dx = destinoX - x;
        double dy = destinoY - y;
        double distancia = Math.sqrt(dx * dx + dy * dy);

        if (distancia != 0) {
            this.dirX = (int) (dx / distancia * 100);
            this.dirY = (int) (dy / distancia * 100);
        } else {
            this.dirX = 0;
            this.dirY = 0;
        }
    }

    public void mover() {
        x += dirX * velocidad / 100;
        y += dirY * velocidad / 100;
    }
    

    public boolean fueraDelMapa() {
        return x < 0 || x > Mapa.ANCHO || y < Mapa.TOPE || y > Mapa.ALTO;
    }

    public void dibujar(Entorno e) {
        e.dibujarRectangulo(x, y, mitad * 2, mitad * 2, 0, Color.CYAN);
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getMitad() { return mitad; }
}