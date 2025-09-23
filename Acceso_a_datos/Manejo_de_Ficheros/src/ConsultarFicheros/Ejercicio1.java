package ConsultarFicheros;

import java.io.File;
import java.util.Scanner;

public class Ejercicio1 {

	static Scanner entrada = new Scanner(System.in);
	public static void main(String[] args) {
		String directorioCompleto;
		String[] lista;
		System.out.print("Introduce una ruta completa: ");
		directorioCompleto = entrada.nextLine();
		System.out.println("Has introducido esta ruta: "+directorioCompleto);
		File directorio = new File(directorioCompleto);
		lista = directorio.list();
		for (int i = 0; i < lista.length; i++) {
			System.out.println(lista[i]);
		}
	}

}
