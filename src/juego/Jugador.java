package juego;
import java.awt.Color;
import java.awt.Point;
public class Jugador {
	    private Point posicion;
	    private int vidas;
	    
	
	    public Jugador() {
	        this.posicion = new Point(400, 300); // centro de la pantalla
	        this.vidas = 3;
	    }
	    public void mover(int dx, int dy) {
	        posicion.x += dx;
	        posicion.y += dy;
	    }
	    
	    public Point getPosicion() {
	        return posicion;
	    }
	    
	    public void perderVida() {
	        vidas--;
	    }
	    
	    public int getVidas() {
	        return vidas;
	    }
	}

