package n07_LibreriaJavaNIO;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Stream;

public class Main {
	public static void LeerFiles() {
		Path ruta = Paths.get("primos.txt");
		try {
			String contenido = Files.readString(ruta);
			System.out.println(contenido);
			System.out.println("----------------");
			List<String> listaContenido = Files.readAllLines(ruta);
			for (String linea : listaContenido) {
				System.out.println(linea);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void EscribirFiles() {
		Path ruta = Paths.get("FicheroEscritura.txt");
		String continido = "Hola, ésta es mi primera escritura. ♥♥♥";
		try {
			Files.write(ruta, continido.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private static void Copiar() {
		Path rutaOrigen = Paths.get("FicheroEscritura.txt");
		Path tutaDestino = Paths.get("FicheroCopia.txt");
		try {
			Files.copy(rutaOrigen, tutaDestino, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void listarContenidos() {
		Path directorio = Path.of(".");
		try {
			Stream<Path> flujo= Files.list(directorio);
			flujo.forEach(archivo->System.out.println(archivo.getFileName()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void propiedadesFicheros() {
		Path ruta = Path.of("Fichero1.txt");
		System.out.println("Fichero existe: " + Files.exists(ruta));
		System.out.println("Fichero es directoriio: "+Files.isDirectory(ruta));
	}
	private static void borrarFichero() {
		Path fichero = Path.of("BorrarFichero.txt");
		try {
			Files.deleteIfExists(fichero);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		LeerFiles();
		EscribirFiles();
		Copiar();
		listarContenidos();
		propiedadesFicheros();
		borrarFichero();
	}

}
