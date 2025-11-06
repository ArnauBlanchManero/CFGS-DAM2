package n00_IntroduccionHilos;

public class Main {

	public static void main(String[] args) {
		
		// Creqamos los hilos
		Hilo hilo1 = new Hilo("Pepito");
		Thread h1 = new Thread(hilo1);
		Hilo hilo2 = new Hilo("José");
		Thread h2 = new Thread(hilo2);
		
		// Lanzamos los hjlos
		h1.start();
		h2.start();
		
		System.out.println("Hilo principal terminado");
	}

}
