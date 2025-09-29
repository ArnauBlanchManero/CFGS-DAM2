package FicherosBinarios;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class OutputStream {

	public static void escrituraDatos() {
		File ficheroDatos = new File("datos.dat");

		try {
			if (!ficheroDatos.exists()) {
				ficheroDatos.createNewFile();
			}

			DataOutputStream dos = new DataOutputStream(new FileOutputStream(ficheroDatos));
			dos.writeInt(2);
			dos.writeDouble(1.2);
			dos.writeBoolean(false);
			dos.writeUTF("Hola que tal");
			
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();		}
	}

	public static void escrituraPersonas(ArrayList<Persona> personas) {

		String fichero = "personas.dat";

		File ficheroEscritura = new File(fichero);
		if (!ficheroEscritura.exists()) {
			try {
				ficheroEscritura.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			if (ficheroEscritura.isFile()) {
				try {
					// Lo abro para escritura y escribo un flujo de datos
					FileOutputStream fos = new FileOutputStream(ficheroEscritura);
					// El tipo de dato que tiene que transformar es un objeto en bytes
					ObjectOutputStream oos = new ObjectOutputStream(fos);

					oos.writeObject(personas);
					oos.close();

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			} else {
				System.out.println("No es un fichero");
			}
		}

	}

	public static void main(String[] args) {

		ArrayList<Persona> personas = new ArrayList<Persona>();
		personas.add(new Persona("Ruben", 20));
		personas.add(new Persona("Luis", 21));
		personas.add(new Persona("Mateo", 23));
		escrituraPersonas(personas);
		escrituraDatos();

	}

}
