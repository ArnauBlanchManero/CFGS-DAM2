package n05_BloquesSynchronized;

public class Main {

	public static void main(String[] args) {
		Thread[] hilos = new Thread[10];
		for (int i = 0; i < hilos.length; i++) {
			hilos[i] = new Thread(new Hilo());
			hilos[i].start();
		for (int j = 0; j < 10; j++) {
			try {
				hilos[j].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}

	}

}
