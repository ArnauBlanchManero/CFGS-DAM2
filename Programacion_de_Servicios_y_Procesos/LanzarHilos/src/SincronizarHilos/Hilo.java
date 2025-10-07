package SincronizarHilos;


public class Hilo implements Runnable{
	private final int nombre;
	private final int vueltas;
	private final Contador contador;
	private int miCuenta;
	/*
	public Hilo(String nombre) {
		this.nombre = nombre;
	}
	*/

	public Hilo(int nombre, int vueltas, Contador cont) {
		super();
		this.nombre = nombre;
		this.vueltas = vueltas;
		this.contador = cont;
		this.miCuenta = 0;
	}

	public int getMiCuenta() {
		return miCuenta;
	}
	@Override
	public void run() {
		for (int i = 0; i < vueltas; i++) {
			/*System.out.println(nombre+": "+contador.incrementar());
			miCuenta++;
		}
		System.out.println(nombre+" final: "+miCuenta);*/
			contador.incrementar(); // Incremento el contador compartido
			miCuenta++;
		}
		System.out.println("Hilo "+nombre+" lo damos por terminado y la cuenta de " +getMiCuenta());
	}

}
