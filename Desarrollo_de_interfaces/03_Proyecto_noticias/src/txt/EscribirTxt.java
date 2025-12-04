package txt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import usuarios.Usuario;

public class EscribirTxt {
	
	public static boolean escribirCategorias(int id, boolean[] periodicosUsuario) {
		boolean lecturaCorrecta = true;
		File fichero = new File("src/txt/noticiasUsuarios.txt");
		if (fichero.exists() && fichero.canWrite()) {
			try {
				FileWriter escribir = new FileWriter(fichero, true);
				BufferedWriter buffer = new BufferedWriter(escribir);
				buffer.newLine();
				buffer.write("#"+id+":");
				for (boolean b : periodicosUsuario) {
					if (b) {
						buffer.write("1");
					} else {
						buffer.write("0");
					}
				}
				buffer.close();
			} catch (IOException e) {
				lecturaCorrecta = false;
			}
		} else {
			lecturaCorrecta = false;
		}
		return lecturaCorrecta;
	}

	/*
	public static boolean sumarVecesLogueado(int id, boolean[] cs) {
		File fichero = new File("src/txt/credencialesUsuarios.txt");
		boolean lecturaCorrecta = true;
		if (fichero.exists() && fichero.canRead() && fichero.canWrite()) {
			try {
				int idLeer = 0;
				String contenidoLinea;
				FileReader lector;
				BufferedReader br = new BufferedReader(lector);
				do {
					contenidoLinea = br.readLine();
					if(contenidoLinea != null && !contenidoLinea.equals("")) {
						idLeer = Integer.parseInt(contenidoLinea.charAt(0)+"");
					} else {
						System.out.println("ERROR. No he podido leer la línea: "+contenidoLinea+".");
					}
				} while(contenidoLinea!=null);
				Integer cantidadAnterior = Integer.valueOf(contenidoLinea.split("·!·")[5]);
				FileWriter escribir = new FileWriter(fichero, true);
				BufferedWriter buffer = new BufferedWriter(escribir);
				buffer.newLine();
				buffer.write("#"+id+":");
				for (boolean b : periodicosUsuario) {
					if (b) {
						buffer.write("1");
					} else {
						buffer.write("0");
					}
				}
				buffer.close();
			} catch (IOException e) {
				lecturaCorrecta = false;
			}
		} else {
			lecturaCorrecta = false;
		}
		return lecturaCorrecta;
	}
	*/
	
}
