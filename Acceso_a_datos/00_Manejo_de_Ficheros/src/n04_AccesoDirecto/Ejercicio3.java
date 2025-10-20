package n04_AccesoDirecto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ejercicio3 {
	static Scanner entrada = new Scanner(System.in);
	public static void main(String[] args) {
		File fichero = new File("personas2.dat");
		if (!fichero.exists()) {
			try {
				fichero.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String nombrePersona;
		int edadPersona;
		List<Persona> personas = new ArrayList<Persona>();
		Persona persona;
		System.out.println("Introduce los datos de tres personas: ");
		for (int i = 0; i < 3; i++) {
			char[] nombrePersona2 = new char[10];
			do {
				System.out.print("Nombre: ");
				nombrePersona = entrada.next();
				entrada.nextLine();
			}while(nombrePersona.length()>10);
			for (int j = 0; j < nombrePersona.length(); j++) {
				nombrePersona2[j] = nombrePersona.charAt(j);
			}
			for (int j = nombrePersona.length(); j < 10; j++) {
				nombrePersona2[j] = ' ';
			}
			System.out.print("Edad: ");
			edadPersona = entrada.nextInt();
			entrada.nextLine();
			persona = new Persona(nombrePersona2, edadPersona);
			personas.add(persona);
			System.out.println("");
		}
		try {
			RandomAccessFile raf = new RandomAccessFile(fichero, "rw");
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 10; j++) {
					raf.writeChar(personas.get(i).getNombre()[j]);
				}
				raf.writeInt(personas.get(i).getEdad());
			}
			mostrarPersonas(fichero);
			raf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private static void mostrarPersonas(File fichero) {
		try {
			RandomAccessFile raf = new RandomAccessFile(fichero, "rw");
			raf.seek(0);
			for (int i = 0; i < 3; i++) {
				System.out.print("Nombre: ");
				for (int j = 0; j < 10; j++) {
					System.out.print(raf.readChar());
				}
				System.out.print("Edad: ");
				System.out.print(raf.readInt());
				System.out.println("");
			}
			raf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}