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
			return "Ticket no encontrado.";
		}
		return "Ticket no encontrado.";
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
			for (int i = 10; i < lineasContenido.size() - 14 + 10; i++) {
				String[] infoPlantas = lineasContenido.get(i).split("                   ");
				codigos.add(infoPlantas[0]);
				cantidades.add(infoPlantas[1]);
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
							File listaPlantasBajaFicheroDat = new File("PLANTAS/plantasbaja.dat");
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
					}
					raf.writeFloat(precioPlanta);
					raf.writeInt(nuevaCantidad);
				}
			} catch (Exception e) {
				System.out.println("Ha ocurrido un error en la lectura del fichero 'plantas.dat'.");
				e.printStackTrace();
				return false;
			}
			/*
			 * for (int i = 0; i < plantas.size(); i++) { if (plantas.get(i).getCodigo() ==
			 * codigo) { return i; } } return -1;
			 */
			// Files.deleteIfExists(fichero1);
			System.out.println("Ticket devuelto correctamente.");
		} catch (IOException e) {
			System.out.println("Error en la devolucion.");
			e.printStackTrace();
		}
		return plantas;
	}

}
