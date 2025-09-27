package Biblioteca;

public class Revista extends Biblio{

	int numero;

	public Revista(String codigo, String titulo, int anoPublicacion, int numero) {
		super(codigo, titulo, anoPublicacion);
		this.numero = numero;
	}

	@Override
	public String toString() {
		return "Revista '"+titulo+"' con código "+getCodigo()+" y número: "+numero+", se publicó en el año "+getAnoPublicacion();
		/*return "Revista [numero=" + numero + ", codigo=" + codigo + ", titulo=" + titulo + ", anoPublicacion="
				+ anoPublicacion + ", getNumero()=" + getNumero() + ", getCodigo()=" + getCodigo()
				+ ", getAnoPublicacion()=" + getAnoPublicacion() + ", toString()=" + super.toString() + ", getTitulo()="
				+ getTitulo() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";*/
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	@Override
	public String getCodigo() {
		return "ISSN: "+codigo;
	}

	@Override
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Override
	public int getAnoPublicacion() {
		return anoPublicacion;
	}

	@Override
	public void setAnoPublicacion(int anoPublicacion) {
		this.anoPublicacion = anoPublicacion;
	}
}
