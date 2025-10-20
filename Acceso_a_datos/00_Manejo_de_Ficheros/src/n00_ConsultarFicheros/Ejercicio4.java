package n00_ConsultarFicheros;

import java.io.File;
import java.util.Scanner;

public class Ejercicio4 {

	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		String directorioCompleto;
		System.out.print("Introduce una ruta completa: ");
		directorioCompleto = entrada.nextLine();
		File directorio = new File(directorioCompleto);
		if (directorio.exists() && directorio.isDirectory()) {
			System.out.println("Has introducido una ruta correcta");
			//System.out.println("Mal hecho:");
			//hechoPorMi(directorio);
			//System.out.println("Bien hecho:");
			hechoPorBea(directorio);
		} else {System.out.println("No es un directorio");}
		
	}

	private static void hechoPorBea(File directorio) {
		File[] lista;
		lista = directorio.listFiles();
		for (File fichero : lista) {
			if (fichero.isDirectory()) {
				System.out.println("");
				System.out.println(fichero.getAbsolutePath());
				hechoPorBea(fichero);
				System.out.println(fichero.getParent());
			} else {
				System.out.println(fichero.getAbsolutePath());
			}
		}
		
	}

	private static void hechoPorMi(File directorio) {
		File[] lista;
		lista = directorio.listFiles();
		for (File fichero : lista) {
			System.out.println(fichero.getName());
		}
		
	}

}
