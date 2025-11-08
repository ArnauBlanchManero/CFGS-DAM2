package TIENDA;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Ticket {
	private String nombre;
	private int numero;
	//private static int totalVendido;

	public Ticket(int numero) {
		super();
		this.nombre = numero+".txt";
		this.numero = numero;
	}
	/*
	public Ticket(String nombre) {
		super();
		this.nombre = nombre;
		this.numero = Integer.valueOf(nombre.substring(0, nombre.lastIndexOf('.')));
	}
	 */
	public ArrayList<String> escribirTicket(List<Planta> plantasVendidas, List<Integer> cantidades, Empleado empleado) {
		ArrayList<String> lineas = new ArrayList<String>();
		lineas.add("TIENDA DE PLANTAS DE ARNAU");
		lineas.add("══════════════════════════");
		lineas.add("");
		lineas.add("Número de ticket: "+numero);
		lineas.add("Fecha: "+LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		lineas.add("····················································");
		lineas.add("Venta realizada por "+empleado.getNombre());
		lineas.add("Código de empleado: "+empleado.getIdCeros());
		lineas.add("");
		lineas.add("Código planta        Cantidad        Precio unitario");
		File listaPlantasFicheroDat = new File("PLANTAS/plantas.dat");
		RandomAccessFile raf;
		float precioPlanta=0f;
		String cero ="";
		float total=0f;
		try {
			raf = new RandomAccessFile(listaPlantasFicheroDat, "r");
			for (int i=0;i<plantasVendidas.size();i++) {
				raf.seek(((plantasVendidas.get(i).getCodigo()-1) * 12)+4);
				precioPlanta = raf.readFloat();
				if (precioPlanta == 0f) {
					return null;
				}
				cero ="";
				if(cantidades.get(i)<10)
					cero="0";
				lineas.add(plantasVendidas.get(i).getCodigoCeros()+"                   "+cero+cantidades.get(i)+"              "+precioPlanta+"€");
				total+=(float)cantidades.get(i)*precioPlanta;
			}
		} catch (Exception e) {
			lineas.add("00                   00              0€");
			e.printStackTrace();
		}
		lineas.add("");
		lineas.add("····················································");
		total = Math.round(total * 100f) / 100f;
		lineas.add("Total: "+total+"€");
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
	public void mostrarContenido() {
		Path directorio = Path.of("TICKETS");
		try {
			Stream<Path> flujo= Files.list(directorio);
			flujo.forEach(archivo->System.out.println(archivo.getFileName()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
