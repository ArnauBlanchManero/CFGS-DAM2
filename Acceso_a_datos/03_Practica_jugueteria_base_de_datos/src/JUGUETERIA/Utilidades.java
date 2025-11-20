package JUGUETERIA;

import java.util.Scanner;

public class Utilidades {
	private static Scanner read = new Scanner(System.in);
	
	public static String preguntarString(String pregunta) {
		String respuesta;
		System.out.print(pregunta);
		respuesta = read.nextLine();
		return respuesta;
	}

}
