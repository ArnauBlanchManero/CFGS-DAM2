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

	public static int preguntarRangoInt(int inicio, int fin, String pregunta, String especificacion) {
		String respuestaUsuario;
		String regex = "^["+inicio+"-"+fin+"]$";
		System.out.print(pregunta);
		respuestaUsuario = read.next();
		read.nextLine();
		while(!respuestaUsuario.matches(regex)) {
			System.out.print("Introduce un valor correcto ("+especificacion+"): ");
			respuestaUsuario = read.next();
			read.nextLine();
		}
		return Integer.valueOf(respuestaUsuario);
	}

}
