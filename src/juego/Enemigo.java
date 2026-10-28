package juego;

import java.awt.Color;
import entorno.Entorno;

public class Enemigo {

    private int x, y;
    private int velocidad;
    private int minimo, maximo;
    private int direccion;
    private boolean vertical;
    private boolean poneBombas;
    private int proximaBomba = 2000;

    public Enemigo(int x, int y, int velocidad,
                   int minimo, int maximo,
                   boolean vertical, boolean poneBombas) {

        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        this.minimo = minimo;
        this.maximo = maximo;
        this.vertical = vertical;
        this.poneBombas = poneBombas;
        this.direccion = 1;
    }

    public void mover(Mapa mapa) {
    int nuevoX = x;
    int nuevoY = y;

    if (vertical) {
        nuevoY = y + velocidad * direccion;
    } else {
        nuevoX = x + velocidad * direccion;
    }
    
    boolean seSale;
    if (vertical) {
        seSale = nuevoY <= minimo || nuevoY >= maximo;
    } else {
        seSale = nuevoX <= minimo || nuevoX >= maximo;
    }  
        
       
     if (seSale || !mapa.puedeOcupar(nuevoX, nuevoY, 12)) {
        direccion = -direccion;
    } else {
    	x = nuevoX;
        y = nuevoY;
   
    }
  }

    public void dibujar(Entorno e) {
        Color color = poneBombas ? Color.MAGENTA : Color.BLUE;
        e.dibujarRectangulo(x, y, 25, 25, 0, color);
    }

    public boolean colisiona(int px, int py, int mitad) {
        int d = 12 + mitad;
        return px > x - d && px < x + d
            && py > y - d && py < y + d;
    }

    public boolean debeSoltarBomba(int ahora) {
        if (poneBombas && ahora >= proximaBomba) {
        	proximaBomba = ahora + 4000;
        	return true;
        }
    return false;

}
public boolean esBombardero() { return poneBombas; }
public boolean esVertical() {return vertical; }
public int getX() { return x; }
public int getY() {return y; }
}




