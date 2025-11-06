package n03_SincronizarHilos;

public class Main {

	private static final int NUM_HILOS = 9;
	private static final int CUENTA_TOTAL = 900;

	public static void main(String[] args) {
		Contador cont = new Contador();
		Thread[] hilos = new Thread[NUM_HILOS];
		
		for (int i = 0; i < NUM_HILOS; i++) {
			Thread th = new Thread(new Hilo(i+1, CUENTA_TOTAL/NUM_HILOS, cont));
			th.start();
			hilos[i]=th;
		}
		for (Thread h : hilos) {
			try {
				h.join();
			}catch(InterruptedException e) {
				System.out.println("Interrupcion del hilo");
				e.printStackTrace();
			}
		}
		System.out.println("Contador final: "+cont.getContador());
		/*// Esta es mi versión mal hecha porque no sabía que para el contador fuera compartido lo tenía que pasar en el constructor del hilo.
		Thread h1 = new Thread(new Hilo("H1"));
		Thread h2 = new Thread(new Hilo("H2"));
		Thread h3 = new Thread(new Hilo("H3"));
		Thread h4 = new Thread(new Hilo("H4"));
		h1.start();
		h2.start();
		h3.start();
		h4.start();
		
		try {
			h1.join();
			h2.join();
			h3.join();
			h4.join();
		} catch (InterruptedException e) {
			System.out.println("Interrupcion del hilo!!");
			e.printStackTrace();
		}
		*/
		System.out.println("Hilo principal terminado");
	}

}
