package txt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import usuarios.Usuario;

/*
 * Trabajo realizado por Arnau Blanch Manero
 */

public class EscribirTxt {
	
	public static boolean escribirCategorias(int id, boolean[] periodicosUsuario) {
		// Guardo las categorías favoritas de un usuario en el fichero correspondiente
		boolean escrituraCorrecta = true;
		File fichero = new File("src/txt/noticiasUsuarios.txt");
		
		if (fichero.exists() && fichero.canWrite()) {
			try {
				// Pongo el parámetro true para escribir a continuación y no sobreesribir
				FileWriter escribir = new FileWriter(fichero, true);
				BufferedWriter buffer = new BufferedWriter(escribir);
				
				// Escribo el id en segunda posición y separado con : porque así es como luego lo leo
				buffer.newLine();
				buffer.write("#"+(id < 10 ? "0"+id : id )+":");
				
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

	public static boolean guardarUsuario(int nuevoID, String nombre, String contraseña, String correo) {
		// Guardo la información del usuario en el fichero
		boolean usuarioGuardado = true;
		File fichero = new File("src/txt/credencialesUsuarios.txt");
		
		if (fichero.exists() && fichero.canWrite()) {
			try {
				// Pongo el parámetro true para escribir a continuación y no sobreesribir
				FileWriter escribir = new FileWriter(fichero, true);
				BufferedWriter buffer = new BufferedWriter(escribir);
				
				// Escribo en una nueva línea los parámetros separados por ·!· y en orden
				buffer.newLine();
				buffer.write((nuevoID < 10 ? "0"+nuevoID : nuevoID )+"·!·");
				buffer.write(nombre+"·!·");
				buffer.write(correo+"·!·");
				buffer.write(contraseña+"·!·");
				buffer.write("0"); // Ya que no tiene permisos de administración
				
				buffer.close();
				
			} catch (IOException e) {
				usuarioGuardado = false;
			}
			
		} else {
			usuarioGuardado = false;
		}
		return usuarioGuardado;
	}

	public static boolean eliminarUsuario(int idUsuarioBorrar) {
		// TODO Eliminar datos usuario
		boolean usuarioEliminado = true;

		Path ficheroUsuarios = Paths.get("src/txt/credencialesUsuarios.txt");
		Path ficheroNoticias = Paths.get("src/txt/noticiasUsuarios.txt");
		
		if (Files.exists(ficheroUsuarios) && Files.isReadable(ficheroUsuarios) && Files.isWritable(ficheroUsuarios) && Files.exists(ficheroNoticias) && Files.isReadable(ficheroNoticias) && Files.isWritable(ficheroNoticias)) {
			try {
				List<String> lineasUsuarios = Files.readAllLines(ficheroUsuarios);
				List<String> lineasNoticias = Files.readAllLines(ficheroNoticias);

				lineasUsuarios.removeIf(linea -> {
					// Elimino las líneas vacías
					if(linea.trim().isEmpty()) return true;
					try {
						int id = Integer.parseInt(linea.charAt(0)+""+linea.charAt(1));
						// Elimino al usuario con el id buscado
						if (id == idUsuarioBorrar)
							return true;
					} catch (NumberFormatException e) {
						return false;
					}
					return false;
				});
				lineasNoticias.removeIf(linea -> {
					// Elimino las líneas vacías
					if(linea.trim().isEmpty()) return true;
					try {
						int id = Integer.parseInt(linea.charAt(1)+""+linea.charAt(2));
						// Elimino al usuario con el id buscado
						if (id == idUsuarioBorrar)
							return true;
					} catch (NumberFormatException e) {
						return false;
					}
					return false;
				});
				
				Files.write(ficheroUsuarios, lineasUsuarios);
				Files.write(ficheroNoticias, lineasNoticias);
				
			} catch (IOException e) {
				usuarioEliminado = false;
				e.printStackTrace();
			}
		} else {
			usuarioEliminado = false;
		}
		
		return usuarioEliminado;
	}

}
