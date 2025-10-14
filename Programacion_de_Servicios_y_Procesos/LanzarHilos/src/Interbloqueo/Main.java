package Interbloqueo;

public class Main {

	public static void main(String[] args) {
		Cuenta c1 = new Cuenta("ES754893021759", 12000);
		Cuenta c2 = new Cuenta("ES209457823450", 35000);

		System.out.printf("Saldo inicial de %s: %d\n", c1.getNumCuenta(), c1.getSaldo());
		System.out.printf("Saldo inicial de %s: %d\n", c2.getNumCuenta(), c2.getSaldo());
		
		Thread h1 = new Thread(new Hilo("H1", c1, c2));
		Thread h2 = new Thread(new Hilo("H2", c2, c1));
		
		h1.start();
		h2.start();
		
		try {
			h1.join();
			h2.join();
		}catch (InterruptedException e){
			e.printStackTrace();
		}

		System.out.printf("Saldo final de %s: %d\n", c1.getNumCuenta(), c1.getSaldo());
		System.out.printf("Saldo final de %s: %d\n", c2.getNumCuenta(), c2.getSaldo());
	}

}
