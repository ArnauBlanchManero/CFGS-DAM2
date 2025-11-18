package JUGUETERIA;

public class Stand {
	private int num;
	private int idZona;
	private String id;
	private String nombre;
	private String descripcion;
	
	public Stand(int num, int idZona, String nombre, String descripcion) {
		super();
		this.num = num;
		this.idZona = idZona;
		this.id = num + " " +idZona;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}
	
}
