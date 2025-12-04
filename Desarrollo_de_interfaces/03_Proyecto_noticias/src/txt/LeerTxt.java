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

public class LeerTxt {
	
	public static ArrayList<Usuario> leerTodosUsuarios() throws IOException {
		File fichero = new File("src/txt/credencialesUsuarios.txt");
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		int vuelta = 0;
		if (fichero.exists() && fichero.canRead()) {
			FileReader lector;
			usuarios = new ArrayList<Usuario>();
			lector = new FileReader(fichero);
			BufferedReader br = new BufferedReader(lector);
			String contenidoLinea;
			while((contenidoLinea=br.readLine())!=null) {
				String [] lineaSeparada = contenidoLinea.split("·!·");
				if (lineaSeparada.length == 5)
					usuarios.add(new Usuario(Integer.parseInt(lineaSeparada[0]), lineaSeparada[1], lineaSeparada[2], lineaSeparada[3], lineaSeparada[4].equals("1"), leerCategorias(Integer.parseInt(lineaSeparada[0]))));
			}
		}

		return usuarios;
	}

	public static boolean[] leerCategorias(int id) {
		// TODO leer fichero y devolver categorias usuario segun su id
		boolean[] categorias = new boolean[Ventana.CANTIDAD_CATEGORIAS];
		File fichero = new File("src/txt/noticiasUsuarios.txt");
		if (fichero.exists() && fichero.canRead()) {
			FileReader lector;
			try {
				lector = new FileReader(fichero);
				BufferedReader br = new BufferedReader(lector);
				String contenidoLinea;
				boolean idEncontrado = false;
				while((contenidoLinea=br.readLine())!=null) {
					String [] lineaSeparada = contenidoLinea.split(":");
					if (lineaSeparada.length == 2) {
						if (Integer.parseInt(lineaSeparada[0].charAt(1)+"") == id ){
							idEncontrado = true;
							for (int i = 0; i < Ventana.CANTIDAD_CATEGORIAS; i++) {
								if(lineaSeparada[1].charAt(i)=='0') {
									categorias[i] = false;
								} else {
									categorias[i] = true;
								}
							}
						}
					}
				}
				if(!idEncontrado) {
					categorias = null;
				}
			} catch (IOException | NumberFormatException e) {
				categorias = null;
//				e.printStackTrace();
			}
		}
		return categorias;
	}

	public static ArrayList<String> leerTodasNoticias() {
		ArrayList<String> urlsParametrosPosicion = leerUrlsParametrosPosicion();
		ArrayList<String> titulares = new ArrayList<String>();
		for (int i = 0; i < urlsParametrosPosicion.size(); i++) {
			// TODO manejo de error si no hay url en txt
			String infoTitular [] = urlsParametrosPosicion.get(i).split(";");
			if(infoTitular.length >= 3) {
				Titular titular = new Titular(infoTitular[0], infoTitular[1]);
				try {
					titulares.add(titular.devolverTitular(Integer.parseInt(infoTitular[2])));
				if(titulares.get(i).equals("ERROR. Titular no encontrado"))
					return null;
				} catch (NumberFormatException | IndexOutOfBoundsException e) {
					titulares.add(titular.devolverTitular(0));
					if(titulares.get(i).equals("ERROR. Titular no encontrado"))
						return null;
				}
			}
		}
		return titulares;
	}

	private static ArrayList<String> leerUrlsParametrosPosicion() {
		File fichero = new File("src/txt/urlsNoticias.txt");
		ArrayList<String> lineas = new ArrayList<String>();
		if (fichero.exists() && fichero.canRead()) {
			FileReader lector;
			try {
				lector = new FileReader(fichero);
				BufferedReader br = new BufferedReader(lector);
				String contenidoLinea;
				while((contenidoLinea=br.readLine())!=null) {
					lineas.add(contenidoLinea);
				}
			} catch (IOException e) {
				lineas.add("");
				e.printStackTrace();
			}
		} else {
			lineas.add("");
		}

		return lineas;
	}
}
