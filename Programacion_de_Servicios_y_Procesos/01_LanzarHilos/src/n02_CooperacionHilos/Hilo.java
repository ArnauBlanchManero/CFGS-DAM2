package n02_CooperacionHilos;


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
	
	@Override
	public void run() {
		for (int i = 0; i < vueltas; i++) {
			contador.incrementar(); // Esto lo tenía dentro de un System.out.println() y como es de E/S me daba el resultado esperado. Ahora da una respuesta inesperada porque los hilos se interponen unos con otros.
			miCuenta++;
		}
		System.out.println(nombre+" final: "+miCuenta);
	}

}
