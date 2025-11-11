package TIENDA;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class GestorTienda {
	static Scanner read = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("┌♣┐ ┌♣┐ ┌♣┐ ┌♣┐ ┌♣┐ ┌♣┐ ┌♣┐ ┌♣┐ ┌♣┐ ┌♣┐ ┌♣┐ ");
		System.out.println("┘ └ ┘ └ ┘ └ ┘ └ ┘ └ ┘ └ ┘ └ ┘ └ ┘ └ ┘ └ ┘ └ ");
		System.out.println("BIENVENIDO AL GESTOR DE LA TIENDA DE PLANTAS");

		ArrayList<Planta> plantas = new ArrayList<Planta>();
		ArrayList<Planta> plantasBaja = new ArrayList<Planta>();
		ArrayList<Empleado> empleados = new ArrayList<Empleado>();
		ArrayList<Empleado> empleadosBaja = new ArrayList<Empleado>();
		File listaPlantasFicheroXml = new File("PLANTAS/plantas.xml");
		if (crear_jerarquía_ficheros()) {
			File listaEmpleadosFichero = new File("EMPLEADOS/empleados.dat");
			empleados = cargar_datos_empleados(listaEmpleadosFichero);
			if (empleados != null) {
				// iniciar sesión por empleado
				int intentos = 3;
				Empleado empleado = iniciar_sesión(empleados);
				while (empleado == null && intentos > 0) {
					System.out.println("El id o la contraseña están mal, te quedan " + intentos + " intentos.");
					empleado = iniciar_sesión(empleados);
					intentos--;
				}
				if (intentos > 0) {
					System.out.println("\nSesión iniciada correctamente " + empleado.getNombre());
					plantas = cargar_datos_plantas(listaPlantasFicheroXml);
					if (plantas != null) {
						// TODO funciones menus empleado
						if (empleado.getCargo().equals("Vendedor")) {
							plantas = menu_vendedor(empleado, plantas);
						} else if (empleado.getCargo().equals("Gestor")) {
							File listaPlantasBajaFicheroXml = new File("PLANTAS/plantasBaja.xml");
							plantasBaja = (ArrayList<Planta>) cargar_datos_plantas(listaPlantasBajaFicheroXml);
							File listaEmpleadosBajaFichero = new File("EMPLEADOS/BAJA/empleadosBaja.dat");
							empleadosBaja = cargar_datos_empleados(listaEmpleadosBajaFichero);
							plantas = menu_gestor(empleado, plantas, plantasBaja, empleados, empleadosBaja);
						} else {
							System.out.println("No se ha encontrado el cargo del empleado.");
						}
						guardar_plantas(plantas, listaPlantasFicheroXml);
					} else {
						System.out.print("¿Quieres añadir las plantas por defecto? (si o no): ");
						if (read.next().equalsIgnoreCase("si")) {
							crear_plantas_por_defecto();
						}
						read.nextLine();
					}
					// TODO guardar datos
					// guardar_datos(plantas, empleados);
				} else {
					System.out.println("Te has quedado sin intentos, reinicia la aplicación.");
				}
			} else {
				System.out.print("¿Quieres añadir los empleados por defecto? (si o no): ");
				if (read.next().equalsIgnoreCase("si")) {
					crear_empleados_por_defecto();
				}
				read.nextLine();
			}
		}
		// guardar_empleados(empleados, listaEmpleadosFichero);
		// mostrar_catalogo_plantas(plantasBaja, empleados.get(0), plantasBaja);
		System.out.println("Aplicación cerrada.");
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
			if (!directorio.exists()) {
				directorio.mkdir();
			}
		}

		ficheros.add(new File(directorios.get(0).getPath(), "plantas.xml"));
		ficheros.add(new File(directorios.get(0).getPath(), "plantasBaja.xml"));
		ficheros.add(new File(directorios.get(0).getPath(), "plantas.dat"));
		ficheros.add(new File(directorios.get(0).getPath(), "plantasbaja.dat"));
		ficheros.add(new File(directorios.get(1).getPath(), "empleados.dat"));
		ficheros.add(new File(directorios.get(2).getPath(), "empleadosBaja.dat"));

		for (File fichero : ficheros) {
			if (!fichero.exists()) {
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

	private static Empleado iniciar_sesión(ArrayList<Empleado> empleados) {
		Empleado seleccionado = null;
		System.out.println("\nIniciar sesión");
		System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
		System.out.print("Id: ");
		String id = read.nextLine();
		System.out.print("Contraseña: ");
		String passwd = read.nextLine();
		for (int i = 0; i < empleados.size(); i++) {
			if (empleados.get(i).getId() == Integer.valueOf(id)) {
				if (passwd.compareTo(empleados.get(i).getPasswd()) == 0) {
					seleccionado = empleados.get(i);
				}
			}
		}
		return seleccionado;
	}

	private static ArrayList<Planta> menu_vendedor(Empleado empleado, ArrayList<Planta> plantas) {
		String respuesta;
		do {
			System.out.println("\n─────────────");
			System.out.println("Menú vendedor");
			System.out.println("─────────────");
			System.out.println("1. Catálogo de plantas");
			System.out.println("2. Vender/devolver plantas");
			System.out.println("3. Buscar ticket");
			System.out.println("4. Guardar y salir");
			System.out.print("Opción: ");
			respuesta = read.next();
			read.nextLine();

			while (!respuesta.matches("[1-4]")) {
				System.out.print("Introduce un número del 1 al 4: ");
				respuesta = read.next();
				read.nextLine();
			}
			System.out.println("");
			switch (respuesta) {
			case "1":
				plantas = mostrar_catalogo_plantas(plantas, empleado);
				break;
			case "2":
				plantas = menu_ventas(empleado, plantas);
				break;
			case "3":
				String numBuscar;
				System.out.print("Introduce el número de ticket que quieres buscar: ");
				numBuscar = read.next();
				read.nextLine();
				while (!numBuscar.matches("[0-9]*")) {
					System.out.print("Introduce un número válido: ");
					numBuscar = read.next();
					read.nextLine();
				}
				buscar_tiket(numBuscar);
				break;
			case "4":
				break;
			default:
				System.out.println("Opción inválida, vulva a intentarlo.");
				break;
			}
			// Visualizar catálogo de plantas
			// Generar ventas
			// Buscar por el número de ticket
			// Salir
		} while (!respuesta.equals("4"));
		return plantas;
	}

	private static ArrayList<Planta> menu_ventas(Empleado empleado, ArrayList<Planta> plantas) {
		String respuesta;
		do {
			System.out.println("\n-----------");
			System.out.println("Menú ventas");
			System.out.println("-----------");
			System.out.println("1. Vender plantas");
			System.out.println("2. Devolver plantas");
			System.out.println("3. Volver al menú");
			System.out.print("Opción: ");
			respuesta = read.next();
			read.nextLine();

			while (!respuesta.matches("[1-3]")) {
				System.out.print("Introduce un número del 1 al 3: ");
				respuesta = read.next();
				read.nextLine();
			}
			System.out.println("");
			switch (respuesta) {
			case "1":
				plantas = venta_plantas(empleado, plantas);
				break;
			case "2":
				plantas = devolver_plantas(plantas);
				break;
			case "3":
				break;
			default:
				System.out.println("Opción inválida, vulva a intentarlo.");
				break;
			}
		} while (!respuesta.equals("3"));
		return plantas;
	}

	private static ArrayList<Planta> menu_gestor(Empleado empleado, ArrayList<Planta> plantas, List<Planta> plantasBaja,
			ArrayList<Empleado> empleados, ArrayList<Empleado> empleadosBaja) {
		// TODO menú gestor
		String respuesta;
		do {
			System.out.println("\n═══════════");
			System.out.println("Menú gestor");
			System.out.println("═══════════");
			System.out.println("1. Gestionar plantas");
			System.out.println("2. Gestionar empleados");
			System.out.println("3. Estadísticas de los tickets");
			System.out.println("4. Guardar y salir");
			System.out.print("Opción: ");
			respuesta = read.next();
			read.nextLine();

			while (!respuesta.matches("[1-4]")) {
				System.out.print("Introduce un número del 1 al 4: ");
				respuesta = read.next();
				read.nextLine();
			}
			System.out.println("");
			plantasBaja = cargar_datos_plantas(new File("PLANTAS/plantasBaja.xml"));
			empleadosBaja = cargar_datos_empleados(new File("EMPLEADOS/BAJA/empleadosBaja.dat"));
			switch (respuesta) {
			case "1":
				plantas = menu_plantas(plantas, plantasBaja);
				break;
			case "2":
				empleados = menu_empleados(empleados, empleadosBaja);
				break;
			case "3":
				menu_tickets(plantas);
				break;
			case "4":
				break;
			default:
				System.out.println("Opción inválida, vulva a intentarlo.");
				break;
			}
		} while (!respuesta.equals("4"));
		if(guardar_empleados(new File("EMPLEADOS/empleados.dat"), empleados)==0)
			System.out.println("No se han guardado los empleados correctamente.");
		return plantas;

	}

	private static ArrayList<Planta> menu_plantas(ArrayList<Planta> plantas, List<Planta> plantasBaja) {
		String respuesta;
		do {
			System.out.println("\n------------");
			System.out.println("Menú plantas");
			System.out.println("------------");
			System.out.println("1. Añadir plantas");
			System.out.println("2. Dar de baja plantas");
			System.out.println("3. Reponer stock de plantas");
			System.out.println("4. Volver al menú");
			System.out.print("Opción: ");
			respuesta = read.next();
			read.nextLine();

			while (!respuesta.matches("[1-4]")) {
				System.out.print("Introduce un número del 1 al 4: ");
				respuesta = read.next();
				read.nextLine();
			}
			System.out.println("");
			plantasBaja = cargar_datos_plantas(new File("PLANTAS/plantasBaja.xml"));
			switch (respuesta) {
			case "1":
				plantas = nueva_planta(plantas);
				break;
			case "2":
				plantas = seleccion_planta_baja(plantas);
				break;
			case "3":
				plantas = seleccion_planta_reponer(plantasBaja, plantas);
				break;
			case "4":
				break;
			default:
				System.out.println("Opción inválida, vulva a intentarlo.");
				break;
			}
		} while (!respuesta.equals("4"));
		return plantas;
	}

	private static ArrayList<Empleado> menu_empleados(ArrayList<Empleado> empleados,
			ArrayList<Empleado> empleadosBaja) {
		String respuesta;
		do {
			System.out.println("\n--------------");
			System.out.println("Menú empleados");
			System.out.println("--------------");
			System.out.println("1. Añadir un empleado");
			System.out.println("2. Dar de baja a un empleado");
			System.out.println("3. Rescatar un empleado de baja");
			System.out.println("4. Volver al menú");
			System.out.print("Opción: ");
			respuesta = read.next();
			read.nextLine();

			while (!respuesta.matches("[1-4]")) {
				System.out.print("Introduce un número del 1 al 4: ");
				respuesta = read.next();
				read.nextLine();
			}
			System.out.println("");
			empleadosBaja = cargar_datos_empleados(new File("EMPLEADOS/BAJA/empleadosBaja.dat"));
			switch (respuesta) {
			case "1":
				empleados = pedir_datos_empleado_nuevo(empleados);
				break;
			case "2":
				empleados = dar_empleado_baja(empleados, empleadosBaja);
				break;
			case "3":
				empleados = dar_empleado_alta(empleados, empleadosBaja);
				break;
			case "4":
				break;
			default:
				System.out.println("Opción inválida, vulva a intentarlo.");
				break;
			}
		} while (!respuesta.equals("4"));
		return empleados;
	}

	private static void menu_tickets(ArrayList<Planta> plantas) {
		String respuesta;
		do {
			System.out.println("\n------------");
			System.out.println("Menú tickets");
			System.out.println("------------");
			System.out.println("1. Total recaudado");
			System.out.println("2. Plantas más vendidas");
			System.out.println("3. Volver al menú");
			System.out.print("Opción: ");
			respuesta = read.next();
			read.nextLine();

			while (!respuesta.matches("[1-3]")) {
				System.out.print("Introduce un número del 1 al 3: ");
				respuesta = read.next();
				read.nextLine();
			}
			System.out.println("");
			switch (respuesta) {
			case "1":
				total_recaudado_tickets();
				break;
			case "2":
				plantas_mas_vendidas(plantas);
				break;
			case "3":
				break;
			default:
				System.out.println("Opción inválida, vulva a intentarlo.");
				break;
			}
		} while (!respuesta.equals("3"));

	}

	private static ArrayList<Planta> mostrar_catalogo_plantas(ArrayList<Planta> plantas, Empleado empleado) {
		// TODO hacerlo un pco mas bonito con guiones y redirigir a la venta
		// directamente
		File listaPlantasFicheroDat = new File("PLANTAS/plantas.dat");
		for (Planta planta : plantas) {
			imprimir_planta(planta, listaPlantasFicheroDat);
			System.out.println("\n································\n");
		}
		/*
		 * float precio; int stock; int codigo; int puntero = 0; File
		 * listaPlantasFicheroDat = new File("PLANTAS/plantas.dat"); RandomAccessFile
		 * raf;
		 * 
		 * try { raf = new RandomAccessFile(listaPlantasFicheroDat, "r"); int i = 0;
		 * while (raf.getFilePointer() < raf.length() && i < plantas.size()) { if (i !=
		 * 0) { System.out.println("································\n"); } do {
		 * raf.seek(puntero * 12); codigo = raf.readInt(); //
		 * plantas.get(i).setCodigo(codigo); precio = plantas.get(i).getPrecio(codigo,
		 * listaPlantasFicheroDat); // precio = raf.readFloat(); //
		 * plantas.get(i).setPrecio(codigo, listaPlantasFicheroDat, precio); stock =
		 * plantas.get(i).getStock(codigo, listaPlantasFicheroDat); // stock =
		 * raf.readInt(); puntero++; } while (precio == 0 && stock == 0);
		 * System.out.println(plantas.get(i).toString()); i++; try { Thread.sleep(300);
		 * } catch (InterruptedException e) { e.printStackTrace(); } } raf.close(); }
		 * catch (FileNotFoundException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); }
		 */
		return venta_plantas(empleado, plantas);
	}

	private static ArrayList<Planta> venta_plantas(Empleado empleado, ArrayList<Planta> plantas) {
		// TODO vender
		boolean otraPlanta = true;
		List<Planta> cestaPlantas = new ArrayList<Planta>();
		List<Integer> cantidades = new ArrayList<Integer>();
		List<String> codigosPlantasVendidas = new ArrayList<String>();
		do {
			String codigo;
			System.out.print("Escribe el id de la planta que quieras vender (0 para cancelar): ");
			codigo = read.next();
			read.nextLine();
			while (!codigo.matches("[0-9]*")) {
				System.out.print("Introduce un número válido: ");
				codigo = read.next();
				read.nextLine();
			}
			if (codigo.equals("0")) {
				// System.out.println("Proceso cancelado.");
				otraPlanta = false;
			} else {
				int posicion = posicion_planta_por_codigo(Integer.valueOf(codigo), plantas);
				if (posicion != -1) {
					codigosPlantasVendidas.add(codigo);
					if (Collections.frequency(codigosPlantasVendidas, codigo) >= 2) {
						System.out.println("No puedes volver a añadir la misma planta.");
					} else {
						Planta planta = plantas.get(posicion);
						cestaPlantas.add(planta);
						cantidades.add(vender_planta(empleado, planta));
					}
					System.out.print("¿Quieres añadir otra planta a la venta? (si o no): ");
					String seguir = read.next();
					read.nextLine();
					if (seguir.equalsIgnoreCase("si")) {
						otraPlanta = true;
					} else {
						otraPlanta = false;
					}
				} else {
					System.out.println("Planta no encontrada");
				}
			}
		} while (otraPlanta);
		if (cestaPlantas.size() != 0 && cantidades.size() != 0 && cestaPlantas.size() == cantidades.size()) {
			System.out.println("");
			Ticket venta = new Ticket(cantidad_tickets()+1);
			ArrayList<String> contenidoTicket = venta.escribirTicket(cestaPlantas, cantidades, empleado);
			if (contenidoTicket != null) {
				for (String linea : contenidoTicket) {
					System.out.println(linea);
				}
				System.out.print("Escribe 'Aceptar' para confirmar la venta y producir el ticket: ");
				String acepta = read.nextLine();
				if (acepta.equals("Aceptar")) {
					if (venta.crearTicket(contenidoTicket)) {
						plantas = restar_cantidades(cestaPlantas, cantidades, plantas);
					}
				} else {
					System.out.println("Venta cancelada.");
				}
			} else {
				System.out.println("No es posible vender esta planta.");
			}
		} else {
			System.out.println("Proceso de venta cancelado.");
		}
		return plantas;
	}

	private static ArrayList<Planta> devolver_plantas(ArrayList<Planta> plantas) {
		System.out.println("Esta es toda la lista de tickets");
		for (int i = 1; i <= cantidad_tickets(); i++) {
			System.out.println("////////////////////////////////////////////////////");
			buscar_tiket(String.valueOf(i));
		}
		String numTicket;
		System.out.print("Escribe el número de ticket que quieras devolver (0 para cancelar): ");
		numTicket = read.next();
		read.nextLine();
		while (!numTicket.matches("[0-9]*")) {
			System.out.print("Introduce un número válido: ");
			numTicket = read.next();
			read.nextLine();
		}
		if (!numTicket.equals("0")) {
			System.out.println("Esta es la información del ticket que vas a cancelar");
			Ticket ticketDevolver = new Ticket(Integer.valueOf(numTicket));
			System.out.println("\n" + ticketDevolver.devolverContenido());
			plantas = ticketDevolver.marcarDevuelto(plantas);
		} else {
			System.out.println("Proceso de devolución cancelado.");
		}
		return plantas;
	}

	private static void buscar_tiket(String numBuscar) {
		if (Integer.valueOf(numBuscar) <= cantidad_tickets()) {
			Ticket ticketBuscar = new Ticket(Integer.valueOf(numBuscar));
			System.out.println("\n" + ticketBuscar.devolverContenido());
		} else {
			System.out.println("No se ha encontrado el ticket pedido.");
		}
	}

	/*
	 * private static void guardar_datos(List<Planta> plantas, ArrayList<Empleado>
	 * empleados) { // TODO Auto-generated method stub
	 * 
	 * }
	 */
	private static ArrayList<Planta> nueva_planta(ArrayList<Planta> plantas) {
		System.out.println("Datos de la nueva planta");
		int maxId = plantas.get(0).getCodigo();
		for (int i = 0; i < plantas.size(); i++) {
			if (plantas.get(i).getCodigo() > maxId) {
				maxId = plantas.get(i).getCodigo();
			}
		}
		maxId++;
		System.out.println("Código generado automáticamente: " + maxId);
		System.out.print("Nombre: ");
		String nombre = read.nextLine();
		System.out.print("Foto: ");
		String foto = read.nextLine();
		System.out.print("Descripcion: ");
		String descripcion = read.nextLine();
		boolean repetir1 = true;
		System.out.print("Precio: ");
		String precio = read.next();
		read.nextLine();
		float preciof = 0;
		try {
			preciof = Float.valueOf(precio);
			repetir1 = false;
		} catch (Exception e) {
			repetir1 = true;
		}
		while (repetir1) {
			System.out.print("Escribe un precio válido: ");
			precio = read.next();
			read.nextLine();
			try {
				preciof = Float.valueOf(precio);
				repetir1 = false;
			} catch (Exception e) {
				repetir1 = true;
			}
		}
		boolean repetir2 = true;
		System.out.print("Cantidad: ");
		String cantidad = read.next();
		read.nextLine();
		int cantidadi = 0;
		try {
			cantidadi = Integer.valueOf(cantidad);
			repetir2 = false;
		} catch (Exception e) {
			repetir2 = true;
		}
		while (repetir2) {
			System.out.print("Escribe una cantidad válida: ");
			cantidad = read.next();
			read.nextLine();
			try {
				cantidadi = Integer.valueOf(cantidad);
				repetir2 = false;
			} catch (Exception e) {
				repetir2 = true;
			}
		}
		File ficheroEscribirDat = new File("PLANTAS/plantas.dat");
		try {
			RandomAccessFile raf = new RandomAccessFile(ficheroEscribirDat, "rw");
			raf.seek(raf.length());
			raf.writeInt(maxId);
			raf.writeFloat(preciof);
			raf.writeInt(cantidadi);
			raf.close();
		} catch (IOException e) {
			System.out.println("Ha ocurrido un error en la escritura del fichero 'plantas.dat'.");
			e.printStackTrace();
			return plantas;
		}
		plantas.add(new Planta(maxId, nombre, foto, descripcion));
		return plantas;
	}

	private static ArrayList<Planta> seleccion_planta_baja(ArrayList<Planta> plantas) {
		System.out.println("Esta es toda la lista de plantas");
		File listaPlantasFicheroDat = new File("PLANTAS/plantas.dat");
		for (Planta planta : plantas) {
			imprimir_planta(planta, listaPlantasFicheroDat);
			System.out.println("\n································\n");
		}
		System.out.print("Escribe el código de la planta que quieras dar de baja: ");
		String codigoBuscar = read.next();
		read.nextLine();
		int posicion = posicion_planta_por_codigo(Integer.valueOf(codigoBuscar), plantas);
		while (!codigoBuscar.matches("[0-9]*") || posicion == -1) {
			System.out.print("Introduce un código válido: ");
			codigoBuscar = read.next();
			read.nextLine();
			posicion = posicion_planta_por_codigo(Integer.valueOf(codigoBuscar), plantas);
		}
		if (dar_de_baja(plantas.get(posicion))) {
			plantas.remove(plantas.get(posicion));
		} else {
			System.out.println("No se ha podido dar de baja la planta con código " + plantas.get(posicion).getCodigo());
		}
		return plantas;
	}

	private static ArrayList<Planta> seleccion_planta_reponer(List<Planta> plantasBaja, ArrayList<Planta> plantas) {
		System.out.println("Lista de plantas dadas de baja");
		File listaPlantasBajaFicheroDat = new File("PLANTAS/plantasbaja.dat");
		if (plantasBaja == null) {
			System.out.println("No se han encontrado plantas de baja para reponer.");
			return plantas;
		}
		for (Planta planta : plantasBaja) {
			imprimir_planta(planta, listaPlantasBajaFicheroDat);
			System.out.println("\n································\n");
		}
		// TODO esto es para dar de baja, hay que dar de alta las plantas.
		System.out.print("Escribe el código de la planta que quieras dar de baja: ");
		String codigoBuscar = read.next();
		read.nextLine();
		// TODO arreglar el bucle while por lo de Integer.valueOf
		int posicion = posicion_planta_por_codigo(Integer.valueOf(codigoBuscar), plantas);
		while (!codigoBuscar.matches("[0-9]*") || posicion == -1) {
			System.out.print("Introduce un código válido: ");
			codigoBuscar = read.next();
			read.nextLine();
			posicion = posicion_planta_por_codigo(Integer.valueOf(codigoBuscar), plantas);
		}
		if (dar_de_baja(plantas.get(posicion))) {
			plantas.remove(plantas.get(posicion));
		} else {
			System.out.println("No se ha podido dar de baja la planta con código " + plantas.get(posicion).getCodigo());
		}
		return plantas;
	}

	private static ArrayList<Empleado> pedir_datos_empleado_nuevo(ArrayList<Empleado> empleados) {
		System.out.println("Datos del nuevo empleado");
		if (empleados==null) {
			empleados = new ArrayList<Empleado>();
		}
		int nuevoCodigo = generar_codigo_empleado(empleados);
		System.out.println("Código generado automáticamente: " + nuevoCodigo);
		// No puedo permitir cualquier cadena de caracteres
		System.out.print("Nombre: ");
		String nombre = read.nextLine();
		while (!nombre.matches("([A-Z][a-z]*) ?([A-Z][a-z]*)?")) {
			System.out.print("Introduce un nombre válido: ");
			nombre = read.nextLine();
		}
		// No puedo permitir cualquier cadena de caracteres
		System.out.print("Contraseña: ");
		String passwd = read.nextLine();
		while (!passwd.matches("[A-Za-z0-9@#\\.\\-\\_\\+\\$%&]{5,7}")) {
			System.out.print("Introduce una contraseña válida: ");
			passwd = read.nextLine();
		}
		boolean repetir = true;
		System.out.print("Cargo: ");
		String cargo = read.nextLine();
		if (cargo.equals("Vendedor") || cargo.equals("Gestor")) {
			repetir = false;
		}
		while (repetir) {
			System.out.print("Escribe un cargo disponible (Vendedor o Gestor): ");
			cargo = read.nextLine();
			if (cargo.equals("Vendedor") || cargo.equals("Gestor")) {
				repetir = false;
			}
		}
		File listaEmpleadosFichero = new File("EMPLEADOS/empleados.dat");
		empleados.add(new Empleado(nuevoCodigo, nombre, passwd, cargo));
		//guardar_empleados(listaEmpleadosFichero, empleados);
		return empleados;
	}

	private static ArrayList<Empleado> dar_empleado_baja(ArrayList<Empleado> empleados,
			ArrayList<Empleado> empleadosBaja) {
		if (empleados != null) {
					System.out.println("Esta es la lista de empleados");
			for (Empleado empleado : empleados) {
				System.out.println(empleado.toString());
				System.out.println("\n································\n");
			}
			System.out.print("Introduce la identificación del empleado que quieras dar de baja (0000 para cancelar): ");
			String idBuscar = read.next();
			read.nextLine();
			while (!idBuscar.matches("[0-9]{4}")) {
				System.out.print("Introduce una identificación válida: ");
				idBuscar = read.next();
				read.nextLine();
			}
			int posicion = posicion_empleado_por_codigo(Integer.valueOf(idBuscar), empleados);
			if(posicion != -1) {
				Empleado empleadoDarBaja = empleados.get(posicion);
				if (empleadosBaja == null) {
					empleadosBaja = new ArrayList<Empleado>();
				}
				empleadosBaja.add(empleadoDarBaja);
				if (guardar_empleados(new File("EMPLEADOS/BAJA/empleadosBaja.dat"), empleadosBaja) == 1) {
					empleados.remove(empleadoDarBaja);
				} else {
					System.out.println("El empleado "+empleadoDarBaja.getNombre()+" no se ha podido dar de baja.");
				}
			} else {
				System.out.println("No se ha encontrado el código");
			}
		} else {
			System.out.println("No se han encontrado empleados para dar de baja.");
		}
		return empleados;
	}

	private static ArrayList<Empleado> dar_empleado_alta(ArrayList<Empleado> empleados,
			ArrayList<Empleado> empleadosBaja) {
		if (empleadosBaja != null) {
			System.out.println("Esta es la lista de empleados dados de baja");
			for (Empleado empleado : empleadosBaja) {
				System.out.println(empleado.toString());
				System.out.println("\n································\n");
			}
			System.out.print("Introduce la identificación del empleado que quieras dar de alta (0000 para cancelar): ");
			String idBuscar = read.next();
			read.nextLine();
			while (!idBuscar.matches("[0-9]{4}")) {
				System.out.print("Introduce una identificación válida: ");
				idBuscar = read.next();
				read.nextLine();
			}
			int posicion = posicion_empleado_por_codigo(Integer.valueOf(idBuscar), empleadosBaja);
			if(posicion != -1) {
				Empleado empleadoDarAlta = empleadosBaja.get(posicion);
				if (empleados == null) {
					empleados = new ArrayList<Empleado>();
				}
				empleadosBaja.remove(empleadoDarAlta);
				if (guardar_empleados(new File("EMPLEADOS/BAJA/empleadosBaja.dat"), empleadosBaja) == 1) {
					empleados.add(empleadoDarAlta);
				} else {
					System.out.println("El empleado "+empleadoDarAlta.getNombre()+" no se ha podido eliminar de los empleados de baja.");
				}
			} else {
				System.out.println("No se ha encontrado el código");
			}
		} else {
			System.out.println("No se han encontrado empleados de baja para dar de alta");
		}
		return empleados;
	}

	private static void total_recaudado_tickets() {
		float totalRecaudado = 0f;
		for (int i = 1; i <= cantidad_tickets(); i++) {
			Ticket datosTicket = new Ticket(i);
			String paso1 = datosTicket.devolverContenido();
			String contenido = "Ticket "+i+" no encontrado.";
			if (!paso1.equals(contenido)) {
				String [] paso2 = paso1.split("Total: ");
				String [] paso3 = paso2[1].split("€");
				totalRecaudado += Float.valueOf(paso3[0]);
			}
		}
		System.out.printf("El total recaudado según todos los tickets es de %.2f€\n", totalRecaudado);
	}

	private static void plantas_mas_vendidas(ArrayList<Planta> plantas) {
		// TODO Sacar listas de los codigos y las cantidades
		ArrayList<Integer> codigosLeidos = new ArrayList<Integer>();
		ArrayList<Integer> cantidadesLeidas = new ArrayList<Integer>();
		for (int i = 1; i <= cantidad_tickets(); i++) {
			Ticket infoTicket = new Ticket(i);
			String contenido = infoTicket.devolverContenido();
			if (!contenido.equals("Ticket "+i+" no encontrado.")) {
				String paso1 [] = contenido.split("unitario");
				String paso2 [] = paso1[1].split("·");
				String paso3 [] = paso2[0].split("€");
				for (int j = 0; j < paso3.length-1; j++) {
					String paso4 [] = paso3[j].split(" ");
					codigosLeidos.add(Integer.valueOf(paso4[0]));
					cantidadesLeidas.add(Integer.valueOf(paso4[19]));
				}
			}
		}
		// Sacar listas de los codigos sin repetirse y sus cantidades sumadas
		// TODO Ordenar los codigos por las cantidades de mayor a menor
		ArrayList<Integer> codigosUnicos = new ArrayList<Integer>();
		ArrayList<Integer> cantidadesSumadas = new ArrayList<Integer>();
		for (int i = 0; i < plantas.size(); i++) {
			Integer cantidadTotalPlanta = 0;
			Integer codigoVueltaPlanta = 0;
			for (int j = 0; j < codigosLeidos.size(); j++) {
				if (plantas.get(i).getCodigo() == codigosLeidos.get(j)) {
					cantidadTotalPlanta += cantidadesLeidas.get(j);
					codigoVueltaPlanta = codigosLeidos.get(j);
				}
			}
			codigosUnicos.add(codigoVueltaPlanta);
			cantidadesSumadas.add(cantidadTotalPlanta);
		}
		ArrayList<Planta> plantasOrdenadas = new ArrayList<Planta>();
		ArrayList<Integer> cantidadesOredenadas = new ArrayList<Integer>();
		while (cantidadesSumadas.size() <= 0) {
			int posicionCantidadMax = 0;
			int cantidadMax = cantidadesSumadas.get(0);
			for (int i = 1; i < cantidadesSumadas.size(); i++) {
				if (cantidadMax < cantidadesSumadas.get(i)) {
					posicionCantidadMax = i;
					cantidadMax = cantidadesSumadas.get(i);
				}
			}
			plantasOrdenadas.add(plantas.get(posicion_planta_por_codigo(codigosUnicos.get(posicionCantidadMax), plantas)));
			cantidadesOredenadas.add(cantidadesSumadas.get(posicionCantidadMax));
			codigosUnicos.remove(codigosUnicos.get(posicionCantidadMax));
			cantidadesSumadas.remove(cantidadesSumadas.get(posicionCantidadMax));
		}
		// TODO Imprimir el nombre de la planta segun el codio junto con su cantidad total de ventas.
		for (int i = 0; i < plantasOrdenadas.size(); i++) {
			System.out.println(plantasOrdenadas.get(i)+": "+cantidadesOredenadas.get(i));
		}
	}

	private static int guardar_plantas(ArrayList<Planta> plantas, File ficheroEscribirXml) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();

			Element root = doc.createElement("plantas");
			doc.appendChild(root);
			for (Planta añadeplanta : plantas) {
				Element planta = doc.createElement("planta");
				root.appendChild(planta);
				Element codigo = doc.createElement("codigo");
				codigo.appendChild(doc.createTextNode(añadeplanta.getCodigo() + ""));
				planta.appendChild(codigo);
				Element nombre = doc.createElement("nombre");
				nombre.appendChild(doc.createTextNode(añadeplanta.getNombre()));
				planta.appendChild(nombre);
				Element foto = doc.createElement("foto");
				foto.appendChild(doc.createTextNode(añadeplanta.getFoto()));
				planta.appendChild(foto);
				Element descripcion = doc.createElement("descripcion");
				descripcion.appendChild(doc.createTextNode(añadeplanta.getDescripcion()));
				planta.appendChild(descripcion);
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			// transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
			// transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(ficheroEscribirXml);
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			System.out.println("Ha ocurrido un error en la escritura del fichero 'plantas.xml'.");
			e.printStackTrace();
			return 0;
		} catch (TransformerException e) {
			System.out.println("Ha ocurrido un error en la escritura del fichero 'plantas.xml'.");
			e.printStackTrace();
			return 0;
		} catch (ParserConfigurationException e) {
			System.out.println("Ha ocurrido un error en la escritura del fichero 'plantas.xml'.");
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	private static int guardar_empleados(File listaEmpleadosFichero, ArrayList<Empleado> listaEmpleados) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(listaEmpleadosFichero.getPath()));
			out.writeObject(listaEmpleados);
			out.close();
			//System.out.println("Empleados añadidos.");
		} catch (FileNotFoundException e) {
			System.out.println(
					"Ha ocurrido un error en la escritura del fichero '" + listaEmpleadosFichero.getPath() + "'.");
			e.printStackTrace();
			return 0;
		} catch (IOException e) {
			System.out.println(
					"Ha ocurrido un error en la escritura del fichero '" + listaEmpleadosFichero.getPath() + "'.");
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	private static void imprimir_planta(Planta planta, File listaPlantasFicheroDat) {
		RandomAccessFile raf;
		float precio = 0;
		int stock = 0;
		try {
			raf = new RandomAccessFile(listaPlantasFicheroDat, "r");
			raf.seek((planta.getCodigo() - 1) * 12);
			int codigo = raf.readInt();
			// plantas.get(i).setCodigo(codigo);
			// float precio = planta.getPrecio(planta.getCodigo(), listaPlantasFicheroDat);
			// raf.seek(((codigo-1)*12)+4);
			precio = raf.readFloat();
			// plantas.get(i).setPrecio(codigo, listaPlantasFicheroDat, precio);
			// int stock = planta.getStock(planta.getCodigo(), listaPlantasFicheroDat);
			stock = raf.readInt();
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println(planta.toString());
		System.out.println("\tPrecio: " + precio + "€\n\tStock: " + stock);
	}

	private static Integer vender_planta(Empleado empleado, Planta planta) {
		// TODO Auto-generated method stub
		System.out.println("Esta es la información de la planta seleccionada");
		File listaPlantasFicheroDat = new File("PLANTAS/plantas.dat");
		imprimir_planta(planta, listaPlantasFicheroDat);
		String cantidad;
		System.out.print("¿Cuántas plantas quieres vender? ");
		cantidad = read.next();
		read.nextLine();
		RandomAccessFile raf;
		int stockPlanta = 0;
		try {
			raf = new RandomAccessFile(listaPlantasFicheroDat, "r");
			raf.seek(((planta.getCodigo() - 1) * 12) + 8);
			stockPlanta = raf.readInt();
		} catch (Exception e) {
			stockPlanta = 100;
		}
		while (!cantidad.matches("[0-9]*") || stockPlanta < Integer.valueOf(cantidad)) {
			System.out.print("Introduce otro número: ");
			cantidad = read.next();
			read.nextLine();
		}
		return Integer.valueOf(cantidad);

	}

	private static ArrayList<Planta> restar_cantidades(List<Planta> plantasVendidas, List<Integer> cantidades,
			ArrayList<Planta> plantas) {
		int codigo = 0;
		int cantidadResta = 0;
		int cantidadAnterior = 0;
		int cantidadFinal = 0;
		int puntero = 0;
		File listaPlantasFicheroDat = new File("PLANTAS/plantas.dat");
		RandomAccessFile raf;
		for (int i = 0; i < plantasVendidas.size(); i++) {
			codigo = plantasVendidas.get(i).getCodigo();
			cantidadResta = cantidades.get(i);
			puntero = ((codigo - 1) * 12) + 8;
			try {
				raf = new RandomAccessFile(listaPlantasFicheroDat, "r");
				raf.seek(puntero);
				cantidadAnterior = raf.readInt();
				cantidadFinal = cantidadAnterior - cantidadResta;
				raf.close();
			} catch (Exception e) {
				cantidadFinal = 0;
			}
			if (cantidadFinal == 0) {
				System.out.println("Se han comprado todas las unidades de " + plantasVendidas.get(i).getNombre()
						+ ", se dará de baja automáticamente.");
				boolean eliminarPlanta = dar_de_baja(plantasVendidas.get(i));
				if (eliminarPlanta) {
					plantas.remove(plantasVendidas.get(i));
				}
			}
			try {
				raf = new RandomAccessFile(listaPlantasFicheroDat, "rw");
				raf.seek(puntero);
				raf.writeInt(cantidadFinal);
				raf.close();
			} catch (IOException e) {
				System.out.println("Ha ocurrido un error en la escritura del fichero 'plantas.dat'.");
				e.printStackTrace();
			}
		}
		return plantas;
	}

	private static boolean dar_de_baja(Planta planta) {
		File listaPlantasFicheroDat = new File("PLANTAS/plantas.dat");
		File listaPlantasBajaFicheroDat = new File("PLANTAS/plantasbaja.dat");
		RandomAccessFile raf;
		float precioPlanta = 0f;
		int puntero = ((planta.getCodigo() - 1) * 12) + 4;
		try {
			raf = new RandomAccessFile(listaPlantasFicheroDat, "r");
			raf.seek(puntero);
			precioPlanta = raf.readFloat();
			try {
				raf = new RandomAccessFile(listaPlantasBajaFicheroDat, "rw");
				raf.seek(raf.length());
				// System.out.println(raf.readInt());
				/*
				 * while (raf.getFilePointer() < raf.length()) { raf.readInt(); }
				 */
				raf.writeInt(planta.getCodigo());
				raf.writeFloat(precioPlanta);
				raf.writeInt(0);
				raf.close();
				ArrayList<Planta> plantasBaja = cargar_datos_plantas(new File("PLANTAS/plantasBaja.xml"));
				if (plantasBaja == null) {
					plantasBaja = new ArrayList<Planta>();
				}
				plantasBaja.add(planta);
				guardar_plantas(plantasBaja, new File("PLANTAS/plantasBaja.xml"));
				raf = new RandomAccessFile(listaPlantasFicheroDat, "rw");
				raf.seek(puntero);
				raf.writeFloat(0f);
				raf.close();
			} catch (IOException e) {
				System.out.println("Ha ocurrido un error en la escritura del fichero 'plantas.dat'.");
				e.printStackTrace();
				return false;
			}
		} catch (Exception e) {
			System.out.println("Ha ocurrido un error en la lectura del fichero 'plantas.dat'.");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private static int posicion_planta_por_codigo(Integer codigo, List<Planta> plantas) {
		for (int i = 0; i < plantas.size(); i++) {
			if (plantas.get(i).getCodigo() == codigo) {
				return i;
			}
		}
		return -1;
	}

	private static int posicion_empleado_por_codigo(Integer id, ArrayList<Empleado> empleados) {
		for (int i = 0; i < empleados.size(); i++) {
			if (empleados.get(i).getId() == id) {
				return i;
			}
		}
		return -1;
	}

	private static int generar_codigo_empleado(ArrayList<Empleado> empleados) {
		int posibleCodigo = 0;
		boolean seguir = true;
		do {
			seguir = false;
			posibleCodigo = ThreadLocalRandom.current().nextInt(1, 10000);
			for (Empleado empleado : empleados) {
				if (empleado.getId() == posibleCodigo) {
					seguir = true;
				}
			}
		} while (seguir);
		return posibleCodigo;
	}

	private static int cantidad_tickets() {
		int num = 0;
		Path tickets = Path.of("TICKETS");
		Path devoluciones = Path.of("DEVOLUCIONES");
		try {
			Stream<Path> flujo = Files.list(tickets);
			Stream<Path> flujo2 = Files.list(devoluciones);
			num = (int) (flujo.count() + flujo2.count());
			// System.out.println(num);
			// flujo.forEach(archivo->System.out.println(archivo.getFileName()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return num;
	}

	private static ArrayList<Planta> cargar_datos_plantas(File listaPlantasFichero) {
		ArrayList<Planta> listaPlantas = new ArrayList<Planta>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder docB = dbf.newDocumentBuilder();
			Document doc = docB.parse(listaPlantasFichero.getPath());
			doc.getDocumentElement().normalize();
			NodeList lista = doc.getElementsByTagName("planta");

			for (int i = 0; i < lista.getLength(); i++) {
				Node nodo = lista.item(i);
				if (nodo.getNodeType() == Node.ELEMENT_NODE) {
					Planta plantaTmp;
					Element planta = (Element) nodo;
					int codigo = Integer.valueOf(planta.getElementsByTagName("codigo").item(0).getTextContent());
					String nombre = planta.getElementsByTagName("nombre").item(0).getTextContent();
					String foto = planta.getElementsByTagName("foto").item(0).getTextContent();
					String descripcion = planta.getElementsByTagName("descripcion").item(0).getTextContent();

					plantaTmp = new Planta(codigo, nombre, foto, descripcion);
					listaPlantas.add(plantaTmp);
				}
			}
		} catch (IOException e) {
			System.out
					.println("Ha ocurrido un error en la lectura del fichero '" + listaPlantasFichero.getPath() + "'.");
			e.printStackTrace();
			return null;
		} catch (ParserConfigurationException e) {
			System.out
					.println("Ha ocurrido un error en la lectura del fichero '" + listaPlantasFichero.getPath() + "'.");
			e.printStackTrace();
			return null;
		} catch (SAXException e) {
			System.out
					.println("Ha ocurrido un error en la lectura del fichero '" + listaPlantasFichero.getPath() + "'.");
			e.printStackTrace();
			return null;
		}
		return listaPlantas;
	}

	private static ArrayList<Empleado> cargar_datos_empleados(File listaEmpleadosFichero) {
		ArrayList<Empleado> listaEmpleados = new ArrayList<Empleado>();
		ObjectInputStream ois;
		FileInputStream fis;
		try {
			fis = new FileInputStream(listaEmpleadosFichero.getPath());
			ois = new ObjectInputStream(fis);
			listaEmpleados = (ArrayList<Empleado>) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			System.out.println(
					"Ha ocurrido un error en la lectura del fichero '" + listaEmpleadosFichero.getPath() + "'.");
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			System.out.println(
					"Ha ocurrido un error en la lectura del fichero '" + listaEmpleadosFichero.getPath() + "'.");
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			System.out.println(
					"Ha ocurrido un error en la lectura del fichero '" + listaEmpleadosFichero.getPath() + "'.");
			// System.out.println("Comprueba que el archivo no esté vacío");
			e.printStackTrace();
			return null;
		}
		return listaEmpleados;
	}

	private static void crear_plantas_por_defecto() {
		File ficheroEscribirXml = new File("PLANTAS/plantas.xml");
		File ficheroEscribirDat = new File("PLANTAS/plantas.dat");
		ArrayList<Planta> plantas = new ArrayList<Planta>();
		// TODO tengo que hacer que escriba bien los precios y el stock
		// plantaEjemplo1.setPrecio(1, ficheroEscribirDat, 30.5f);
		// plantaEjemplo1.setStock(1, ficheroEscribirDat, 4);
		Planta plantaEjemplo1 = new Planta(1, "Cactus", "cactus.jpg", "Planta suculenta del desierto");
		Planta plantaEjemplo2 = new Planta(2, "Geranio", "geranio.jpg", "Flor común de jardín con colores vivos");
		Planta plantaEjemplo3 = new Planta(3, "Helecho", "helecho.jpg", "Planta de sombra con hojas verdes largas");
		Planta plantaEjemplo4 = new Planta(4, "Rosa", "rosa.jpg", "Flor ornamental con espinas");
		Planta plantaEjemplo5 = new Planta(5, "Lavanda", "lavanda.jpg", "Planta aromática de color violeta");
		Planta plantaEjemplo6 = new Planta(6, "Aloe Vera", "aloe_vera.jpg", "Planta medicinal con hojas carnosas");
		Planta plantaEjemplo7 = new Planta(7, "Menta", "menta.jpg", "Planta aromática usada en infusiones");
		Planta plantaEjemplo8 = new Planta(8, "Bambú", "bambu.jpg",
				"Planta de tallo alto y hueco, de rápido crecimiento");
		Planta plantaEjemplo9 = new Planta(9, "Orquídea", "orquidea.jpg", "Flor exótica de gran valor ornamental");
		Planta plantaEjemplo10 = new Planta(10, "Hortensia", "hortensia.jpg", "Planta de flores grandes y coloridas");
		Planta plantaEjemplo11 = new Planta(11, "Palmera", "palmera.jpg",
				"Árbol tropical con hojas largas en forma de abanico");
		Planta plantaEjemplo12 = new Planta(12, "Clavel", "clavel.jpg", "Flor de colores vivos y aroma agradable");
		Planta plantaEjemplo13 = new Planta(13, "Jacinto", "jacinto.jpg", "Planta bulbosa de flores perfumadas");
		Planta plantaEjemplo14 = new Planta(14, "Petunia", "petunia.jpg", "Flor de jardín muy colorida");
		Planta plantaEjemplo15 = new Planta(15, "Begonia", "begonia.jpg", "Planta ornamental con hojas decorativas");
		Planta plantaEjemplo16 = new Planta(16, "Poto", "poto.jpg", "Planta colgante de interior muy resistente");
		Planta plantaEjemplo17 = new Planta(17, "Crisantemo", "crisantemo.jpg",
				"Flor típica de otoño en muchos colores");
		Planta plantaEjemplo18 = new Planta(18, "Romero", "romero.jpg",
				"Planta aromática usada en la cocina mediterránea");
		Planta plantaEjemplo19 = new Planta(19, "Ficus", "ficus.jpg", "Árbol o arbusto de interior muy popular");
		Planta plantaEjemplo20 = new Planta(20, "Jazmín", "jazmin.jpg", "Planta trepadora de flores fragantes");
		plantas.add(plantaEjemplo1);
		plantas.add(plantaEjemplo2);
		plantas.add(plantaEjemplo3);
		plantas.add(plantaEjemplo4);
		plantas.add(plantaEjemplo5);
		plantas.add(plantaEjemplo6);
		plantas.add(plantaEjemplo7);
		plantas.add(plantaEjemplo8);
		plantas.add(plantaEjemplo9);
		plantas.add(plantaEjemplo10);
		plantas.add(plantaEjemplo11);
		plantas.add(plantaEjemplo12);
		plantas.add(plantaEjemplo13);
		plantas.add(plantaEjemplo14);
		plantas.add(plantaEjemplo15);
		plantas.add(plantaEjemplo16);
		plantas.add(plantaEjemplo17);
		plantas.add(plantaEjemplo18);
		plantas.add(plantaEjemplo19);
		plantas.add(plantaEjemplo20);
		int flag = 0;
		flag += guardar_plantas(plantas, ficheroEscribirXml);

		try {
			RandomAccessFile raf = new RandomAccessFile(ficheroEscribirDat, "rw");
			raf.seek(0);
			for (int i = 1; i <= plantas.size(); i++) {
				raf.writeInt(i);
				float numero = numeroAleatorioPrecio();
				raf.writeFloat(numero);
				int numero1 = numeroAleatorioStock();
				raf.writeInt(numero1);
			}
			/*
			 * raf.writeInt(plantaEjemplo.getCodigo()); // TODO esto esta mal porque lo
			 * escribo con el .setPrecio y .setStock
			 * raf.writeFloat(plantaEjemplo.getPrecio(1, ficheroEscribirDat));
			 * raf.writeInt(plantaEjemplo.getStock(1, ficheroEscribirDat)); //
			 * raf.getFilePointer() = 12
			 */
			raf.close();
			flag++;
		} catch (IOException e) {
			System.out.println("Ha ocurrido un error en la escritura del fichero 'plantas.dat'.");
			e.printStackTrace();
		}
		if (flag == 2) {
			System.out.println("Plantas añadidas.");
		}
	}

	private static void crear_empleados_por_defecto() {
		File listaEmpleadosFichero = new File("EMPLEADOS/empleados.dat");
		ArrayList<Empleado> listaEmpleados = new ArrayList<Empleado>();
		listaEmpleados.add(new Empleado(1452, "Teresa", "asb123", "Vendedor"));
		listaEmpleados.add(new Empleado(156, "Miguel Ángel", "123qwes", "Gestor"));
		listaEmpleados.add(new Empleado(7532, "Natalia", "xs21qw4", "Gestor"));
		if(guardar_empleados(listaEmpleadosFichero, listaEmpleados)==1) 
			System.out.println("Empleados añadidos correctamente.");
		// mi usuario: 6812 456jkl
	}

	private static float numeroAleatorioPrecio() {

		float numero = ThreadLocalRandom.current().nextFloat(10, 50);
		return Math.round(numero * 100f) / 100f;

	}

	private static int numeroAleatorioStock() {
		return ThreadLocalRandom.current().nextInt(1, 100);
	}

}
