package HilosOrdenados;

import java.util.Random;

public class Hilo implements Runnable {
	private final String nombre;
	
	public Hilo(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public void run() {
		System.out.printf("Hola soy el hilo %s\n", this.nombre);
		for (int i = 0; i < 5; i++) {
			Random r = new Random();
			int pausa = 20 + r.nextInt(500-20);
			System.out.printf("Hilo %s hace pausa de %dms\n", this.nombre, pausa);
			try {
				Thread.sleep(pausa);
			} catch (InterruptedException e) {
				System.out.printf("El hilo %s se ha interrumpido!!", nombre);
				e.printStackTrace();
			}
		}
		System.out.printf("Hilo %s terminado\n", nombre);
	}

}
