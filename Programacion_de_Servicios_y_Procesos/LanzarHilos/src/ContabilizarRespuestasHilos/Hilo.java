package ContabilizarRespuestasHilos;

public class Hilo implements Runnable{
	String identificador;
	int sumaTotal, sumaZona, sumaSi, sumaSiZona, sumaNo, sumaNoZona, sumaNSNC, sumaNSNCZona;
	int[] tipo_respuesta;
	ResultadosEncuesta res;

	public Hilo(String identificador, int[] tipo_respuesta, ResultadosEncuesta res) {
		super();
		this.identificador = identificador;
		this.tipo_respuesta = tipo_respuesta;
		this.res = res;
	}

	@Override
	public void run() {
		for (int i = 0; i < tipo_respuesta.length; i++) {
			switch (tipo_respuesta[i]) {
			case 0:
				res.incrementarSi();
				sumaSiZona++;
				break;
			case 1:
				res.incrementarNo();
				sumaNoZona++;
				break;
			case 2:
				res.incrementarNSNC();
				sumaNSNCZona++;
				break;

			default:
				System.out.println("Respuesta no válida");
				break;
			}
		}
		System.out.println(identificador+":\n\tSi: "+sumaSiZona+"\n\tNo: "+sumaNoZona+"\n\tNSNC: "+sumaNSNCZona+"\n");
	}
	

}
