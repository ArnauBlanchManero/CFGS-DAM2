package n04_ContabilizarRespuestasHilos;

public class ResultadosEncuesta {
	// Registrar y contabilizar cada respuesta con synchronized
	private int contadorSi = 0;
	private int contadorNo = 0;
	private int contadorNSNC = 0;

	synchronized public int incrementarSi(){
	 	this.contadorSi++;
	 	return contadorSi;
	 }
	synchronized public int incrementarNo(){
	 	this.contadorNo++;
	 	return contadorNo;
	 }
	synchronized public int incrementarNSNC(){
	 	this.contadorNSNC++;
	 	return contadorNSNC;
	 }

	public int getSi() {
		return contadorSi;
	}
	public int getNo() {
		return contadorNo;
	}
	public int getNSNC() {
		return contadorNSNC;
	}
}
