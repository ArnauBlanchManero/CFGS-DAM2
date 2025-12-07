package txt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;

import noticias.Titular;
import usuarios.Usuario;
import ventanas.Ventana;

/*
 * Trabajo realizado por Arnau Blanch Manero
 */

public class LeerTxt {
	
	public static ArrayList<Usuario> leerTodosUsuarios() throws IOException {
		// Accedo al fichero donde se guardan los usuarios y los guardo en un array
		File fichero = new File("src/txt/credencialesUsuarios.txt");
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		
		if (fichero.exists() && fichero.canRead()) {
			
			FileReader lector;
			usuarios = new ArrayList<Usuario>();
			lector = new FileReader(fichero);
			BufferedReader br = new BufferedReader(lector);
			String contenidoLinea;
			
			// Leo el fichero línea por línea hasta que no haya más líneas
			while((contenidoLinea=br.readLine())!=null) {
				// Uso los caracteres ·!· para separar los datos para crear un usuario
				String [] lineaSeparada = contenidoLinea.split("·!·");
				// Si la línea está mal formada y no hay 5 campos, no se crea el usuario
				if (lineaSeparada.length == 5)
					usuarios.add(new Usuario(Integer.parseInt(lineaSeparada[0]), lineaSeparada[1], lineaSeparada[2], lineaSeparada[3], lineaSeparada[4].equals("1"), leerCategorias(Integer.parseInt(lineaSeparada[0]))));
			}
		}

		return usuarios;
	}

	public static boolean[] leerCategorias(int id) {
		// Guardo las categorías en un array de buleanos
		boolean[] categorias = new boolean[Ventana.CANTIDAD_CATEGORIAS];
		File fichero = new File("src/txt/noticiasUsuarios.txt");
		
		if (fichero.exists() && fichero.canRead()) {
			FileReader lector;
			try {
				
				lector = new FileReader(fichero);
				BufferedReader br = new BufferedReader(lector);
				String contenidoLinea;
				boolean idEncontrado = false;
				
				// Voy leyendo línea por línea hasta que no haya más líneas
				while((contenidoLinea=br.readLine())!=null) {
					String [] lineaSeparada = contenidoLinea.split(":");
					// Uso el caracter : para separar entre el id y el array de categorías favoritas
					if (lineaSeparada.length == 2) {
						if (Integer.parseInt(lineaSeparada[0].charAt(1)+"") == id ){
							
							idEncontrado = true;
							for (int i = 0; i < Ventana.CANTIDAD_CATEGORIAS; i++) {
								// El array de buleanos se representa con 0 y 1 dependiendo si lo ha guardado como favorito o no
								if(lineaSeparada[1].charAt(i)=='0') {
									categorias[i] = false;
								} else {
									categorias[i] = true;
								}
							}
						}
					}
				}
				// Si no encuentra el id significa que el usuario aún no ha guardado sus categorías
				if(!idEncontrado) {
					categorias = null;
				}
				
			} catch (IOException | NumberFormatException e) {
				// Si ocurre algún error en la lectura no se guardan las categorías
				categorias = null;
				// TODO eliminar esto
				e.printStackTrace();
			}
		}
		return categorias;
	}

	public static ArrayList<String> leerTodasNoticias() {
		
		ArrayList<String> urlsParametrosPosicion = leerUrlsParametrosPosicion();
		ArrayList<String> titulares = new ArrayList<String>();
		
		// De cada línea del fichero guardaremos su titular
		for (int i = 0; i < urlsParametrosPosicion.size(); i++) {
			// Separamos por ; la url, el parametro y el selector
			String infoTitular [] = urlsParametrosPosicion.get(i).split(";");
			
			// Puede haber líneas vacías o mal formadas por lo que comprobamos que tenga longitud 3
			if(infoTitular.length == 3) {
				Titular titular = new Titular(infoTitular[0], infoTitular[1]);
				try {
					// Obtenemos el titular y lo guardamos en el array
					titulares.add(titular.devolverTitular(Integer.parseInt(infoTitular[2])));
					if(titulares.getLast().equals("ERROR. Titular no encontrado"))
						return null;
					
				} catch (NumberFormatException | IndexOutOfBoundsException e) {
					// Si algo falla, lo volvemos a intentar con la posición 0
					titulares.add(titular.devolverTitular(0));
					if(titulares.getLast().equals("ERROR. Titular no encontrado"))
						return null;
				}
			}
		}
		return titulares;
	}

	private static ArrayList<String> leerUrlsParametrosPosicion() {
		// En esta función leemos la información del fichero donde se guarda la url, el selector y el índice
		File fichero = new File("src/txt/urlsNoticias.txt");
		ArrayList<String> lineas = new ArrayList<String>();
		
		if (fichero.exists() && fichero.canRead()) {
			FileReader lector;
			try {
				// Voy leyendo línea por línea y guardando la información en el array
				lector = new FileReader(fichero);
				BufferedReader br = new BufferedReader(lector);
				String contenidoLinea;
				
				while((contenidoLinea=br.readLine())!=null) {
					lineas.add(contenidoLinea);
				}
				
			} catch (IOException e) {
				lineas.add("");
				// TODO eliminar esto
				e.printStackTrace();
			}
			
		} else {
			lineas.add("");
		}

		return lineas;
	}
}
