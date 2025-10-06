package AccesoDirecto;

import java.io.File;
import java.util.Scanner;

public class Ejercicio2 {

	static  Scanner entrada = new Scanner(System.in);
	public static void main(String[] args) {
		System.out.print("Cuantos productos quieres añadir: ");
		int num_productos = entrada.nextInt();
		int [] ids = new int[num_productos];
		int [] cantidades = new int [num_productos];
		double [] precios = new double [num_productos];
		for (int i = 0; i < num_productos; i++) {
			ids[i]=i;
			System.out.print("Cantidad del producto " +i+": ");
			cantidades[i]=entrada.nextInt();
			entrada.nextLine();
			System.out.print("Precio del producto " +i+": ");
			precios[i]=entrada.nextInt();
			entrada.hasNextLine();
		}
		File fichero = new File("productos.dat");
		almacenarProductos(fichero, ids, cantidades, precios, num_productos);
		visualizarProductos(fichero, ids, cantidades, precios, num_productos);
		visualizar1producto(fichero, ids, cantidades, precios, num_productos);
		borrar1producto(fichero, ids, cantidades, precios, num_productos);
		modificar1producto(fichero, ids, cantidades, precios, num_productos);
	}

}
