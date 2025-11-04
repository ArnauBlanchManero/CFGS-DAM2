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
import java.util.List;
import java.util.Scanner;

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
		System.out.println("BIENBENIDO AL GESTOR DE LA TIENDA DE PLANTAS");
		System.out.println("============================================");

		List<Planta> plantas = new ArrayList<Planta>();
		List<Empleado> empleados = new ArrayList<Empleado>();
		if (crear_jerarquía_ficheros()) {
			empleados = cargar_datos_empleados();
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
					plantas = cargar_datos_plantas();
					if (plantas != null) {
						// TODO funciones menus empleado
						if (empleado.getCargo().equals("Vendedor")) {
							menu_vendedor(empleado, plantas);
						} else if (empleado.getCargo().equals("Gestor")) {
							menu_gestor(empleado, plantas, empleados);
						} else {
							System.out.println("No se ha encontrado el cargo del empleado.");
						}
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
	}

	private static void crear_plantas_por_defecto() {
		File ficheroEscribirXml = new File("PLANTAS/plantas.xml");
		File ficheroEscribirDat = new File("PLANTAS/plantas.dat");
		Planta plantaEjemplo = new Planta(1, "Cactus", "cactus.jpg", "Ninguna", (float) 30.5, 4);
		int flag = 0;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();

			Element root = doc.createElement("plantas");
			doc.appendChild(root);
			Element planta = doc.createElement("planta");
			root.appendChild(planta);
			Element codigo = doc.createElement("codigo");
			codigo.appendChild(doc.createTextNode(plantaEjemplo.getCodigo() + ""));
			planta.appendChild(codigo);
			Element nombre = doc.createElement("nombre");
			nombre.appendChild(doc.createTextNode(plantaEjemplo.getNombre()));
			planta.appendChild(nombre);
			Element foto = doc.createElement("foto");
			foto.appendChild(doc.createTextNode(plantaEjemplo.getFoto()));
			planta.appendChild(foto);
			Element descripcion = doc.createElement("descripcion");
			descripcion.appendChild(doc.createTextNode(plantaEjemplo.getDescripcion()));
			planta.appendChild(descripcion);

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

			flag++;
		} catch (TransformerConfigurationException e) {
			System.out.println("Ha ocurrido un error en la escritura del fichero 'plantas.xml'.");
			e.printStackTrace();
		} catch (TransformerException e) {
			System.out.println("Ha ocurrido un error en la escritura del fichero 'plantas.xml'.");
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			System.out.println("Ha ocurrido un error en la escritura del fichero 'plantas.xml'.");
			e.printStackTrace();
		}
		try {
			RandomAccessFile raf = new RandomAccessFile(ficheroEscribirDat, "rw");
			raf.writeInt(plantaEjemplo.getCodigo());
			raf.writeFloat(plantaEjemplo.getPrecio());
			raf.writeInt(plantaEjemplo.getStock());
			// raf.getFilePointer() = 12

			raf.close();
			flag++;
		} catch (IOException e) {
			System.out.println("Ha ocurrido un error en la escritura del fichero 'plantas.dat'.");
			e.printStackTrace();
		}
		if (flag == 2) {
			System.out.println("Planta añadida.");
		}
	}

	private static void guardar_datos(List<Planta> plantas, List<Empleado> empleados) {
		// TODO Auto-generated method stub

	}

	private static void menu_gestor(Empleado empleado, List<Planta> plantas, List<Empleado> empleados) {
		// TODO menú gestor

	}

	private static void menu_vendedor(Empleado empleado, List<Planta> plantas) {
		String respuesta;
		do {
			System.out.println("\nManú vendedor");
			System.out.println("1. Catálogo de plantas");
			System.out.println("2. Vender plantas");
			System.out.println("3. Buscar ticket");
			System.out.println("4. Salir");
			System.out.print("Opción: ");
			respuesta = read.next();
			read.nextLine();

			while (!respuesta.matches("[1-4]")) {
				System.out.print("Introduce un número del 1 al 4: ");
				respuesta = read.next();
				read.nextLine();
			}

			switch (respuesta) {
			case "1":
				mostrar_catalogo_plantas(plantas);
				break;
			case "2":
				venta_plantas(empleado, plantas);
				break;
			case "3":
				buscar_tiket();
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
	}

	private static void buscar_tiket() {
		// TODO tickets

	}

	private static void venta_plantas(Empleado empleado, List<Planta> plantas) {
		// TODO vender
		// TODO devolver

	}

	private static void mostrar_catalogo_plantas(List<Planta> plantas) {
		for (Planta planta : plantas) {
			System.out.println(planta.toString());
		}

	}

	private static Empleado iniciar_sesión(List<Empleado> empleados) {
		Empleado seleccionado = null;
		System.out.println("\nIniciar sesión\n");
		System.out.print("Id: ");
		String id = read.nextLine();
		System.out.print("Contraseña: ");
		String passwd = read.nextLine();
		for (int i = 0; i < empleados.size(); i++) {
			if (id.compareToIgnoreCase(Integer.toString(empleados.get(i).getId())) == 0) {
				if (passwd.compareTo(empleados.get(i).getPasswd()) == 0) {
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
			System.out.println("Ha ocurrido un error en la escritura del fichero 'empleados.dat'.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Ha ocurrido un error en la escritura del fichero 'empleados.dat'.");
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
			System.out.println("Ha ocurrido un error en la lectura del fichero 'empleados.dat'.");
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			System.out.println("Ha ocurrido un error en la lectura del fichero 'empleados.dat'.");
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			System.out.println("Ha ocurrido un error en la lectura del fichero 'empleados.dat'.");
			// System.out.println("Comprueba que el archivo no esté vacío");
			e.printStackTrace();
			return null;
		}
		return listaEmpleados;
	}

	private static List<Planta> cargar_datos_plantas() {
		File listaPlantasFicheroXml = new File("PLANTAS/plantas.xml");
		File listaPlantasFicheroDat = new File("PLANTAS/plantas.dat");
		ArrayList<Planta> listaPlantas = new ArrayList<Planta>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder docB = dbf.newDocumentBuilder();
			Document doc = docB.parse(listaPlantasFicheroXml.getPath());
			doc.getDocumentElement().normalize();
			NodeList lista = doc.getElementsByTagName("planta");
			int i = 0;
			int codigo;

			RandomAccessFile raf = new RandomAccessFile(listaPlantasFicheroDat, "r");
			raf.seek(0);
			while (raf.getFilePointer() < raf.length() || i < lista.getLength()) {
				Node nodo = lista.item(i);
				if (nodo.getNodeType() == Node.ELEMENT_NODE) {
					float precio;
					int stock;
					int codigo1;
					do {
						codigo1 = raf.readInt();
						precio = raf.readFloat();
						stock = raf.readInt();
					} while (precio == 0 && stock == 0);

					Planta plantaTmp;
					Element planta = (Element) nodo;
					int codigo2 = Integer.valueOf(planta.getElementsByTagName("codigo").item(0).getTextContent());
					String nombre = planta.getElementsByTagName("nombre").item(0).getTextContent();
					String foto = planta.getElementsByTagName("foto").item(0).getTextContent();
					String descripcion = planta.getElementsByTagName("descripcion").item(0).getTextContent();
					if (codigo1 == codigo2)
						codigo = codigo1;
					else
						codigo = codigo2;
					plantaTmp = new Planta(codigo, nombre, foto, descripcion, precio, stock);
					listaPlantas.add(plantaTmp);
				}
				i++;
			}
			raf.close();
		} catch (IOException e) {
			System.out.println("Ha ocurrido un error en la lectura del fichero 'plantas.xml' o 'plantas.dat'.");
			e.printStackTrace();
			return null;
		} catch (ParserConfigurationException e) {
			System.out.println("Ha ocurrido un error en la lectura del fichero 'plantas.xml' o 'plantas.dat'.");
			e.printStackTrace();
			return null;
		} catch (SAXException e) {
			System.out.println("Ha ocurrido un error en la lectura del fichero 'plantas.xml' o 'plantas.dat'.");
			e.printStackTrace();
			return null;
		}
		return listaPlantas;
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
		ficheros.add(new File(directorios.get(3).getPath(), "0.txt"));
		ficheros.add(new File(directorios.get(4).getPath()));

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

}
