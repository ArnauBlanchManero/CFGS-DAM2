package ModificarFicheros;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Ejercicio4 {

	public static void main(String[] args) {
		//File directorio = new File("C:/Users/DAM/eclipse-workspace/Acceso a datos");
		File directorio = new File("/home/arnau/GitHub/CFGS-DAM2/Acceso_a_datos/Manejo_de_Ficheros");
		if (directorio.exists() && directorio.isDirectory()) {
			//File fichero1 = new File ("C:/Users/DAM/eclipse-workspace/Acceso a datos/Fichero1.txt");
			//File fichero2 = new File ("C:/Users/DAM/eclipse-workspace/Acceso a datos/Fichero2.txt");
			File fichero1 = new File ("/home/arnau/GitHub/CFGS-DAM2/Acceso_a_datos/Manejo_de_Ficheros/Fichero1.txt");
			File fichero2 = new File ("/home/arnau/GitHub/CFGS-DAM2/Acceso_a_datos/Manejo_de_Ficheros/Fichero2.txt");
			if (!fichero1.exists()) {
				try {
					fichero1.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (!fichero2.exists()) {
				try {
					fichero2.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fichero1.exists() && fichero2.exists()) {
				File ficheroEscritura = new File (directorio, "FicheroDestino.txt");
				if (!ficheroEscritura.exists()) {
					try {
						ficheroEscritura.createNewFile();
						lecturaFicheros(fichero1, ficheroEscritura);
						lecturaFicheros(fichero2, ficheroEscritura);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else {
					lecturaFicheros(fichero1, ficheroEscritura);
					lecturaFicheros(fichero2, ficheroEscritura);
				}
			} else {
				System.out.println("No se han creado correctamente los ficheros.");
			}
		} else {
			System.out.println("No se encuentra la ruta");
		}
		System.out.println("FIN");
	}

	private static void lecturaFicheros(File fichero, File ficheroEscritura) {
		if (fichero.canRead()) {
			System.out.println("Puedes leer el fichero.");
			try {
				FileReader lector = new FileReader(fichero); //Esto me devuelve caracter a caractrer
				BufferedReader buffer = new BufferedReader(lector); // Aquí ya sí que leo líneas
				String linea = buffer.readLine();
				if (ficheroEscritura.canWrite()) {
					FileWriter escritura = new FileWriter(ficheroEscritura,true);
					PrintWriter pw = new PrintWriter(escritura); // Hace lo mismo que la de abajo
					//BufferedWriter bw = new BufferedWriter(escritura);
					while (linea!=null) {
						System.out.println(linea);
						pw.println(linea+"");
						linea=buffer.readLine();
					}
					buffer.close();
					pw.close();
					System.out.println("Has escrito datos en el fichero.");
				} else {
					System.out.println("No se puede escribir en el fichero de destino");
				}
			} catch (IOException e) {
				e.getMessage();
				System.out.println("Problemas con el disco.");
			}
		} else {
			System.out.println("No puedes leer el fichero.");
		}		
	}

}
