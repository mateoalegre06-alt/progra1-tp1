package juego;
import java.awt.Color;


public class Edificio {

	private int x, y, ancho, alto;
	private boolean esNegocio;
	
	public Edificio(int x, int y, int ancho, int alto, boolean esNegocio) {
        this.x = x; this.y = y; this.ancho = ancho; this.alto = alto;
        this.esNegocio = esNegocio;
	}
	public boolean colisiona(int px, int py, int mitad) {
	    return px > x - ancho / 2 - mitad
	        && px < x + ancho / 2 + mitad
	        && py > y - alto / 2 - mitad
	        && py < y + alto / 2 + mitad;
	}
	
	 public void dibujar(entorno.Entorno e) {
	        e.dibujarRectangulo(x, y, ancho, alto, 0, esNegocio ? Color.ORANGE : Color.RED);
	    }
 
	 public int getX() { return x; }
	 public int getY() { return y; }
	    public boolean esNegocio() { return esNegocio; }
	    

public void dibujarFlecha(entorno.Entorno e, Color color) {
    
    e.dibujarRectangulo(x, y - 25, 4, 30, 0, color);

    
    e.dibujarTriangulo(x, y - 40, 15, 15, 0, color);
}

	    
}
	