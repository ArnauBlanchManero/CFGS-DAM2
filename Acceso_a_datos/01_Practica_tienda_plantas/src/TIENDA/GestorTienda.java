package TIENDA;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestorTienda {
	static Scanner read = new Scanner(System.in);
	public static void main(String[] args) {
		System.out.println("BIENBENIDO AL GESTOR DE LA TIENDA DE PLANTAS");
		System.out.println("============================================");

		List<Planta> plantas = new ArrayList<Planta>();
		List<Empleado> empleados = new ArrayList<Empleado>();
		if(crear_jerarquía_ficheros()) {
			plantas = cargar_datos_plantas();
			empleados = cargar_datos_empleados();
			if (empleados != null) {
				// iniciar sesión por empleado
				int intentos = 3;
				Empleado empleado = iniciar_sesión(empleados);
				while (empleado == null && intentos > 0) {
					System.out.println("El id o la contraseña están mal, te quedan "+intentos+ " intentos.");
					empleado = iniciar_sesión(empleados);
					intentos --;
				}
				if (intentos > 0) {
					System.out.println("Bienvenido "+empleado.getNombre());
					// TODO funciones menus empleado
					if (empleado.getCargo().equals("Vendedor")) {
						menu_vendedor(empleado, plantas);
					}else if (empleado.getCargo().equals("Gestor")) {
						menu_gestor(empleado, plantas, empleados);
					} else {
						System.out.println("No se ha encontrado el cargo del empleado.");
					}
					// TODO guardar datos
					guardar_datos(plantas, empleados);
				} else {
					System.out.println("Te has quedado sin intentos, reinicia la aplicación.");
				}
			} else {
				System.out.print("¿Quieres añadir los empleados por defecto? (si o no): ");
				if(read.next().equalsIgnoreCase("si")) {
					crear_empleados_por_defecto();
				}
				read.nextLine();
			}
		}
	}

	private static void guardar_datos(List<Planta> plantas, List<Empleado> empleados) {
		// TODO Auto-generated method stub
		
	}

	private static void menu_gestor(Empleado empleado, List<Planta> plantas, List<Empleado> empleados) {
		// TODO Auto-generated method stub
		
	}

	private static void menu_vendedor(Empleado empleado, List<Planta> plantas) {
		// TODO Auto-generated method stub
		
	}

	private static Empleado iniciar_sesión(List<Empleado> empleados) {
		Empleado seleccionado = null;
		System.out.println("\nIniciar sesión\n");
		System.out.print("Id: ");
		String id = read.nextLine();
		System.out.print("Contraseña: ");
		String passwd = read.nextLine();
		for (int i = 0; i < empleados.size(); i++) {
			if (id.compareToIgnoreCase(Integer.toString(empleados.get(i).getId()))==0) {
				if (passwd.compareTo(empleados.get(i).getPasswd())==0) {
					seleccionado = empleados.get(i);
				}
			}
		}
		return seleccionado;
	}

	private static void crear_empleados_por_defecto() {
		File listaEmpleadosFichero = new File("EMPLEADOS/empleados.dat");
		ArrayList<Empleado> listaEmpleados = new ArrayList<Empleado>();
		try {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(listaEmpleadosFichero.getPath()));
		listaEmpleados.add(new Empleado(1452, "Teresa", "asb123", "Vendedor"));
		listaEmpleados.add(new Empleado(156, "Miguel Ángel", "123qwes", "Gestor"));
		listaEmpleados.add(new Empleado(7532, "Natalia", "xs21qw4", "Gestor"));
		out.writeObject(listaEmpleados);
		out.close();
		System.out.println("Empleados añadidos.");
		} catch (FileNotFoundException e) {
			System.out.println("Ha ocurrido un error en la escritura del fichero empleados.dat.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Ha ocurrido un error en la escritura del fichero empleados.dat.");
			e.printStackTrace();
		}
	}

	private static List<Empleado> cargar_datos_empleados() {
		
		File listaEmpleadosFichero = new File("EMPLEADOS/empleados.dat");
		ArrayList<Empleado> listaEmpleados = new ArrayList<Empleado>();
		ObjectInputStream ois;
		FileInputStream fis;
		try {
			fis = new FileInputStream(listaEmpleadosFichero.getPath());
			ois = new ObjectInputStream(fis);
			listaEmpleados = (ArrayList<Empleado>) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			System.out.println("Ha ocurrido un error en la lectura del fichero empleados.dat.");
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			System.out.println("Ha ocurrido un error en la lectura del fichero empleados.dat.");
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			System.out.println("Ha ocurrido un error en la lectura del fichero empleados.dat.");
			e.printStackTrace();
			return null;
		}
		return listaEmpleados;
	}

	private static List<Planta> cargar_datos_plantas() {
		// TODO Auto-generated method stub
		return null;
	}

	private static boolean crear_jerarquía_ficheros() {
		List<File> directorios = new ArrayList<File>();
		List<File> ficheros = new ArrayList<File>();
		directorios.add(new File("PLANTAS"));
		directorios.add(new File("EMPLEADOS"));
		directorios.add(new File(directorios.get(1).getPath(), "BAJA"));
		directorios.add(new File("TICKETS"));
		directorios.add(new File("DEVOLUCIONES"));
		
		for (File directorio : directorios) {
			if(!directorio.exists()) {
				directorio.mkdir();
			}
		}
		
		ficheros.add(new File(directorios.get(0).getPath(), "plantas.xml"));
		ficheros.add(new File(directorios.get(0).getPath(), "plantasBaja.xml"));
		ficheros.add(new File(directorios.get(0).getPath(), "plantas.dat"));
		ficheros.add(new File(directorios.get(0).getPath(), "plantasbaja.dat"));
		ficheros.add(new File(directorios.get(1).getPath(), "empleados.dat"));
		ficheros.add(new File(directorios.get(2).getPath(),"empleadosBaja.dat"));
		ficheros.add(new File(directorios.get(3).getPath(), "0.txt"));
		ficheros.add(new File(directorios.get(4).getPath()));

		for (File fichero : ficheros) {
			if(!fichero.exists()) {
				try {
					fichero.createNewFile();
				} catch (IOException e) {
					System.out.println("Se ha producido un error en la creación de la jerarquía de ficheros.");
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}

}
