package txt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
}
