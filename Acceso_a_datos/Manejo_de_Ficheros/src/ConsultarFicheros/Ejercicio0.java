package ConsultarFicheros;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Ejercicio0 {

	public static void main(String[] args) {
		File fichero = new File("FicheroEjemplo3.txt");
		//File fichero = new File("C:/Users/DAM/Documents/Datos.txt");
		
		if (fichero.exists()) {
			System.out.println("Nombre del fichero: "+fichero.getName());
			System.out.println("Ruta: "+ fichero.getAbsolutePath());
			System.out.println("Tamaño: "+ fichero.length());
			if (fichero.canRead()) {
				System.out.println("Puedes leer el fichero.");
				try {
					FileReader lector = new FileReader(fichero); //Esto me devuelve caracter a caractrer
					BufferedReader buffer = new BufferedReader(lector); // Aquí ya sí que leo líneas
					String linea = buffer.readLine();
					while (linea!=null) {
						System.out.println(linea);
						linea=buffer.readLine();
					}
				} catch (IOException e) {
					e.getMessage();
					System.out.println("Problemas con el disco.");
				}
			} else {
				System.out.println("No puedes leer el fichero.");
			}
			//fichero.delete();
		} else {
			System.out.println("El fichero no existe actualmente");
			try {
				fichero.createNewFile();
				System.out.println("Creado exitosamente");
				FileWriter escritura = new FileWriter(fichero);
				PrintWriter pw = new PrintWriter(escritura); // Hace lo mismo que la de abajo
				//BufferedWriter bw = new BufferedWriter(escritura);
				for (int i = 1; i <= 10; i++) {
					pw.println("Línea: "+i);
				}
				pw.close();
				System.out.println("Has escrito datos en el fichero.");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("No puedo crear el fichero");
			}
		}

	}

}
