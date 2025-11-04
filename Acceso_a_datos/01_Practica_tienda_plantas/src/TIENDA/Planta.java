package TIENDA;

public class Planta {
	int codigo;
	String nombre;
	String foto;
	String descripcion;
	float precio;
	int stock;
	public Planta(int codigo, String nombre, String foto, String descripcion, float precio, int stock) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.foto = foto;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
	}
	@Override
	public String toString() {
		return "Código planta " + codigo + "\n\tNombre: " + nombre + "\n\tFoto: " + foto + "\n\tDescripcion: " + descripcion + "\n\tPrecio: " + precio + "\n\tStock: " + stock + "\n";
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	
}
