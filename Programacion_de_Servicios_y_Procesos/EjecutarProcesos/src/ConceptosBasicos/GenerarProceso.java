package ConceptosBasicos;

public class GenerarProceso {
	public void ejecutar(String ruta) {
		ProcessBuilder pb = new ProcessBuilder(ruta);
		try {
			Process proceso = pb.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
