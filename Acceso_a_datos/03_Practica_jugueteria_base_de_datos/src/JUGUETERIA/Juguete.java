package JUGUETERIA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Juguete {
	private int id;
	private String nombre;
	private String descripcion;
	private double precio;
	private int cantidad;
	private CategoriaJuguete categoria;
	private boolean visible;
	
	public Juguete(int id, String nombre, String descripcion, double precio, int cantidad, String categoria) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.cantidad = cantidad;
		if (categoria!=null) {
			switch (categoria) {
				case "Vehiculos":
					this.categoria = CategoriaJuguete.VEHICULOS;
					break;
				case "Muñecas":
					this.categoria = CategoriaJuguete.MUÑECAS;
					break;
				case "Electronicos":
					this.categoria = CategoriaJuguete.ELECTRONICOS;
					break;
				case "Libre":
					this.categoria = CategoriaJuguete.LIBRE;
					break;
				case "Accion":
					this.categoria = CategoriaJuguete.ACCION;
					break;
				case "Mesa":
					this.categoria = CategoriaJuguete.MESA;
					break;
				case "Construccion":
					this.categoria = CategoriaJuguete.CONSTRUCCION;
					break;
				case "Peluches":
					this.categoria = CategoriaJuguete.PELUCHES;
					break;
		
				default:
					this.categoria = null;
					break;
			}
		}
		ResultSet datosJuguete = BaseDatos.consulta("SELECT Visible FROM juguetes WHERE idJuguete = "+id);
		try {
			if(datosJuguete.next()) {
				this.visible = datosJuguete.getBoolean(1);
			}
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No hay datos para leer");
			e.printStackTrace();
		}
	}

	public Juguete(int id) {
		super();
		this.id = id;
		ResultSet datosJuguete = BaseDatos.consulta("SELECT * FROM juguetes WHERE idJuguete = "+id);
		try {
			if(datosJuguete.next()) {
				this.nombre = datosJuguete.getString(2);
				this.descripcion = datosJuguete.getString(3);
				this.precio = datosJuguete.getDouble(4);
				this.cantidad = datosJuguete.getInt(5);
				if (datosJuguete.getString(6)!=null) {
					switch (datosJuguete.getString(6)) {
						case "Vehiculos":
							this.categoria = CategoriaJuguete.VEHICULOS;
							break;
						case "Muñecas":
							this.categoria = CategoriaJuguete.MUÑECAS;
							break;
						case "Electronicos":
							this.categoria = CategoriaJuguete.ELECTRONICOS;
							break;
						case "Libre":
							this.categoria = CategoriaJuguete.LIBRE;
							break;
						case "Accion":
							this.categoria = CategoriaJuguete.ACCION;
							break;
						case "Mesa":
							this.categoria = CategoriaJuguete.MESA;
							break;
						case "Construccion":
							this.categoria = CategoriaJuguete.CONSTRUCCION;
							break;
						case "Peluches":
							this.categoria = CategoriaJuguete.PELUCHES;
							break;
				
						default:
							this.categoria = null;
							break;
					}
				}
				this.visible = datosJuguete.getBoolean(7);
			}
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No hay datos para leer");
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		if(visible)
			return "Juguete [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", precio=" + precio
				+ ", cantidad=" + cantidad + ", categoria=" + categoria + "]";
		return "Juguete con id "+id+" no disponible";
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
		if(BaseDatos.consultaModifica("UPDATE juguetes set Visible = FALSE WHERE idJuguete = "+id)==1)
			visible = false;
	}

	public void modificar(ArrayList<String> columnas, ArrayList<String> datos, int id) {
		String seters = "";
		int i;
		for (i = 0; i < columnas.size()-1; i++) {
			seters += columnas.get(i)+" = ";
			if (columnas.get(i).equals("Nombre ") || columnas.get(i).equals("Descripcion ") || columnas.get(i).equals("Categoria "))
				seters += "'"+datos.get(i)+"', ";
			else
				seters += datos.get(i)+", ";
		}
		seters += columnas.get(i)+" = ";
		if (columnas.get(i).equals("Nombre ") || columnas.get(i).equals("Descripcion ") || columnas.get(i).equals("Categoria "))
			seters += "'"+datos.get(i)+"' ";
		else
			seters += datos.get(i)+" ";

		if(BaseDatos.consultaModifica("UPDATE juguetes SET "+seters+"WHERE idJuguete = "+id+";")==1)
			System.out.println("Juguete modificado correctamente.");
		else {
			System.out.println("Error al modificar el juguete.");
		}
	}
	
	public static void registrarNuevoJuguete(String nombreNuevo, String descNueva, String precioNuevo, int cantNueva, String categoriaNueva) {
		if (!nombreNuevo.equals("NULL")) {
			nombreNuevo = "'"+nombreNuevo+"'";
		}
		if (!descNueva.equals("NULL")) {
			descNueva = "'"+descNueva+"'";
		}
		if (!categoriaNueva.equals("NULL")) {
			categoriaNueva = "'"+categoriaNueva+"'";
		}
		if(BaseDatos.consultaModifica("INSERT INTO juguetes (Nombre, Descripcion, Precio, Cantidad_stock, Categoria, Visible) VALUES ("+nombreNuevo+", "+descNueva+", "+precioNuevo+", "+cantNueva+", "+categoriaNueva+", TRUE);")==1)
			System.out.println("Juguete añadido correctamente.");
		else {
			System.out.println("Error al añadir el nuevo juguete.");
		}
	}

	public static void mostrarPrecio() {
		// TODO Auto-generated method stub
		
	}

	public static void mostrarPorStand(String idStand) {
		// TODO Auto-generated method stub
		
	}

	public static void mostrarPorId(int idBuscar) {
		ResultSet datosJuguetes = BaseDatos.consulta("SELECT * FROM juguetes WHERE idJuguete = "+idBuscar);
		try {
			if(datosJuguetes.next()) {
				Juguete jugueteTMP;
				int id = datosJuguetes.getInt(1);
				String nombre = datosJuguetes.getString(2);
				String descripcion = datosJuguetes.getString(3);
				Double precio = datosJuguetes.getDouble(4);
				int cantidad = datosJuguetes.getInt(5);
				String categoria = datosJuguetes.getString(6);
				
				jugueteTMP = new Juguete(id, nombre, descripcion, precio, cantidad, categoria);
				System.out.println(jugueteTMP.toString());
			}
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No hay datos para leer");
			e.printStackTrace();
		}
	}

}
