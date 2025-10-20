package n01_LeerFicheros;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Ejercicio1 {

	public static void main(String[] args) {
		File fichero = new File("FicheroEjemplo.txt");
		if (fichero.exists()) {
			if (fichero.canRead()) {
				try {
					FileReader leer = new FileReader(fichero);
					int letra;
					try {
						while ((letra = leer.read()) != -1) {
							if (letra != 32) {
								System.out.print((char)letra);
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("El fichero no se puede leer");
			}
		} else {
			System.out.println("El fichero no existe.");
			try {
				fichero.createNewFile();
				System.out.println("Se ha creado el fichero "+fichero.getName());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
