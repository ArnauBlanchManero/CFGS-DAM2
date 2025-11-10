package TIENDA;

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
		return "Código planta " + codigo + "\n\tNombre: " + nombre + "\n\tFoto: " + foto + "\n\tDescripcion: " + descripcion;
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
	/*
	public float getPrecio(int codigo, File fichero) {
		RandomAccessFile raf;
		try {
			raf = new RandomAccessFile(fichero, "r");
			raf.seek(((codigo-1)*12)+4);
			precio = raf.readFloat();
		} catch (Exception e) {
			System.out.println("Se ha producido un error al leer el precio.");
		}
		return precio;
	}
	public void setPrecio(int codigo, File fichero, float precionuevo) {
		RandomAccessFile raf;
		try {
			raf = new RandomAccessFile(fichero, "rw");
			raf.seek(((codigo-1)*12)+4);
			raf.writeFloat(precionuevo);
		} catch (Exception e) {
			System.out.println("Se ha producido un error al guardar el precio.");
		}
		this.precio = precionuevo;
	}
	public int getStock(int codigo, File fichero) {
		RandomAccessFile raf;
		try {
			raf = new RandomAccessFile(fichero, "r");
			raf.seek(((codigo-1)*12)+8);
			stock = raf.readInt();
		} catch (Exception e) {
			System.out.println("Se ha producido un error al leer el stock.");
		}
		return stock;
	}
	public void setStock(int codigo, File fichero, int stocknuevo) {
		RandomAccessFile raf;
		try {
			raf = new RandomAccessFile(fichero, "rw");
			raf.seek(((codigo-1)*12)+8);
			raf.writeInt(stocknuevo);
		} catch (Exception e) {
			System.out.println("Se ha producido un error al guardar el stock.");
		}
		this.stock = stocknuevo;
	}
	*/
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
