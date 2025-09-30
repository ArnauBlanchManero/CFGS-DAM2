package EjecutarComandos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ComandoTerminal {
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
			int codigoRetorno = proceso.waitFor();
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$");
			System.out.println("El comando devuelve: "+codigoRetorno);
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$");
			if (codigoRetorno ==0) {
				System.out.println("Ejecución correcta");
			} else {
				System.out.println("Ejecución con error");
			}
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

}
