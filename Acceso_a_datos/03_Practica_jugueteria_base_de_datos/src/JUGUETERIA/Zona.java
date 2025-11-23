package JUGUETERIA;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Zona {
	private int id;
	private String nombre;
	private String descripcion;
	
	public Zona(int id, String nombre, String descripcion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "Zona [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
	}

	public static void mostrarPorId(int idBuscar) {
		ResultSet datosZonas = BaseDatos.consulta("SELECT * FROM zonas WHERE idZona = "+idBuscar);
		try {
			if(datosZonas.next()) {
				Zona zonaTMP;
				int id = datosZonas.getInt(1);
				String nombre = datosZonas.getString(2);
				String descripcion = datosZonas.getString(3);
				
				zonaTMP = new Zona(id, nombre, descripcion);
				System.out.println(zonaTMP.toString());
			}
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No hay datos para leer");
			e.printStackTrace();
		}
	}
	
}
