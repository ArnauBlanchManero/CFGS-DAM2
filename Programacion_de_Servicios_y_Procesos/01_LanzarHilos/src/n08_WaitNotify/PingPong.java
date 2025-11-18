package n08_WaitNotify;

import java.util.concurrent.Semaphore;

public class PingPong {
	
	static Semaphore s1 = new Semaphore(1);
	static Semaphore s2 = new Semaphore(0);
	
	public static void main(String[] args) {
		System.out.println("Este es el juego del PING PONG");
		HiloJugador jugador1 = new HiloJugador("PING...");
		HiloJugador jugador2 = new HiloJugador("...PONG");
		jugador1.start();
		jugador2.start();
	}
}
