package JUGUETERIA;

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
	
}
