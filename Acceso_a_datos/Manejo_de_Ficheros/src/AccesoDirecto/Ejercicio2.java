package AccesoDirecto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
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
			precios[i]=entrada.nextDouble();
			entrada.hasNextLine();
		}
		File fichero = new File("productos.dat");
		if (!fichero.exists()) {
			try {
				fichero.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		boolean ficheroEscrito =false;
		ficheroEscrito = almacenarProductos(fichero, ids, cantidades, precios, num_productos);
		if (ficheroEscrito) {
			visualizarProductos(fichero, ids, cantidades, precios, num_productos);
			visualizar1producto(fichero, ids, cantidades, precios, num_productos);
			borrar1producto(fichero, ids, cantidades, precios, num_productos);
			modificar1producto(fichero, ids, cantidades, precios, num_productos);
		}
	}
	private static void modificar1producto(File fichero, int[] ids, int[] cantidades, double[] precios,
			int num_productos) {
		try {
			RandomAccessFile raf = new RandomAccessFile(fichero, "rw");
			int idUsuario;
			System.out.println();
			System.out.print("Escribe el ID del que quieras modificar: ");
			idUsuario = entrada.nextInt()*16;
			raf.seek(idUsuario);
			System.out.println("Información antes de ser modificada: ");
			System.out.println("ID: "+raf.readInt());
			System.out.println("Cantidad: "+raf.readInt());
			System.out.println("Precio: "+raf.readDouble());
			raf.seek(idUsuario+4);
			System.out.println("Actualiza los datos:");
			int nuevaCantidad;
			double nuevoPrecio;
			System.out.print("Nueva cantidad: ");
			nuevaCantidad = entrada.nextInt();
			entrada.nextLine();
			System.out.print("Nuevo precio: ");
			nuevoPrecio = entrada.nextDouble();
			entrada.nextLine();
			raf.writeInt(nuevaCantidad);
			raf.writeDouble(nuevoPrecio);
			raf.seek(idUsuario);
			System.out.println("Datos actualizados:");
			System.out.println("ID: "+raf.readInt());
			System.out.println("Cantidad: "+raf.readInt());
			System.out.println("Precio: "+raf.readDouble());
			raf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	private static void borrar1producto(File fichero, int[] ids, int[] cantidades, double[] precios,
			int num_productos) {
		try {
			RandomAccessFile raf = new RandomAccessFile(fichero, "rw");
			int idUsuario;
			System.out.println();
			System.out.print("Escribe el ID del que quieras eliminar: ");
			idUsuario = entrada.nextInt()*16;
			raf.seek(idUsuario);
			System.out.println("Información antes de ser eliminada: ");
			System.out.println("ID: "+raf.readInt());
			System.out.println("Cantidad: "+raf.readInt());
			System.out.println("Precio: "+raf.readDouble());
			raf.seek(idUsuario);
			// Molaría mover los productos que hay a continuación en el hueco que deja este producto eliminado
			int nuevoId = -1;
			int nuevaCantidad = -1;
			double nuevoPrecio = -1;
			raf.writeInt(nuevoId);
			raf.writeInt(nuevaCantidad);
			raf.writeDouble(nuevoPrecio);
			System.out.println("Datos eliminados.");
			raf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
	private static void visualizar1producto(File fichero, int[] ids, int[] cantidades, double[] precios,
			int num_productos) {
		try {
			RandomAccessFile raf = new RandomAccessFile(fichero, "rw");
			int idUsuario;
			System.out.print("Escribe el ID del que quieras saber información: ");
			idUsuario = entrada.nextInt()*16;
			raf.seek(idUsuario);
			System.out.println("ID: "+raf.readInt());
			System.out.println("Cantidad: "+raf.readInt());
			System.out.println("Precio: "+raf.readDouble());
			raf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private static void visualizarProductos(File fichero, int[] ids, int[] cantidades, double[] precios,
			int num_productos) {
		try {
			RandomAccessFile raf = new RandomAccessFile(fichero, "rw");
			raf.seek(0);
			for(int i = 0; i<num_productos;i++) {
				System.out.println("ID: "+raf.readInt());
				System.out.println("Cantidad: "+raf.readInt());
				System.out.println("Precio: "+raf.readDouble());
				System.out.println();
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
	private static boolean almacenarProductos(File fichero, int[] ids, int[] cantidades, double[] precios,
			int num_productos) {
		try {
			RandomAccessFile raf = new RandomAccessFile(fichero, "rw");
			for(int i = 0; i<num_productos;i++) {
				raf.writeInt(ids[i]);
				raf.writeInt(cantidades[i]);
				raf.writeDouble(precios[i]);
			}
			raf.close();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}

}
