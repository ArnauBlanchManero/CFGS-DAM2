package ModificarFicheros;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Ejercicio1 {

	public static void main(String[] args) {
		File ficheroPrimos = new File("primos.txt");
		ArrayList<String> numPrimos = new ArrayList<>();
		if (!ficheroPrimos.exists()) {
			try {
				ficheroPrimos.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		boolean esPrimo;
		for (int i = 2; i < 500; i++) {
			esPrimo=true;
			for (int j = 2; j < Math.sqrt(i); j++) {
				if (i%j==0) {
					esPrimo = false;
				}
			}
			if (esPrimo) {
				numPrimos.add("Numero: "+i);
			}
		}
		try {
			FileWriter escribir = new FileWriter(ficheroPrimos);
			BufferedWriter buffer = new BufferedWriter(escribir);
			for (int i = 0; i < numPrimos.size(); i++) {
				buffer.write(String.valueOf(numPrimos.get(i)));
				//buffer.write(numPrimos.get(i)+"");
				buffer.newLine();
			}
			buffer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
