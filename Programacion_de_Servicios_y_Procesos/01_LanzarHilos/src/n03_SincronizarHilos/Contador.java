package n03_SincronizarHilos;

public class Contador {
	/*// Mi version
	int i;

	public Contador(int i) {
		super();
		this.i = i;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}
	*/
	// El profe lo ha hecho mas optimizado
	 private int contador = 0;
	 
	 synchronized public int getContador(){
	 	return contador;
	 }
	 
	 synchronized  public int incrementar(){
	 	this.contador++;
	 	return contador;
	 }
}
