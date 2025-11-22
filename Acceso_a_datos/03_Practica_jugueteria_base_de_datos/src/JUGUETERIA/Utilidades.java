package JUGUETERIA;

import java.awt.font.TextLayout.CaretPolicy;
import java.util.ArrayList;
import java.util.Scanner;

public class Utilidades {
	private static Scanner read = new Scanner(System.in);
	
	public static String preguntarString(String pregunta) {
		String respuesta;
		System.out.print(pregunta);
		respuesta = read.nextLine();
		return respuesta;
	}

	public static String preguntarRegex(String regex, String pregunta, String especificacion) {
		String respuestaUsuario;
		System.out.print(pregunta);
		respuestaUsuario = read.nextLine();
		while(!respuestaUsuario.matches(regex)) {
			System.out.print("Introduce un valor correcto ("+especificacion+"): ");
			respuestaUsuario = read.nextLine();
		}
		return respuestaUsuario;
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

	public static String preguntarDouble(String pregunta, String especificacion) {
		String respuesta;
		double respuestaD = 0;
		boolean repetir;
		System.out.print(pregunta);
		respuesta = read.nextLine();
		if (respuesta.equals("")) {
			return "NULL";
		}
		try {
			respuestaD = Double.valueOf(respuesta);
			repetir = false;
		} catch (NumberFormatException e) {
			repetir = true;
		}
		while(repetir) {
			System.out.print("Introduce un valor correcto ("+especificacion+"): ");
			respuesta = read.nextLine();
			if (respuesta.equals("")) {
				return "NULL";
			}
			try {
				respuestaD = Double.valueOf(respuesta);
				repetir = false;
			} catch (NumberFormatException e) {
				repetir = true;
			}
		}
		return respuesta;
	}

	public static String preguntarCategoriaJuguete() {
		String respuesta;
		ArrayList<CategoriaJuguete> categorias = new ArrayList<CategoriaJuguete>();
		ArrayList<String> categoriaString = new ArrayList<String>();
		boolean comprobar = true;
		
		categorias.add(CategoriaJuguete.ACCION);
		categorias.add(CategoriaJuguete.CONSTRUCCION);
		categorias.add(CategoriaJuguete.ELECTRONICOS);
		categorias.add(CategoriaJuguete.LIBRE);
		categorias.add(CategoriaJuguete.MESA);
		categorias.add(CategoriaJuguete.MUÑECAS);
		categorias.add(CategoriaJuguete.PELUCHES);
		categorias.add(CategoriaJuguete.VEHICULOS);
		for (int i = 0; i < categorias.size(); i++) {
			categoriaString.add(categorias.get(i).toString());
			System.out.println((i+1)+". "+categoriaString.get(i));
		}
		categoriaString.add("");
		System.out.print("Categoria: ");
		respuesta = read.nextLine();
		if (respuesta.matches("^[1-8]$")) {
			comprobar = false;
			respuesta = categoriaString.get(Integer.valueOf(respuesta)-1);
		} else {
			for (int j = 0; j < categoriaString.size(); j++) {
				if (respuesta.equalsIgnoreCase(categoriaString.get(j))) {
					comprobar = false;
				}
			}
		}
		while (comprobar) {
			System.out.print("Introduce un valor correcto (una de las categorías mostradas): ");
			respuesta = read.nextLine();
			if (respuesta.matches("^[1-8]$")) {
				comprobar = false;
				respuesta = categoriaString.get(Integer.valueOf(respuesta)-1);
			} else {
				for (int j = 0; j < categoriaString.size(); j++) {
					if (respuesta.equalsIgnoreCase(categoriaString.get(j))) {
						comprobar = false;
						//respuesta = categoriaString.get(j).toLowerCase();
					}
				}
			}
		} 
		return respuesta;
	}

}
