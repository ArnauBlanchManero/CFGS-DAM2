package n02_EjecutarProcesoRuta;

public class Main {

	public static void main(String[] args) {
		EjecutarProceso levantar = new EjecutarProceso();
		String ruta, programa;
		ruta = "C:/windows/system32";
		programa = "notepad.exe";
		for (int i = 0; i < 1; i++) {
			levantar.ejecutar(ruta, programa);
		}
		System.out.println("Proceso ejecutado.");


	}

}
