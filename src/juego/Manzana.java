package juego;
import java.awt.Color;
import entorno.Entorno;



public class Manzana {
	private int x, y, ancho, alto; 
    private Edificio[] edificios = new Edificio[4];

    public Manzana(int x, int y, int ancho, int alto) {
    	this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        
        int negocio = (int)(Math.random() * 4); 
        int k = 0;
        for (int f = 0; f < 2; f++) {
            for (int c = 0; c < 2; c++) {
                int cx = x + c * 80 + 35;
                int cy = y + f * 47 + 19;
                edificios[k] = new Edificio(cx, cy, 70, 38, k == negocio);
                k++;
            
            }    
        } 
    }  

public boolean colisiona(int px, int py, int mitad) {
 
    if (px < x - mitad || px > x + ancho + mitad
            || py < y - mitad || py > y + alto + mitad) {
        return false;
    }
    for (int i = 0; i < edificios.length; i++) {
        if (edificios[i].colisiona(px, py, mitad)) return true;
    }
    return false;
}


public Edificio getCasaAleatoria() {
	int elegida = (int)(Math.random() * 3); 
    int contadas = 0;                      
    for (int i = 0; i < edificios.length; i++) {
        if (!edificios[i].esNegocio()) {
            if (contadas == elegida) return edificios[i];
            contadas++;
        }
    }
    return null; 
}

public Edificio getNegocio() {
    for (int i = 0; i < edificios.length; i++) {
        if (edificios[i].esNegocio()) return edificios[i];
    }
    return null; 
}

public void dibujar(Entorno e) {
   
    e.dibujarRectangulo(x + ancho / 2, y + alto / 2, ancho, alto, 0, new Color(60, 140, 60));
    for (int i = 0; i < edificios.length; i++) {
        edificios[i].dibujar(e);
    }
}
}