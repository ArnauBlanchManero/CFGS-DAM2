package n05_BloquesSynchronized;

public class Hilo implements Runnable{
	Contadores cont = new Contadores();
	@Override
	public void run() {
		for (int i = 0; i < 10000; i++) {
			cont.incrementar1();
		}
		for (int i = 0; i < 10000; i++) {
			cont.incrementar2();
		}
		
	}

}
