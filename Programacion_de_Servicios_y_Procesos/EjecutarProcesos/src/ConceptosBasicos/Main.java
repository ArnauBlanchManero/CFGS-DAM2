package ConceptosBasicos;

public class Main {

	public static void main(String[] args) {
		String ruta = "C:/windows/system32/notepad.exe";
		GenerarProceso levantar = new GenerarProceso();
		for (int i = 0; i < 1; i++) {
			levantar.ejecutar(ruta);
		}
		System.out.println("Se ha abierto el bloc de notas correctamente.");

	}

}
