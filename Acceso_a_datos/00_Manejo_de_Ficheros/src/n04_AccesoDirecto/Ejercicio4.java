package n04_AccesoDirecto;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ejercicio4 {
	static Scanner entrada = new Scanner(System.in);

	public static void main(String[] args) {
		File fichero = new File("libros.dat");
		if (!fichero.exists()) {
			try {
				fichero.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		List<Libro> libros = new ArrayList<Libro>();
		String tituloStr, autorStr;
		double precio;

		System.out.println("Introduce los datos de tres libros:");
		for (int i = 0; i < 3; i++) {
			char[] titulo = new char[20];
			char[] autor = new char[20];
			do {
				System.out.print("Título: ");
				tituloStr = entrada.nextLine();
			} while (tituloStr.length() > 20);

			for (int j = 0; j < tituloStr.length(); j++) titulo[j] = tituloStr.charAt(j);
			for (int j = tituloStr.length(); j < 20; j++) titulo[j] = ' ';

			do {
				System.out.print("Autor: ");
				autorStr = entrada.nextLine();
			} while (autorStr.length() > 20);

			for (int j = 0; j < autorStr.length(); j++) autor[j] = autorStr.charAt(j);
			for (int j = autorStr.length(); j < 20; j++) autor[j] = ' ';

			System.out.print("Precio: ");
			precio = entrada.nextDouble();
			entrada.nextLine();

			libros.add(new Libro(titulo, autor, precio));
			System.out.println("");
		}

		try {
			RandomAccessFile raf = new RandomAccessFile(fichero, "rw");
			for (int i = 0; i < libros.size(); i++) {
				for (int j = 0; j < 20; j++) raf.writeChar(libros.get(i).getTitulo()[j]);
				for (int j = 0; j < 20; j++) raf.writeChar(libros.get(i).getAutor()[j]);
				raf.writeDouble(libros.get(i).getPrecio());
			}
			raf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Libros guardados:");
		mostrarLibros(fichero);

		System.out.println("\nOrdenando por precio...");
		ordenarPorPrecio(fichero);

		System.out.println("\nFiltrar libros por precio:");
		System.out.print("Precio mínimo: ");
		double min = entrada.nextDouble();
		System.out.print("Precio máximo: ");
		double max = entrada.nextDouble();
		filtrarPorPrecio(fichero, min, max);
	}

	private static void mostrarLibros(File fichero) {
		try {
			RandomAccessFile raf = new RandomAccessFile(fichero, "r");
			while (raf.getFilePointer() < raf.length()) {
				char[] titulo = new char[20];
				for (int j = 0; j < 20; j++) titulo[j] = raf.readChar();
				char[] autor = new char[20];
				for (int j = 0; j < 20; j++) autor[j] = raf.readChar();
				double precio = raf.readDouble();
				System.out.print("Título: ");
				for (int j = 0; j < 20; j++) System.out.print(titulo[j]);
				System.out.print("Autor: ");
				for (int j = 0; j < 20; j++) System.out.print(autor[j]);
				System.out.println("Precio: " + precio);
			}
			raf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void ordenarPorPrecio(File fichero) {
		List<Libro> libros = new ArrayList<Libro>();
		try {
			RandomAccessFile raf = new RandomAccessFile(fichero, "r");
			while (raf.getFilePointer() < raf.length()) {
				char[] titulo = new char[20];
				for (int j = 0; j < 20; j++) titulo[j] = raf.readChar();
				char[] autor = new char[20];
				for (int j = 0; j < 20; j++) autor[j] = raf.readChar();
				double precio = raf.readDouble();
				libros.add(new Libro(titulo, autor, precio));
			}
			raf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < libros.size() - 1; i++) {
			for (int j = 0; j < libros.size() - i - 1; j++) {
				if (libros.get(j).getPrecio() > libros.get(j + 1).getPrecio()) {
					Libro aux = libros.get(j);
					libros.set(j, libros.get(j + 1));
					libros.set(j + 1, aux);
				}
			}
		}

		try {
			RandomAccessFile raf = new RandomAccessFile(fichero, "rw");
			raf.setLength(0);
			for (int i = 0; i < libros.size(); i++) {
				for (int j = 0; j < 20; j++) raf.writeChar(libros.get(i).getTitulo()[j]);
				for (int j = 0; j < 20; j++) raf.writeChar(libros.get(i).getAutor()[j]);
				raf.writeDouble(libros.get(i).getPrecio());
			}
			raf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		mostrarLibros(fichero);
	}

	private static void filtrarPorPrecio(File fichero, double min, double max) {
		try {
			RandomAccessFile raf = new RandomAccessFile(fichero, "r");
			while (raf.getFilePointer() < raf.length()) {
				char[] titulo = new char[20];
				for (int j = 0; j < 20; j++) titulo[j] = raf.readChar();
				char[] autor = new char[20];
				for (int j = 0; j < 20; j++) autor[j] = raf.readChar();
				double precio = raf.readDouble();
				if (precio >= min && precio <= max) {
					System.out.print("Título: ");
					for (int j = 0; j < 20; j++) System.out.print(titulo[j]);
					System.out.print("Autor: ");
					for (int j = 0; j < 20; j++) System.out.print(autor[j]);
					System.out.println("Precio: " + precio);
				}
			}
			raf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
