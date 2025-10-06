package CooperacionHilos;


public class Hilo implements Runnable{
	private final int nombre;
	private final int vueltas;
	private final Contador contador;
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
	}
	
	@Override
	public void run() {
		for (int i = 0; i < vueltas; i++) {
			contador.setI(contador.getI()+1);
			System.out.println(nombre+": "+contador.getI());
		}
	}

}
