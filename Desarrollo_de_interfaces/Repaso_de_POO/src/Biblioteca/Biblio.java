package Biblioteca;

public abstract class Biblio {
	String codigo;
	String titulo;
	int anoPublicacion;
	public Biblio(String codigo, String titulo, int anoPublicacion) {
		super();
		this.codigo = codigo;
		this.titulo = titulo;
		this.anoPublicacion = anoPublicacion;
	}
	@Override
	public String toString() {
		return "Biblio [codigo=" + codigo + ", titulo=" + titulo + ", anoPublicacion=" + anoPublicacion + "]";
	}
	
	public abstract String getCodigo();
	public abstract void setCodigo(String codigo);
	public abstract int getAnoPublicacion();
	public abstract void setAnoPublicacion(int anoPublicacion);
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
}
