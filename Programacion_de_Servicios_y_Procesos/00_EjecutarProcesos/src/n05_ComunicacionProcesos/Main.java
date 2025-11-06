package n05_ComunicacionProcesos;

public class Main {

	public static void main(String[] args) {
		String ruta = "ping" + " google.es";
		GeneradorProcesos lanzador = new GeneradorProcesos();
		lanzador.ejecutar(ruta);

	}

}
