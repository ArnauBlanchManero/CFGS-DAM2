package RandomAccessFile;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class AccesoAleatorio {

	public static void main(String[] args) {
		int nummeroLista;// 4 bytes
		String nombre = "Pedro";// sabemos que un caracter 2 bytes * 5 = 10 bytes
		double nota; // 8bytes

		try {
			File fichero = new File("datos.dat");

			// r -> Lectura
			// w -> Escritura
			// rw -> Lectura y Escritura

			RandomAccessFile raf = new RandomAccessFile(fichero, "rw");

			//raf.writeInt(0); // Int ocupa 4 bytes, si fuera doble seria 8
			//raf.writeInt(1); // 4 bytes
			//raf.writeInt(2); // 4 bytes
			//System.out.println(raf.getFilePointer()); // 12 bytes

			/*
			 * 0 	4 	8 	12 bytes 
			 * | 	| 	| 	| 
			 * 0 	1 	2 	X Valor raf
			 */

			//raf.seek(4);// Posiciona el puntero al byte 4, es decir al 1
			//System.out.println(raf.readInt()); // imprime el 1
			
			raf.writeInt(1); //4
			raf.writeChars(nombre); //10
			raf.writeDouble(5.12); //8
			System.out.println("Valor del archivo en bytes: "+raf.getFilePointer()); //22 bytes
		

			raf.seek(0);
			while (raf.getFilePointer() < raf.length()) {
				System.out.println(raf.readInt());
				for (int i = 0; i < nombre.length(); i++) {
					System.out.print(raf.readChar());
					
				}
				System.out.println("\n"+raf.readDouble());

			}

			raf.close();

		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
