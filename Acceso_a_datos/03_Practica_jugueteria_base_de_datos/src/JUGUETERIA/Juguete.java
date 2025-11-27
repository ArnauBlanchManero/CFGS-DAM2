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
			switch (categoria.toLowerCase()) {
				case "vehiculos":
					this.categoria = CategoriaJuguete.VEHICULOS;
					break;
				case "muñecas":
					this.categoria = CategoriaJuguete.MUÑECAS;
					break;
				case "electronicos":
					this.categoria = CategoriaJuguete.ELECTRONICOS;
					break;
				case "libre":
					this.categoria = CategoriaJuguete.LIBRE;
					break;
				case "accion":
					this.categoria = CategoriaJuguete.ACCION;
					break;
				case "mesa":
					this.categoria = CategoriaJuguete.MESA;
					break;
				case "construccion":
					this.categoria = CategoriaJuguete.CONSTRUCCION;
					break;
				case "peluches":
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
			return nombre + " (ID: " + id +
			           " • Precio: " + precio + "€" +
			           " • Stock: " + cantidad +
			           " • Categoría: " + categoria +
			           ")";
		return "Juguete con id "+id+" no disponible";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
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

	public void modificar(ArrayList<String> columnas, ArrayList<Object> datos, int id) {
		ArrayList<Object> param = new ArrayList<Object>();
		String seters = "";
		int i;
		for (i = 0; i < columnas.size()-1; i++) {
			seters += columnas.get(i)+" = ";
			param.add(datos.get(i));
			seters += "?, ";
		}
		seters += columnas.get(i)+" = ";
		param.add(datos.get(i));
		seters += "? ";
		param.add(id);
		if(BaseDatos.modificaSeguro(param, "UPDATE juguetes SET "+seters+"WHERE idJuguete = ?")==1)
			System.out.println("Juguete modificado correctamente.");
		else {
			System.out.println("Error al modificar el juguete.");
		}
	}
	
	public static int registrarNuevoJuguete(String nombreNuevo, String descNueva, Double precioNuevo, int cantNueva, String categoriaNueva) {
		ArrayList<Object> param = new ArrayList<Object>();
		param.add(nombreNuevo);
		param.add(descNueva);
		param.add(precioNuevo);
		param.add(cantNueva);
		param.add(categoriaNueva);
		int idRet = 0;
		if(BaseDatos.modificaSeguro(param, "INSERT INTO juguetes (Nombre, Descripcion, Precio, Cantidad_stock, Categoria, Visible) VALUES (?, ?, ?, ?, ?, TRUE)")==1) {
			System.out.println("Juguete añadido correctamente.");
			ResultSet datosJuguetes = BaseDatos.consulta("SELECT idJuguete FROM juguetes ORDER BY idJuguete");
			try {
				while(datosJuguetes.next()) {
					idRet = datosJuguetes.getInt(1);
				}
			} catch (SQLException e) {
				System.out.println("La consulta no se ha ejecutado correctamente.");
				e.printStackTrace();
			}
		} else {
			System.out.println("Error al añadir el nuevo juguete.");
		}
		return idRet;
	}

	public static void mostrarPrecio() {
		ResultSet datosJuguetes = BaseDatos.consulta("SELECT idJuguete FROM juguetes ORDER BY precio");
		try {
			while(datosJuguetes.next()) {
				Juguete j = new Juguete(datosJuguetes.getInt(1));
				System.out.println(j.toString());
			}
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			e.printStackTrace();
		}
	}

	public static void mostrarPorStand(int idZona, int idStand) {
		ResultSet datosJuguetes = BaseDatos.consulta("SELECT juguete_idJuguete FROM stocks WHERE stand_idStand = "+idStand+" AND stand_zona_idZona = "+idZona);
		try {
			while(datosJuguetes.next()) {
				Juguete j = new Juguete(datosJuguetes.getInt(1));
				System.out.println(j.toString());
			}
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			e.printStackTrace();
		}
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
