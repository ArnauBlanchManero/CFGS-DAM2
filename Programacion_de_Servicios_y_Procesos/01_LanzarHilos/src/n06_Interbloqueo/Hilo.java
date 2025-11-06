package n06_Interbloqueo;

public class Hilo implements Runnable{
	String identificador;
	Cuenta enviar;
	Cuenta recibir;
	
	public Hilo(String identificador, Cuenta enviar, Cuenta recibir) {
		super();
		this.identificador = identificador;
		this.enviar = enviar;
		this.recibir = recibir;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
