package TIENDA;

/*
 * Trabajo hecho por Arnau Blanch Manero
 */

public class Planta {
	private int codigo;
	private String nombre;
	private String foto;
	private String descripcion;
	//float precio;
	//int stock;
	
	public Planta(int codigo, String nombre, String foto, String descripcion) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.foto = foto;
		this.descripcion = descripcion;
	}
	@Override
	public String toString() {
		return "Código planta: " + codigo + "\n\tNombre: " + nombre + "\n\tFoto: " + foto + "\n\tDescripcion: " + descripcion;
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

	public String getCodigoCeros() {
		String codigo = this.codigo+"";
		String codigoCeros = "";
		for (int i = 0; i < 2-codigo.length(); i++) {
			codigoCeros += "0";
		}
		codigoCeros += codigo;
		return codigoCeros;
	}
	
}
