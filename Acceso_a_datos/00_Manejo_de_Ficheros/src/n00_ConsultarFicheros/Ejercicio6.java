package n00_ConsultarFicheros;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio6 {
	static Scanner entrada = new Scanner(System.in);
	public static void main(String[] args) {
		System.out.print("Nombre del fichero que quieres crear: ");
		String nombre = entrada.next();
		entrada.nextLine();
		File fichero = new File(nombre);
		if (fichero.exists()) {
			System.out.println(fichero.getAbsolutePath());
		}else {
			System.out.println("El fichero no existe");
			try {
				fichero.createNewFile();
				System.out.println("Fichero creado");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
