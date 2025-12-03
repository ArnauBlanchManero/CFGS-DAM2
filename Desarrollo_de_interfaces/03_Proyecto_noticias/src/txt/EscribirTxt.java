package txt;

import java.io.File;

public class EscribirTxt {
	
	public static boolean escribirCategorias(int id, boolean[] periodicosUsuario) {
		boolean lecturaCorrecta = true;
		File fichero = new File("src/txt/noticiasUsuarios.txt");
		if (fichero.exists() && fichero.canRead()) {
			// TODO escribir las categorias del usuario con su id poniendo True o False
		} else {
			lecturaCorrecta = false;
		}
		return lecturaCorrecta;
	}
	
}
