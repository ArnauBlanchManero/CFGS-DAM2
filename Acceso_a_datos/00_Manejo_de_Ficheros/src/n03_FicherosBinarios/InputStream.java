package n03_FicherosBinarios;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class InputStream {

	public static void LecturaDatos() {

		try {
			DataInputStream dis = new DataInputStream(new FileInputStream("datos.dat"));
			int entero = dis.readInt();
			double doble = dis.readDouble();
			boolean boleano = dis.readBoolean();
			String frase = dis.readUTF();

			System.out.println("\nEntero: " + entero);
			System.out.println("Double: " + doble);
			System.out.println("Boolean: " + boleano);
			System.out.println("Frase: " + frase);

		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public static void lecturaPersonas() {

		try {

			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("personas.dat"));
			ArrayList<Persona> personas = (ArrayList<Persona>) ois.readObject();
			System.out.println("Las personas almacenadas en el fichero son:");
			for (Persona persona : personas) {
				System.out.println(persona.toString());
			}

		} catch (IOException e) {
			// TODO: handle exception
			e.getStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		ArrayList<Persona> personas = new ArrayList<Persona>();
		personas.add(new Persona("Ruben", 20));
		personas.add(new Persona("Luis", 21));
		personas.add(new Persona("Mateo", 23));
		lecturaPersonas();
		LecturaDatos();

	}

}
