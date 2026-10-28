
package juego;

import java.awt.Color;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {

    private static final int META = 5;
    private static final int[] CALLES_X = {20, 210, 400, 590, 780};
    private static final int MAX_PERDIDOS = 5;
    private static final int MARGEN = 30;
    private static final int ESPERA_PEDIDO = 3000;

    private Entorno entorno;
    private Mapa mapa;
    private Jugador jugador;
    private Enemigo[] enemigos;
    private Pedido pedido;
    private Proyectil proyectil;

    private int entregados = 0;
    private int eliminados = 0;
    private int perdidos = 0;
    private int proximoPedido = ESPERA_PEDIDO;
    private Enemigo crearEnemigo() {
        int x = CALLES_X[(int)(Math.random() * 5)];  
        int y = 80;                                 
        boolean vertical = Math.random() < 0.5;     
        boolean poneBombas = Math.random() < 0.3;   

        if (vertical) {
            return new Enemigo(x, y, 1, Mapa.TOPE, Mapa.ALTO, true, poneBombas);
        }
        return new Enemigo(x, y, 1, 0, Mapa.ANCHO, false, poneBombas);
    }
    
    Juego() {
        entorno = new Entorno(this, "proyecto tp", 800, 600);

        mapa = new Mapa();
        jugador = new Jugador();

        enemigos = new Enemigo[4];
        for (int i = 0; i < enemigos.length; i++) {
            enemigos[i] = crearEnemigo();
        }
        entorno.iniciar();
    }

    public void tick() {
        int ahora = entorno.tiempo();

        if (jugador.getVidas() <= 0 || perdidos >= MAX_PERDIDOS) {
            entorno.cambiarFont("Arial", 40, Color.RED);
            entorno.escribirTexto("GAME OVER", 270, 300);
            return;
        }

        if (entregados >= META) {
            entorno.cambiarFont("Arial", 40, Color.GREEN);
            entorno.escribirTexto("¡GANASTE!", 270, 300);
            return;
        }

        mapa.dibujar(entorno);

        int px = jugador.getPosicion().x;
        int py = jugador.getPosicion().y;

        	
        if (pedido == null) {
            if (ahora >= proximoPedido) {
                pedido = new Pedido(
                    mapa.getCasaAleatoria(),
                    mapa.getNegocioAleatorio(),
                    ahora
                );
            }
        } else {
            pedido.actualizar(ahora);

            if (pedido.vencio(ahora)) {
                pedido.perder();
                pedido = null;
                perdidos++;
                proximoPedido = ahora + ESPERA_PEDIDO;
            } else if (entorno.sePresionoBoton(entorno.BOTON_DERECHO)) {
                int mx = entorno.mouseX();
                int my = entorno.mouseY();
                int cerca = jugador.getMitad() + MARGEN;
                if (pedido.getEstado() == Pedido.ESPERANDO
                	 
                	&& pedido.getNegocio().colisiona(mx, my, 0)
                     
                	 && pedido.getNegocio().colisiona(px, py, cerca)) {
                        

                    pedido.retirar(ahora);

                } else if (pedido.getEstado() == Pedido.EN_CAMINO
                        
                	&& pedido.getCasa().colisiona(mx, my, 0)
                        && pedido.getCasa().colisiona(px, py, cerca)) {

                    pedido.entregar();
                    entregados++;
                    pedido = null;
                    proximoPedido = ahora + ESPERA_PEDIDO;
                }
            }
        }

        if (pedido != null) {
            pedido.dibujarFlecha(entorno);
        }

        
        int dx = 0;
        int dy = 0;

        if (entorno.estaPresionada('w')
                || entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
            dy = -2;
        }

        if (entorno.estaPresionada('s')
                || entorno.estaPresionada(entorno.TECLA_ABAJO)) {
            dy = 2;
        }

        if (entorno.estaPresionada('a')
                || entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
            dx = -2;
        }

        if (entorno.estaPresionada('d')
                || entorno.estaPresionada(entorno.TECLA_DERECHA)) {
            dx = 2;
        }

        jugador.mover(dx, dy, mapa);
        jugador.dibujar(entorno);

        px = jugador.getPosicion().x;
        py = jugador.getPosicion().y;

        
        proyectil = new Proyectil(px, py, entorno.mouseX(), entorno.mouseY());
        

        if (proyectil != null) {
            proyectil.mover();

            if (proyectil.fueraDelMapa()) {
                proyectil = null;
            } else {
                proyectil.dibujar(entorno);
            }
        }

        
        for (int i = 0; i < enemigos.length; i++) {
            if (enemigos[i] == null) {
                enemigos[i] = crearEnemigo();
            } 
        }	
        for (int i = 0; i < enemigos.length; i++) {
        	enemigos[i].mover(mapa);
            enemigos[i].dibujar(entorno);

            if (proyectil != null
                    && enemigos[i].colisiona(
                        proyectil.getX(),
                        proyectil.getY(),
                        proyectil.getMitad()
                    )) {

                enemigos[i] = null;
                proyectil = null;
                eliminados++;
                continue;
            }

            if (enemigos[i].colisiona(px, py, jugador.getMitad())) {
                jugador.perderVida();
                enemigos[i] = null;

                if (pedido != null
                        && pedido.getEstado() == Pedido.EN_CAMINO) {
                    pedido.perder();
                    pedido = null;
                    perdidos++;
                    proximoPedido = ahora + ESPERA_PEDIDO;
                }
            }
        }

      
        int segundos = ahora / 1000;
        String seg = segundos % 60 < 10
                ? "0" + segundos % 60
                : "" + segundos % 60;

        String textoPedido = pedido == null
                ? "Ninguno"
                : pedido.getTexto(ahora);

        entorno.dibujarRectangulo(
            400, 30, 800, 60, 0, Color.BLACK
        );

        entorno.cambiarFont("Arial", 13, Color.WHITE);

        entorno.escribirTexto(
            "Tiempo: " + segundos / 60 + ":" + seg, 15, 35
        );
        entorno.escribirTexto(
            "Vidas: " + jugador.getVidas(), 110, 35
        );
        entorno.escribirTexto(
            "Entregados: " + entregados + "/" + META, 185, 35
        );
        entorno.escribirTexto(
            "Eliminados: " + eliminados, 315, 35
        );
        entorno.escribirTexto(
            "Perdidos: " + perdidos + "/" + MAX_PERDIDOS, 425, 35
        );
        entorno.escribirTexto(
            "Pedido: " + textoPedido, 540, 35
        );
    }
    
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        Juego juego = new Juego();
    }
}

