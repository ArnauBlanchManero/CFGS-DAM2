package JUGUETERIA;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Stand {
	private int idStand;
	private int idZona;
	private String id;
	private String nombre;
	private String descripcion;
	
	public Stand(int idStand, int idZona, String nombre, String descripcion) {
		super();
		this.idStand = idStand;
		this.idZona = idZona;
		this.id = idZona + " " +idStand;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "Stand [idStand=" + idStand + ", idZona=" + idZona + ", id=" + id + ", nombre=" + nombre
				+ ", descripcion=" + descripcion + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static void mostrarPorId(int idBuscar) {
		ResultSet datosStands = BaseDatos.consulta("SELECT * FROM juguetes WHERE idJuguete = "+idBuscar);
		try {
			if(datosStands.next()) {
				Stand standTMP;
				int idStand = datosStands.getInt(1);
				String nombre = datosStands.getString(2);
				String descripcion = datosStands.getString(3);
				int idZona = datosStands.getInt(4);
				
				standTMP = new Stand(idStand, idZona, nombre, descripcion);
				System.out.println(standTMP.toString());
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
