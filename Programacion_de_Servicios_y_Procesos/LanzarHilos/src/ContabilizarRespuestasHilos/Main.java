package ContabilizarRespuestasHilos;


public class Main {

	public static void main(String[] args) {
		// Crear hilos para 20 zonas
		Thread[] threads = new Thread[20];
		for (int i = 0; i < 20; i++) {
			threads[i] = new Thread(new Hilo("Zona"+i));
		}
		// Crear respuestas aleatorias para el cuestionario
		int num_aleatorio = (int) (Math.random()*100);
		int cantidad_respuestas = num_aleatorio+200;
		int[] tipo_respuesta = new int[cantidad_respuestas];
		System.out.println(num_aleatorio);
		System.out.println(cantidad_respuestas);
		for (int j = 0; j < tipo_respuesta.length; j++) {
			num_aleatorio = (int) (Math.random()*100);
			tipo_respuesta[j] = num_aleatorio%3;
			System.out.print(tipo_respuesta[j]+", ");
		}
		// Mostrar los resultados

	}

}
