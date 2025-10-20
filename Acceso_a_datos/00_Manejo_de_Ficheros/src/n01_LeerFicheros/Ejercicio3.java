package n01_LeerFicheros;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Ejercicio3 {

	public static void main(String[] args) {
		File fichero = new File("Restaurants.csv");
		List<String> frases = new ArrayList<String>();
		int vuelta = 0;
		if (fichero.exists()) {
			if (fichero.canRead()) {
				FileReader lector;
				try {
					lector = new FileReader(fichero);
					BufferedReader br = new BufferedReader(lector);
					frases.add(br.readLine());
					while( frases.get(vuelta) != null) {
						frases.add(br.readLine());
						System.out.println(frases.get(vuelta) );
						vuelta++;
					}
					for (String frase : frases) {
						if (frase != null) {
							List<String> valores = new ArrayList<String>();
							valores = dividir(frase);
							for (String valor : valores) {
								System.out.println("Campo: "+valor);
							}
							System.out.println("");
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("No existe el fichero buscado");
		}

	}

	private static List<String> dividir(String frase) {
		List<String> retval = new ArrayList<String>();
		String valor = "";
		char caracter;
		for (int i = 0; i < frase.length(); i++) {
			caracter=frase.charAt(i);
			if (caracter == '"') {
				i++;
				while (i < frase.length() && (caracter = frase.charAt(i)) != '"') {
					valor += caracter;
					i++;
				}
				retval.add(valor);
				valor = "";
				i++;
			} else if (caracter == ','){
				retval.add(valor);
				valor = "";
			} else {
				valor += caracter;
			}
		}
		retval.add(valor);
		return retval;
	}

}
