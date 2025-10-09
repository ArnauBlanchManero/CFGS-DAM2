package ContabilizarRespuestasHilos;


public class Main {

	public static void main(String[] args) {
		// Crear respuestas aleatorias para el cuestionario
		int num_aleatorio = (int) (Math.random()*100);
		int cantidad_respuestas = num_aleatorio+200;
		int[][] tipo_respuesta = new int[20][cantidad_respuestas];
		//System.out.println(num_aleatorio);
		//System.out.println(cantidad_respuestas);
		// Crear hilos para 20 zonas
		Thread[] threads = new Thread[20];
		ResultadosEncuesta res = new ResultadosEncuesta();
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < cantidad_respuestas; j++) {
				num_aleatorio = (int) (Math.random()*100);
				tipo_respuesta[i][j] = num_aleatorio%3;
				//System.out.print(tipo_respuesta[i][j]+", ");
			}
			threads[i] = new Thread(new Hilo("Zona"+i, tipo_respuesta[i], res));
		}
		for (int i = 0; i < 20; i++) {
			threads[i].start();
		}
		for (int i = 0; i < 20; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Mostrar los resultados
		System.out.println("Resultados finales: ");
		System.out.println("\tSi: "+res.getSi());
		System.out.println("\tNo: "+res.getNo());
		System.out.println("\tNSNC: "+res.getNSNC());
	}

}
