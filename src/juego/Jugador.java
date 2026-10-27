package juego;
import java.awt.Color;
import java.awt.Point;
import entorno.Entorno;

public class Jugador {
    private Point posicion;
    private int vidas;
    private int mitad = 10;          
    private int dirX = 0, dirY = -1; 

    public Jugador() {
        this.posicion = new Point(400, 580); 
        this.vidas = 3;
    }

    
    public void mover(int dx, int dy, Mapa mapa) {
        if (dx == 0 && dy == 0) return;
        if (dx > 0) dirX = 1; else if (dx < 0) dirX = -1; else dirX = 0;
        if (dy > 0) dirY = 1; else if (dy < 0) dirY = -1; else dirY = 0;
        if (mapa.puedeOcupar(posicion.x + dx, posicion.y, mitad)) posicion.x += dx;
        if (mapa.puedeOcupar(posicion.x, posicion.y + dy, mitad)) posicion.y += dy;
    }

    public void dibujar(Entorno e) {
        e.dibujarRectangulo(posicion.x, posicion.y, mitad * 2, mitad * 2, 0, Color.BLUE);
    }

    public Point getPosicion() { return posicion; }
    public int getMitad() { return mitad; }
    public int getDirX() { return dirX; }
    public int getDirY() { return dirY; }
    public void perderVida() { vidas--; }
    public int getVidas() { return vidas; }
}