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

	public static int preguntarIntRegex(String regex, String pregunta, String especificacion) {
		String respuestaUsuario;
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

	public static String preguntarLongitud(int longitud, String pregunta, String especificacion) {
		String respuesta;
		System.out.print(pregunta);
		respuesta = read.nextLine();
		while (respuesta.length()> longitud) {
			System.out.print("Introduce un valor correcto ("+especificacion+"): ");
			respuesta = read.nextLine();
		}
		return respuesta;
	}

	public static double preguntarDouble(String pregunta, String especificacion) {
		String respuesta;
		double respuestaD = 0;
		boolean repetir;
		System.out.print(pregunta);
		respuesta = read.next();
		read.nextLine();
		try {
			respuestaD = Double.valueOf(respuesta);
			repetir = false;
		} catch (Exception e) {
			repetir = true;
		}
		while(repetir) {
			System.out.print("Introduce un valor correcto ("+especificacion+"): ");
			respuesta = read.next();
			read.nextLine();
			try {
				respuestaD = Double.valueOf(respuesta);
				repetir = false;
			} catch (Exception e) {
				repetir = true;
			}
		}
		return respuestaD;
	}

	public static String preguntarCategoriaJuguete() {
		String respuesta;
		CategoriaJuguete categoria1 = CategoriaJuguete.ACCION;
		CategoriaJuguete categoria2 = CategoriaJuguete.CONSTRUCCION;
		CategoriaJuguete categoria3 = CategoriaJuguete.ELECTRONICOS;
		CategoriaJuguete categoria4 = CategoriaJuguete.LIBRE;
		CategoriaJuguete categoria5 = CategoriaJuguete.MESA;
		CategoriaJuguete categoria6 = CategoriaJuguete.MUÑECAS;
		CategoriaJuguete categoria7 = CategoriaJuguete.PELUCHES;
		CategoriaJuguete categoria8 = CategoriaJuguete.VEHICULOS;
		System.out.println("1. "+categoria1.toString());
		System.out.println("2. "+categoria2.toString());
		System.out.println("3. "+categoria3.toString());
		System.out.println("4. "+categoria4.toString());
		System.out.println("5. "+categoria5.toString());
		System.out.println("6. "+categoria6.toString());
		System.out.println("7. "+categoria7.toString());
		System.out.println("8. "+categoria8.toString());
		System.out.print("Categoria: ");
		respuesta = read.nextLine();
		//TODO control de errores
		return respuesta;
	}

}
