package CooperacionHilos;

public class Contador {
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
	/*// El profe lo ha hecho mas optimizado
	 private int contador = 0;
	 
	 public int getContador(){
	 	return contador;
	 }
	 
	 public int incrementar(){
	 	this.contador++;
	 	return contador;
	 }
	 */
}
