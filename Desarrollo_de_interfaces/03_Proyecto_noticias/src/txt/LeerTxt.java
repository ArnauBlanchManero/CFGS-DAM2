package txt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
		File fichero = new File("src/txt/usuarios.txt");
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
				try {
					// Si la línea está mal formada y no hay 5 campos, no se crea el usuario
					if (lineaSeparada.length == 5) {
						int id = Integer.parseInt(lineaSeparada[0]);
						usuarios.add(new Usuario(id, lineaSeparada[1], lineaSeparada[2], lineaSeparada[3], lineaSeparada[4].equals("1"), leerCategorias(Integer.parseInt(lineaSeparada[0]))));
					}
				} catch (NumberFormatException e) {
					usuarios = new ArrayList<Usuario>();
				}
			}

			br.close();
			lector.close();
		}

		return usuarios;
	}

	public static boolean[] leerCategorias(int id) {
		// Guardo las categorías en un array de buleanos
		boolean[] categorias = new boolean[Ventana.CANTIDAD_CATEGORIAS];
		File fichero = new File("src/txt/configuracion.txt");
		
		if (fichero.exists() && fichero.canRead()) {
			FileReader lector;
			try {
				
				lector = new FileReader(fichero);
				BufferedReader br = new BufferedReader(lector);
				String contenidoLinea;
				boolean idEncontrado = false;
				
				// Voy leyendo línea por línea hasta que no haya más líneas
				while((contenidoLinea=br.readLine())!=null) {
					String [] lineaSeparada = contenidoLinea.split("::");
					// Uso el caracter : para separar entre el id y el array de categorías favoritas
					if (lineaSeparada.length == 2) {
						if (Integer.parseInt(lineaSeparada[0].charAt(1)+""+lineaSeparada[0].charAt(2)) == id ){
							
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
				br.close();
				lector.close();
			} catch (IOException | NumberFormatException | StringIndexOutOfBoundsException e) {
				// Si ocurre algún error en la lectura no se guardan las categorías
				categorias = null;
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
		File fichero = new File("src/txt/configuracion.txt");
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
				
				br.close();
				lector.close();
			} catch (IOException e) {
				lineas.add("");
			}
			
		} else {
			lineas.add("");
		}

		return lineas;
	}

	public static String leerHora() {
		File fichero = new File("src/txt/configuracion.txt");
		String hora = "";
		
		if (fichero.exists() && fichero.canRead()) {
			FileReader lector;
			try {
				// Voy leyendo línea por línea y buscando la hora
				lector = new FileReader(fichero);
				BufferedReader br = new BufferedReader(lector);
				String contenidoLinea;
				while((contenidoLinea=br.readLine())!=null) {
					String[] lineaSeparada = contenidoLinea.split("\\$");
					if (lineaSeparada.length > 1) {
						hora = lineaSeparada[1];
					}
				}
				
				br.close();
				lector.close();
			} catch (IOException e) {
				hora="hora no encontrada";
			}
			
		} else {
			hora="hora no encontrada";
		}

		return hora;
	}

	public static String leerInfoCorreo(int posicion) {
		File fichero = new File("src/txt/configuracion.txt");
		String info = "";
		
		if (fichero.exists() && fichero.canRead()) {
			FileReader lector;
			try {
				// Voy leyendo línea por línea y buscando la hora
				lector = new FileReader(fichero);
				BufferedReader br = new BufferedReader(lector);
				String contenidoLinea;
				int vueltas = 0;
				while((contenidoLinea=br.readLine())!=null) {
					String[] lineaSeparada = contenidoLinea.split("=");
					if (lineaSeparada.length > 1) {
						if (vueltas == posicion){
							info = lineaSeparada[1];
						}
						vueltas++;
					}
				}
				
				br.close();
				lector.close();
			} catch (IOException e) {
				info="ERROR";
			}
			
		} else {
			info="ERROR";
		}

		return info;
	}
}
