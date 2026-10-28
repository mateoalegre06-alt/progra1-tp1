package juego;

import java.awt.Color;
import entorno.Entorno;

public class Enemigo {

    public int x, y;
    public int velocidad;
    public int minimo, maximo;
    public int direccion;
    public boolean vertical;
    public boolean poneBombas;

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
    if (vertical && (nuevoY >= maximo || nuevoY <= minimo)) {
        direccion = -direccion;
       
    } if (mapa.puedeOcupar(nuevoX, nuevoY, 12)) {
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
        return Math.abs(px - x) < 12 + mitad
            && Math.abs(py - y) < 12 + mitad;
    }

    public boolean esBombardero() {
        return poneBombas;
    }

}


