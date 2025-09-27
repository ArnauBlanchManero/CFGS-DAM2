package ConsultarFicheros;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio3 {

	static Scanner entrada = new Scanner(System.in);
	public static void main(String[] args) {
		String directorio, nombre;
		System.out.print("Escribe el nombre de la carpeta: ");
		directorio = entrada.next();
		entrada.nextLine();
		System.out.print("Escribe el nombre del fichero: ");
		nombre = entrada.next();
		entrada.nextLine();
		directorio = "C:/"+directorio;
		//directorio = "~/"+directorio;
		nombre = nombre + ".txt";
		File fichero = new File(directorio, nombre);
		
		if (fichero.exists()) {
			System.out.println("El fichero "+ fichero.getAbsolutePath()+" existe.");

		} else {
			try {
				fichero.createNewFile();
				System.out.println("Se ha creado el fichero "+ fichero.getAbsolutePath()+" con éxito.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
