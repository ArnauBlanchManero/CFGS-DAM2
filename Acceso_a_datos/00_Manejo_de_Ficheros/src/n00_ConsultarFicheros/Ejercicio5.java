package n00_ConsultarFicheros;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio5 {
	static Scanner entrada = new Scanner(System.in);
	public static void main(String[] args) {
		File ficheroPermisos = new File("Permisos.txt");
		if (!ficheroPermisos.exists()) {
			System.out.println("El fichero no existe");
			try {
				ficheroPermisos.createNewFile();
				System.out.println("Se ha creado el fichero");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			char[] permisos = new char[4];
			if (ficheroPermisos.canRead()) {
				permisos[0]= 'r';
			} else {
				permisos[0]= '-';
			}
			if (ficheroPermisos.canWrite()) {
				permisos[1]= 'w';
			} else {
				permisos[1]= '-';
			}
			if (ficheroPermisos.canExecute()) {
				permisos[2]= 'x';
			} else {
				permisos[2]= '-';
			}
			permisos[3]='\0';
			System.out.printf("Los permisos de %s son: %c%c%c\n", ficheroPermisos.getName(), permisos[0],permisos[1],permisos[2]);
			String[] cambiarPermisos;
			System.out.print("Escribe los permisos que quieras para el fichero: ");
			String respuesta = entrada.next();
			cambiarPermisos = respuesta.split("");
			if (cambiarPermisos[0].equals("r")) {
				ficheroPermisos.setReadable(true);
			} else if (cambiarPermisos[0].equals("-")){
				ficheroPermisos.setReadable(false);
			} else {
				System.out.println("No entindo a lo que te refieres con "+cambiarPermisos[0]);
			}
			if (cambiarPermisos[1].equals("w")) {
				ficheroPermisos.setWritable(true);
			} else if (cambiarPermisos[1].equals("-")){
				ficheroPermisos.setWritable(false);
			} else {
				System.out.println("No entindo a lo que te refieres con "+cambiarPermisos[1]);
			}
			if (cambiarPermisos[2].equals("x")) {
				ficheroPermisos.setExecutable(true);
			} else if (cambiarPermisos[2].equals("-")){
				ficheroPermisos.setExecutable(false);
			} else {
				System.out.println("No entindo a lo que te refieres con "+cambiarPermisos[2]);
			}
			
			if (ficheroPermisos.canRead()) {
				permisos[0]= 'r';
			} else {
				permisos[0]= '-';
			}
			if (ficheroPermisos.canWrite()) {
				permisos[1]= 'w';
			} else {
				permisos[1]= '-';
			}
			if (ficheroPermisos.canExecute()) {
				permisos[2]= 'x';
			} else {
				permisos[2]= '-';
			}
			System.out.printf("Los nuevos permisos de %s son: %c%c%c\n", ficheroPermisos.getName(), permisos[0],permisos[1],permisos[2]);
		}
	}

}
