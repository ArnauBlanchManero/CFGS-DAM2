package HilosOrdenados;

public class Main {

	public static void main(String[] args) {
		Thread h1 = new Thread(new Hilo("NaCl"));
		Thread h2 = new Thread(new Hilo("H2O"));
		System.out.println("Creo el primer hilo");
		h1.start();
		System.out.println("Creo el segundo hilo");
		h2.start();
		
		try {
			h1.join();
			h2.join();
		} catch (InterruptedException e) {
			System.out.println("Interrupcion del hilo!!");
			e.printStackTrace();
		}
		System.out.println("Hilo principal terminado");
	}

}
