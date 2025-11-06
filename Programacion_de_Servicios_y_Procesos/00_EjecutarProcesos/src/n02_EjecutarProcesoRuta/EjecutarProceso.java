package n02_EjecutarProcesoRuta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EjecutarProceso {
	public void ejecutar(String rutaDirectorio, String nombreEjecutable) {
		List <String> nombreArgumentos = new ArrayList<>();
		File directorio = new File(rutaDirectorio);
		nombreArgumentos.add(nombreEjecutable);
		ProcessBuilder pb = new ProcessBuilder(nombreArgumentos);
		pb.command(nombreEjecutable);
		pb.directory(directorio);
		try {
			//Process proceso = pb.start();
			pb.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
