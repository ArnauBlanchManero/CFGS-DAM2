package TIENDA;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestorTienda {

	public static void main(String[] args) {
		System.out.println("BIENBENIDO AL GESTOR DE LA TIENDA DE PLANTAS");
		System.out.println("============================================");

		List<Planta> plantas = new ArrayList<Planta>();
		List<Empleado> empleados = new ArrayList<Empleado>();
		if(crear_jerarquía_ficheros()) {
			plantas = cargar_datos_plantas();
			empleados = cargar_datos_empleados();
			if (empleados == null) {
				System.out.println("No se hay empleados para trabajar.");
			}
			// TODO iniciar por empleado
			// TODO funciones menus empleado
			// TODO guardar datos
		}
	}

	private static List<Empleado> cargar_datos_empleados() {
		// TODO Auto-generated method stub
		return null;
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
