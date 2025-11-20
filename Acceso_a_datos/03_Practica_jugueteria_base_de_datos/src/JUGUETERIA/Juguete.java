package JUGUETERIA;

public class Juguete {
	private int id;
	private String nombre;
	private String descripcion;
	private double precio;
	private int cantidad;
	private CategoriaJuguete categoria;
	
	public Juguete(int id, String nombre, String descripcion, double precio, int cantidad) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "Juguete [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", precio=" + precio
				+ ", cantidad=" + cantidad + ", categoria=" + categoria + "]";
	}
	
}
