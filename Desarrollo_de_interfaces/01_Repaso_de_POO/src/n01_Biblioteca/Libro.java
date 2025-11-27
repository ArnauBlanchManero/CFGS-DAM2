package n01_Biblioteca;

public class Libro extends Biblio implements Prestable<Libro>{
	boolean prestado;

	public Libro(String codigo, String titulo, int anoPublicacion) {
		super(codigo, titulo, anoPublicacion);
		this.prestado = false;
	}

	@Override
	public String toString() {
		String esPrestado;
		if (prestado) {
			esPrestado = "Está prestado.";
		} else {
			esPrestado = "No está prestado.";
		}
		return "Libro '"+titulo+"' con código "+getCodigo()+", se publicó en el año "+anoPublicacion+". "+esPrestado;
		/*return "Libro [prestado=" + prestado + ", codigo=" + codigo + ", titulo=" + titulo + ", anoPublicacion="
				+ anoPublicacion + ", getCodigo()=" + getCodigo() + ", getAnoPublicacion()=" + getAnoPublicacion()
				+ ", prestar()=" + prestar() + ", devolver()=" + devolver() + ", prestado()=" + prestado()
				+ ", toString()=" + super.toString() + ", getTitulo()=" + getTitulo() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + "]";*/
	}

	@Override
	public String getCodigo() {
		return "ISBN: "+ codigo;
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

	@Override
	public boolean prestar() {
		if (prestado) {
			System.out.println("No te podemos prestar el libro "+titulo);
		} else {
			System.out.println("Acabas de pedir prestado el libro "+titulo);
			prestado = true;
		}
		return prestado;
		//return true;
	}

	@Override
	public boolean devolver() {
		if (!prestado) {
			System.out.println("No puedes devolver el libro "+titulo);
		} else {
			System.out.println("Acabas de devolver el libro "+titulo);
			prestado = false;
		}
		return prestado;
		//return false;
	}

	@Override
	public boolean prestado() {
		if (prestado) {
			System.out.println("Al tener este libro prestado puedes devolverlo.");
		} else {
			System.out.println("Al no tener este libro prestado puedes pedirlo prestado.");
		}
		return prestado;
	}
	
}
