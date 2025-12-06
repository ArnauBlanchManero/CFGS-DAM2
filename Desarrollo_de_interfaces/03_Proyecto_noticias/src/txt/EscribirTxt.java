package txt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import usuarios.Usuario;

/*
 * Trabajo realizado por Arnau Blanch Manero
 */

public class EscribirTxt {
	
	public static boolean escribirCategorias(int id, boolean[] periodicosUsuario) {
		// Guardo las categorçias vaforitas de un usuario en el fichero correspondiente
		boolean escrituraCorrecta = true;
		File fichero = new File("src/txt/noticiasUsuarios.txt");
		
		if (fichero.exists() && fichero.canWrite()) {
			try {
				// Pongo el parámetro true para escribir a continuación y no sobreesribir
				FileWriter escribir = new FileWriter(fichero, true);
				BufferedWriter buffer = new BufferedWriter(escribir);
				
				// Escribo el id en segunda posición y separado con : porque así es como luego lo leo
				buffer.newLine();
				buffer.write("#"+id+":");
				
				for (boolean b : periodicosUsuario) {
					// Escribo 1 si ese periódico es su favorito y 0 si no
					if (b) {
						buffer.write("1");
					} else {
						buffer.write("0");
					}
					
				}
				
				buffer.close();
				
			} catch (IOException e) {
				escrituraCorrecta = false;
			}
			
		} else {
			escrituraCorrecta = false;
		}
		return escrituraCorrecta;
	}

}
