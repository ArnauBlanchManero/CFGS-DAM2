package JUGUETERIA;

public class Juguete {
	private int id;
	private String nombre;
	private String descripcion;
	private double precio;
	private int cantidad;
	private CategoriaJuguete categoria;
	private boolean visible;
	
	public Juguete(int id, String nombre, String descripcion, double precio, int cantidad) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.cantidad = cantidad;
//		this.visible = visible;
	}

	@Override
	public String toString() {
		return "Juguete [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", precio=" + precio
				+ ", cantidad=" + cantidad + ", categoria=" + categoria + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void eliminar() {
		visible = false;
		
	}
	
	public void modificar(String nombreNuevo, String descNueva, double precioNuevo, int cantNueva, String categoriaNueva) {
		// TODO Auto-generated method stub
		
	}

	public static void registrarNuevoJuguete(String nombreNuevo, String descNueva, double precioNuevo, int cantNueva, String categoriaNueva) {
		/*
		 * "INSERT INTO `jugueteria`.`juguetes` (`Nombre`, `Descripcion`, `Precio`, `Cantidad_stock`, `Categoria`, `Visible`) VALUES" +
		    "('Auto de Carrera', 'Auto a fricción rojo', 12.99, 50, 'Vehículos', TRUE),"
		 */
		BaseDatos.consultaModifica("INSERT INTO juguetes (Nombre, Descripcion, Precio, Cantidad_stock, Categoria, Visible) VALUES ('"+nombreNuevo+"', '"+descNueva+"', "+precioNuevo+", "+cantNueva+", '"+categoriaNueva+"', TRUE);");
		
	}

	public static void mostarPrecio() {
		// TODO Auto-generated method stub
		
	}

	public static void mostrar(String idStand) {
		// TODO Auto-generated method stub
		
	}
	
}
