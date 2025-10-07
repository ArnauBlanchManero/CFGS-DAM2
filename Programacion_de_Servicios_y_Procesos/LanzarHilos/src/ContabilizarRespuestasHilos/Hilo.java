package ContabilizarRespuestasHilos;

public class Hilo implements Runnable{
	String identificador;

	public Hilo(String identificador) {
		super();
		this.identificador = identificador;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	

}
