package n02_ModificarFicheros;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Ejercicio2 {
	static Scanner entrada = new Scanner(System.in);
	public static void main(String[] args) {
		File fichero = new File("registroDeUsuario.txt");
		if (!fichero.exists()) {
			System.out.println("No existe el fichero"+fichero.getName());
			try {
				fichero.createNewFile();
				System.out.println("Se ha creado el fichero"+fichero.getName());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Escribe lo que quieras guardar en el fichero y acaba escribiendo 'fin'");
		System.out.print("Escribe: ");
		String frase = entrada.nextLine();
		if (fichero.canWrite()) {
			try {
				FileWriter escribir = new FileWriter(fichero);
				BufferedWriter bw = new BufferedWriter(escribir);
				while (!frase.equalsIgnoreCase("fin")) {
					bw.write(frase+'\n');
					System.out.println("La frase que se ha añadido al fichero: "+frase);
					System.out.print("Escribe: ");
					frase = entrada.nextLine();
				}
				System.out.println("Fin del escribimiento");
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("No puedo escribir.");
		}
		
	}

}
