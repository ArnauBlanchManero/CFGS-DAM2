package LeerFicheros;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Ejercicio1 {

	public static void main(String[] args) {
		File fichero = new File("FicheroEjemplo.txt");
		if (fichero.exists()) {
			if (fichero.canRead()) {
				try {
					FileReader leer = new FileReader(fichero);
					for (int i = 0; i < fichero.length(); i++) {
						
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("El fichero no se puede leer");
			}
		} else {
			System.out.println("El fichero no existe.");
		}

	}

}
