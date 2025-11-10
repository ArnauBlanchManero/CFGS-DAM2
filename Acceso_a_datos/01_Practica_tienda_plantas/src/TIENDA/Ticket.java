package TIENDA;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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

public class Ticket {
	private String nombre;
	private int numero;
	// private static int totalVendido;

	public Ticket(int numero) {
		super();
		this.nombre = numero + ".txt";
		this.numero = numero;
	}

	/*
	 * public Ticket(String nombre) { super(); this.nombre = nombre; this.numero =
	 * Integer.valueOf(nombre.substring(0, nombre.lastIndexOf('.'))); }
	 */
	public ArrayList<String> escribirTicket(List<Planta> plantasVendidas, List<Integer> cantidades, Empleado empleado) {
		ArrayList<String> lineas = new ArrayList<String>();
		lineas.add("TIENDA DE PLANTAS DE ARNAU");
		lineas.add("══════════════════════════");
		lineas.add("");
		lineas.add("Número de ticket: " + numero);
		lineas.add("Fecha: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		lineas.add("····················································");
		lineas.add("Venta realizada por " + empleado.getNombre());
		lineas.add("Código de empleado: " + empleado.getIdCeros());
		lineas.add("");
		lineas.add("Código planta        Cantidad        Precio unitario");
		File listaPlantasFicheroDat = new File("PLANTAS/plantas.dat");
		RandomAccessFile raf;
		float precioPlanta = 0f;
		String cero = "";
		float total = 0f;
		try {
			raf = new RandomAccessFile(listaPlantasFicheroDat, "r");
			for (int i = 0; i < plantasVendidas.size(); i++) {
				raf.seek(((plantasVendidas.get(i).getCodigo() - 1) * 12) + 4);
				precioPlanta = raf.readFloat();
				if (precioPlanta == 0f) {
					return null;
				}
				cero = "";
				if (cantidades.get(i) < 10)
					cero = "0";
				lineas.add(plantasVendidas.get(i).getCodigoCeros() + "                   " + cero + cantidades.get(i)
						+ "              " + precioPlanta + "€");
				total += (float) cantidades.get(i) * precioPlanta;
			}
		} catch (Exception e) {
			lineas.add("00                   00              0€");
			e.printStackTrace();
		}
		lineas.add("");
		lineas.add("····················································");
		total = Math.round(total * 100f) / 100f;
		lineas.add("Total: " + total + "€");
		lineas.add("");
		return lineas;
	}

	public boolean crearTicket(ArrayList<String> contenidoTicket) {
		File ficheroTicket = new File("TICKETS", nombre);
		if (ficheroTicket.exists()) {
			System.out.println("No se ha podido crear el ticket, ya existe.");
		} else {
			try {
				ficheroTicket.createNewFile();
				FileWriter escribir = new FileWriter(ficheroTicket);
				BufferedWriter buffer = new BufferedWriter(escribir);
				for (int i = 0; i < contenidoTicket.size(); i++) {
					buffer.write(contenidoTicket.get(i));
					buffer.newLine();
				}
				buffer.close();
				System.out.println("Ticket creado correctamente.");
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public String devolverContenido() {
		Path directorio = Path.of("TICKETS");
		try {
			Stream<Path> flujo = Files.list(directorio);
			// flujo.count();
			List<Path> ficheros = flujo.toList();
			for (Path fichero : ficheros) {
				if (nombre.equals(fichero.getFileName().toString())) {
					return Files.readString(fichero);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "Ticket "+numero+" no encontrado.";
		}
		return "Ticket "+numero+" no encontrado.";
	}

	public ArrayList<Planta> marcarDevuelto(ArrayList<Planta> plantas) {
		Path fichero1 = Paths.get("TICKETS", nombre);
		Path fichero2 = Paths.get("DEVOLUCIONES", nombre);
		try {
			List<String> lineasContenido = Files.readAllLines(fichero1);
			for (int i = 0; i < lineasContenido.size() - 2; i++) {
				String linea = lineasContenido.get(i) + System.lineSeparator();
				Files.write(fichero2, linea.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE,
						StandardOpenOption.APPEND);
			}
			String[] lineaTotal = lineasContenido.get(lineasContenido.size() - 2).split(" ");
			String numNegativo = "-" + lineaTotal[1];
			numNegativo = "Total: " + numNegativo + System.lineSeparator();
			Files.write(fichero2, numNegativo.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE,
					StandardOpenOption.APPEND);
			Files.write(fichero2, "--DEVOLUCIÓN--".getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE,
					StandardOpenOption.APPEND);
			ArrayList<String> codigos = new ArrayList<String>();
			ArrayList<String> cantidades = new ArrayList<String>();
			ArrayList<String> precios = new ArrayList<String>();
			for (int i = 10; i < lineasContenido.size() - 14 + 10; i++) {
				String[] infoPlantas = lineasContenido.get(i).split(" ");
				codigos.add(infoPlantas[0]);
				cantidades.add(infoPlantas[19]);
				String [] precio = infoPlantas[33].split("€");
				precios.add(precio[0]);
			}
			// ArrayList<Planta> plantasBaja = cargar_datos_plantas(new
			// File("PLANTAS/plantasBaja.xml"));
			File listaPlantasFicheroDat = new File("PLANTAS/plantas.dat");
			RandomAccessFile raf;
			int cantidadAnterior = 0;
			float precioPlanta = 0f;
			int puntero = 0;
			try {
				for (int j = 0; j < codigos.size(); j++) {
					puntero = ((Integer.valueOf(codigos.get(j)) - 1) * 12) + 4;
					raf = new RandomAccessFile(listaPlantasFicheroDat, "rw");
					raf.seek(puntero);
					precioPlanta = raf.readFloat();
					cantidadAnterior = raf.readInt();
					raf.seek(puntero);
					int nuevaCantidad = cantidadAnterior + Integer.valueOf(cantidades.get(j));
					if (precioPlanta == 0f) {
						try {
							RandomAccessFile raf2;
							File listaPlantasBajaFicheroDat = new File("PLANTAS/plantasbaja.dat");
							raf2 = new RandomAccessFile(listaPlantasBajaFicheroDat, "rw");
							raf2.seek(0);
							int buscaCodigo = raf2.readInt();
							raf2.seek(raf2.getFilePointer()+8);
							while (raf2.getFilePointer() < raf2.length() && Integer.valueOf(codigos.get(j)) == buscaCodigo) {
								buscaCodigo = raf2.readInt();
								raf2.seek(raf2.getFilePointer()+8);
							}
							if (Integer.valueOf(codigos.get(j)) != buscaCodigo) {
								System.out.println("No se ha encontrado el código de la planta "+codigos.get(j)+" en el fichero "+ listaPlantasBajaFicheroDat.getName());
							} else {
								raf2.seek(raf2.getFilePointer()-8);
								precioPlanta = raf2.readFloat();
							}
							/*
							if (precioPlanta != Float.valueOf(precios.get(j))){
								precioPlanta = Float.valueOf(precios.get(j));
							}
							*/
							//raf.writeFloat(precioPlanta);
							ArrayList<Planta> plantasBaja = cargar_datos_plantas(new File("PLANTAS/plantasBaja.xml"));
							Planta planta = null;
							for (int i = 0; i < plantasBaja.size(); i++) {
								if (plantasBaja.get(i).getCodigo() == Integer.valueOf(codigos.get(j))) {
									planta = plantasBaja.get(i);
								}
							}
							if (planta!=null) {
								plantas.add(planta);
								plantasBaja.remove(planta);
							} else {
								System.out.println("Error. Comprueba que la planta con código "+codigos.get(j)+" exista.");
							}
							int bien = guardar_plantas(plantasBaja, new File("PLANTAS/plantasBaja.xml"));
							if (bien == 1) {
								System.out.println("La planta con código "+ planta.getCodigo()+" ya no está de baja.");
							}
							try {
								Files.deleteIfExists(fichero1);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							raf2.close();
						} catch (IOException e) {
							System.out.println("Ha ocurrido un error.");
							e.printStackTrace();
						}
					}
					raf.writeFloat(precioPlanta);
					raf.writeInt(nuevaCantidad);
					raf.close();
				}
			} catch (Exception e) {
				System.out.println("Error. No se ha encontrado el código de la planta a devolver.");
				e.printStackTrace();
			}
		} catch (IOException e) {
			System.out.println("Error. No se ha devuelto el ticket.");
			e.printStackTrace();
		}
		return plantas;
	}

	private int guardar_plantas(ArrayList<Planta> plantasBaja, File file) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();

			Element root = doc.createElement("plantas");
			doc.appendChild(root);
			for (Planta añadeplanta : plantasBaja) {
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
			StreamResult result = new StreamResult(file);
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

	private ArrayList<Planta> cargar_datos_plantas(File file) {
		ArrayList<Planta> listaPlantas = new ArrayList<Planta>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder docB = dbf.newDocumentBuilder();
			Document doc = docB.parse(file.getPath());
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
					.println("Ha ocurrido un error en la lectura del fichero '" + file.getPath() + "'.");
			e.printStackTrace();
			return null;
		} catch (ParserConfigurationException e) {
			System.out
					.println("Ha ocurrido un error en la lectura del fichero '" + file.getPath() + "'.");
			e.printStackTrace();
			return null;
		} catch (SAXException e) {
			System.out
					.println("Ha ocurrido un error en la lectura del fichero '" + file.getPath() + "'.");
			e.printStackTrace();
			return null;
		}
		return listaPlantas;
	}

}
