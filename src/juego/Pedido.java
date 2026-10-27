package juego;
import java.awt.Color;
import entorno.Entorno;

public class Pedido {
    public static final int REALIZADO = 0, ESPERANDO = 1, EN_CAMINO = 2, ENTREGADO = 3, PERDIDO = 4;
    private static final int DEMORA = 2000;   
    private static final int LIMITE = 40000;  
    
    private Edificio casa, negocio;
    private int estado;
    private int creado; 
    private int vence;   

    public Pedido(Edificio casa, Edificio negocio, int ahora) {
        this.casa = casa;
        this.negocio = negocio;
        this.estado = REALIZADO;
        this.creado = ahora;
    }

   
    public void actualizar(int ahora) {
        if (estado == REALIZADO && ahora - creado >= DEMORA) estado = ESPERANDO;
    }

    public void retirar(int ahora) { estado = EN_CAMINO; vence = ahora + LIMITE; }
    public void entregar() { estado = ENTREGADO; }
    public void perder() { estado = PERDIDO; }

    public boolean vencio(int ahora) { return estado == EN_CAMINO && ahora >= vence; }

    public int segundosRestantes(int ahora) { return (vence - ahora) / 1000; }

    public void dibujarFlecha(Entorno e) {
        if (estado == ESPERANDO)
            negocio.dibujarFlecha(e, Color.YELLOW);

        if (estado == EN_CAMINO)
            casa.dibujarFlecha(e, Color.GREEN);
    }
    
    public String getTexto(int ahora) {
        if (estado == REALIZADO) return "Realizado";
        if (estado == ESPERANDO) return "Esperando";
        return "En camino (" + segundosRestantes(ahora) + "s)";
    }

    public int getEstado() { return estado; }
    public Edificio getCasa() { return casa; }
    public Edificio getNegocio() { return negocio; }
}