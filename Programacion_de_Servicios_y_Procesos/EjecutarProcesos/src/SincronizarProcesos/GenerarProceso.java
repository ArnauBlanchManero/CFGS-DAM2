package SincronizarProcesos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GenerarProceso {
	private static int MAXTiempo = 5;

	public void ejecutar(String ruta, String[] parametros) {
		List <String> nombreArgumentos = new ArrayList<>();
		if (ruta==null || ruta.isEmpty()) {
			System.out.println("Metele un dato hombre");
			System.exit(1);
		}
		nombreArgumentos.add(ruta);
		//nombreArgumentos.add("ipconfig");
		for (int i = 0; i < parametros.length; i++) {
			nombreArgumentos.add(parametros[i]);
		}
		ProcessBuilder pb = new ProcessBuilder(nombreArgumentos);
		pb.command(nombreArgumentos);
		
		//llamada inheritIO()
		//hace que el prpcesp herede la E/S estandar del proceso padre
		//Asi podemos ver el resultado del comando
		pb.inheritIO();
		
		try {
			Process proceso = pb.start();
			proceso.waitFor(MAXTiempo, TimeUnit.MICROSECONDS);
			System.out.println("El codigo de retorno es: "+proceso.exitValue());
		} catch (IOException e) {
			System.out.println("Error durante la ejecución del comando");
			System.out.println("INFORMACION ADICIONAL");
			e.printStackTrace();
			System.exit(2);
		} catch (InterruptedException e) {
			System.err.println("Proceso interrumpido");
			System.exit(3);
		}
	}

	public void ejecutar(String ruta) {
		if (ruta == null || ruta.isEmpty()) {
			System.out.println("No tengo datos suficientes");
			System.exit(1);
		}
		ProcessBuilder pb = new ProcessBuilder(ruta);
		try {
			Process proceso = pb.start();
			proceso.waitFor();
			System.out.println("Has cerrado el bloc de notas");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
