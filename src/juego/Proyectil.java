package juego;
import java.awt.Color;
import entorno.Entorno;

public class Proyectil {
    private int x, y;
    private int dirX, dirY;
    private int velocidad = 3;
    private int mitad = 5;

    public Proyectil(int x, int y, int dirX, int dirY) {
        this.x = x;
        this.y = y;
        this.dirX = dirX;
        this.dirY = dirY;		
    }

    public void mover() {
        x += dirX * velocidad;
        y += dirY * velocidad; 
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