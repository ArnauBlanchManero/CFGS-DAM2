package ConsultarFicheros;

import java.io.File;
import java.io.IOException;

public class Ejercicio2 {

	public static void main(String[] args) {
		File existente = new File("Ejemplo.txt");
		if(existente.exists()) {
			existente.delete();
			System.out.println("Se ha eliminado el fichero "+ existente.getAbsolutePath()+" con éxito.");
		} else {
			try {
				existente.createNewFile();
				System.out.println("Se ha creado el fichero "+ existente.getAbsolutePath()+" con éxito.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
