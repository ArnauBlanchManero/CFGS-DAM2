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
				if (lineaSeparada.length == 6)
					usuarios.add(new Usuario(Integer.parseInt(lineaSeparada[0]), lineaSeparada[1], lineaSeparada[2], lineaSeparada[3], lineaSeparada[4].equals("1"), Integer.parseInt(lineaSeparada[5])));
			}
		}

		return usuarios;
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
