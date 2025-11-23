package JUGUETERIA;

import java.util.ArrayList;
import java.sql.Date;
import java.util.Iterator;
import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Menu {
	private ArrayList<String> opciones;
	private int importancia;
	private String titulo;
	
	public Menu(ArrayList<String> opciones, int importancia, String titulo) {
		super();
		this.opciones = opciones;
		this.importancia = importancia;
		this.titulo = titulo;
	}

	public void mostrar() {
		char c = '─';
		if (importancia == 1) {
			c = '═';
		}
		imprimir_n_veces(c, titulo.length());
		System.out.println("\n"+titulo);
		imprimir_n_veces(c, titulo.length());
		System.out.println("");
		for (int i = 1; i <= opciones.size(); i++) {
			System.out.println(i+". "+opciones.get(i-1));
		}
		System.out.println((opciones.size()+1)+". Salir");
	}

	private void imprimir_n_veces(char c, int longitud) {
		for (int i = 0; i < longitud; i++) {
			System.out.print(c);
		}
	}

	public int respuesta(String especificacion) {
		mostrar();
		String numRespuesta;
		String regex = "^[1-"+(opciones.size()+1)+"]$";
		numRespuesta = Utilidades.preguntarRegex(regex, "Opcion: ", especificacion);
		return Integer.valueOf(numRespuesta);
	}

	public void opcionesJuguetes(int num) {
		switch (num) {
		case 1:
			pedir_datos_juguete_nuevo();
			break;
		case 2:
			pedir_datos_juguete_modificar();
			break;
		case 3:
			pedir_id_juguete_eliminar();
			break;

		default:
			break;
		}
	}

	private void pedir_id_juguete_eliminar() {
		System.out.println("\nEliminar un juguete");
		System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
		String idSeleccionado = pedir_id("Escribe el id del juguete que quieres eliminar: ", "idJuguete", "juguetes", "WHERE Visible = TRUE");
		Juguete jugueteEliminar = new Juguete(Integer.valueOf(idSeleccionado));
		System.out.println("Esta es la información del juguete que vas a eliminar\n"+jugueteEliminar.toString());
		String confirmacion = Utilidades.preguntarString("¿Seguro que quieres eliminarlo? (si o no): ");
		if (confirmacion.equalsIgnoreCase("si"))
			jugueteEliminar.eliminar();
		else 
			System.out.println("No se eliminará el juguete.");
		if(!jugueteEliminar.isVisible()) 
			System.out.println("Juguete eliminado correctamente");
		else
			System.out.println("Error al eliminar el juguete con id "+jugueteEliminar.getId());
	}

	private void pedir_datos_juguete_modificar() {
		System.out.println("\nModificar un juguete");
		System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
		String idSeleccionado = pedir_id("Escrbe el id del juguete que quieres modificar: ", "idJuguete", "juguetes", "WHERE Visible = TRUE");
		System.out.println("Deja el campo vacío si no lo qieres cambiar.");
		ArrayList<String> columnas = new ArrayList<String>();
		ArrayList<Object> datos = new ArrayList<Object>();
		Juguete jugueteModificar = new Juguete(Integer.valueOf(idSeleccionado));
		String nombre;
		String desc;
		Double precio;
		int cant;
		String categoriaS;
		nombre = Utilidades.preguntarLongitud(45, "Nombre: ", "menos de 45 caracteres");
		if(nombre.length()!=0) {
			columnas.add("Nombre ");
			datos.add(nombre);
		}
		desc = Utilidades.preguntarLongitud(150, "Descripción: ", "menos de 150 caracteres");
		if(desc.length()!=0) {
			columnas.add("Descripcion ");
			datos.add(desc);
		}
		precio = Utilidades.preguntarDouble("Precio: ", "un simple número con decimales");
		if(precio != -1D) {
			columnas.add("Precio ");
			datos.add(precio);
		}
		String cantS;
		cantS = Utilidades.preguntarRegex("^[0-9]*$", "Cantidad: ", "un numero entero");
		try {
			cant = Integer.valueOf(cantS);
			columnas.add("Cantidad_stock ");
			datos.add(cant);
		} catch (Exception e) {
			cant = jugueteModificar.getCantidad();
		}
		categoriaS = Utilidades.preguntarCategoriaJuguete();
		if (!categoriaS.equals("")) {
			columnas.add("Categoria ");
			datos.add(categoriaS);
		}
		if (columnas.size()>0) {
			jugueteModificar.modificar(columnas, datos, jugueteModificar.getId());
		}else {
			System.out.println("No se actualizará ninguna columna.");
		}
	}
	
	private void pedir_datos_juguete_nuevo() {
		System.out.println("\nAñadir un juguete");
		System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
		System.out.println("Por favor, rellena los siguientes campos");
		String nombre;
		String desc;
		Double precio;
		int cant;
		String categoriaS;
		do {
		nombre = Utilidades.preguntarLongitud(45, "Nombre: ", "menos de 45 caracteres");
		} while(nombre.length()==0);
		do {
		desc = Utilidades.preguntarLongitud(150, "Descripción: ", "menos de 150 caracteres");
		} while(desc.length()==0);
		do {
		precio = Utilidades.preguntarDouble("Precio: ", "un simple número con decimales");
		} while (precio == -1D);
		cant = Integer.valueOf(Utilidades.preguntarRegex("^[0-9]+$", "Cantidad: ", "un numero entero"));
		do {
		categoriaS = Utilidades.preguntarCategoriaJuguete();
		} while (categoriaS.equals(""));
		Juguete.registrarNuevoJuguete(nombre, desc, precio, cant, categoriaS);
		
	}

	public void opcionesEmpleados(int num) {
		switch (num) {
		case 1:
			pedir_datos_empleado_nuevo();
			break;
		case 2:
			pedir_datos_empleado_modificar();
			break;
		case 3:
			pedir_id_empleado_eliminar();
			break;

		default:
			break;
		}
	}

	private void pedir_id_empleado_eliminar() {
		System.out.println("\nEliminar un empleado");
		System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
		String idSeleccionado = pedir_id("Escrbe el id del empleado que quieres eliminar: ", "idEmpleado", "empleados", "");
		Empleado empleadoEliminar = new Empleado(Integer.valueOf(idSeleccionado));
		System.out.println("Esta es la información del empleado que vas a eliminar\n"+empleadoEliminar.toString());
		String confirmacion = Utilidades.preguntarString("¿Seguro que quieres eliminarlo? (si o no): ");
		if (confirmacion.equalsIgnoreCase("si")) {
			if(empleadoEliminar.eliminar()) {
				System.out.println("Empleado eliminado correctamente");
			} else {
				System.out.println("Error al eliminar el empleado");
			}
		} else {
			System.out.println("No se eliminará el empleado.");
		}
	}

	private void pedir_datos_empleado_modificar() {
		System.out.println("\nModificar un empleado");
		System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
		String idSeleccionado = pedir_id("Escrbe el id del empleado que quieres modificar: ", "idEmpleado", "empleados", "");
		System.out.println("Deja el campo vacío si no lo qieres cambiar.");
		ArrayList<String> columnas = new ArrayList<String>();
		ArrayList<Object> datos = new ArrayList<Object>();
		Empleado empleadoModificar = new Empleado(Integer.valueOf(idSeleccionado));
		String nombre;
		String cargo;
		String fechaS;
		nombre = Utilidades.preguntarLongitud(45, "Nombre: ", "menos de 45 caracteres");
		if(nombre.length()!=0) {
			columnas.add("Nombre ");
			datos.add(nombre);
		}
		cargo = Utilidades.preguntarCargoEmpleado();
		if (cargo.length()!=0) {
			columnas.add("Cargo ");
			datos.add(cargo);
		}
		fechaS = Utilidades.preguntarRegex("(^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$)|(^$)", "Fecha de ingreso: ", "formato de fecha yyyy-MM-dd");
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		Date fecha;
		try {
			fecha = new Date(formato.parse(fechaS).getTime());
			columnas.add("Fecha_ingreso ");
			datos.add(fecha);
		} catch (ParseException e){
			System.out.println("No se puede modificar la fecha porque no cumple con el formato (yyyy-MM-dd)"); //TODO no mostrar esta frase
			e.printStackTrace();
		}
		if (columnas.size()>0)
			empleadoModificar.modificar(columnas, datos, empleadoModificar.getId());
		else
			System.out.println("No se modificará el empleado.");
	}

	private void pedir_datos_empleado_nuevo() {
		System.out.println("\nAñadir un empleado");
		System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
		String nombre;
		String cargoS;
		Date fecha;
		do {
		nombre = Utilidades.preguntarLongitud(45, "Nombre: ", "menos de 45 caracteres");
		}while(nombre.length()<1);
		do {
		cargoS = Utilidades.preguntarCargoEmpleado();
		} while (!cargoS.equals(""));
		fecha = new Date(System.currentTimeMillis());
		Empleado.registrarNuevoEmpleado(nombre, cargoS, fecha);
		
	}

	private String pedir_id(String pregunta, String nombreId, String tabla, String where) {
		System.out.println("Esta es la lista de todos los "+tabla);
		ResultSet ids = BaseDatos.consulta("SELECT "+nombreId+" FROM "+tabla+" "+where);
		int cantidadIds = 0;
		ArrayList<Integer> idsTotales = new ArrayList<Integer>();
		try {
			while (ids.next()) {
				if(tabla.equals("juguetes"))
					Juguete.mostrarPorId(ids.getInt(1));
				if(tabla.equals("empleados"))
					Empleado.mostrarPorId(ids.getInt(1));
				idsTotales.add(ids.getInt(1));
				cantidadIds++;
			}
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No hay datos para leer");
			e.printStackTrace();
		}
		String idSeleccionado;
		do {
			idSeleccionado = Utilidades.preguntarRegex("^[0-9]+$", pregunta, "un número del 1 a "+cantidadIds);
		} while(!exisite_id(Integer.valueOf(idSeleccionado), idsTotales));
		return idSeleccionado;
	}

	private boolean exisite_id(Integer idComprobar, ArrayList<Integer> idsTotales) {
		boolean existe = false;
		for (Integer id : idsTotales) {
			if(idComprobar == id)
				existe = true;
		}
		return existe;
	}

	public void opcionesVentas(int num) {
		switch (num) {
		case 1:
			pedir_datos_venta();
			break;
		case 2:
			pedir_datos_devolucion();
			break;
		case 3:
			productos_mas_vendidos();
			break;
		case 4:
			empleados_mas_venden();
			break;

		default:
			break;
		}
	}

	private void empleados_mas_venden() {
		Venta.empleadosMasVenden();
		
	}

	private void productos_mas_vendidos() {
		Venta.productosMasVendidos();
		
	}

	private void pedir_datos_devolucion() {
		Venta venta;
		venta.devolver();
		
	}

	private void pedir_datos_venta() {
		Venta venta;
		venta.vender();
		
	}

	public void opcionesDatos(int num) {
		switch (num) {
		case 1:
			juguetes_disponibles_stand();
			break;
		case 2:
			ventas_realizadas_mes();
			break;
		case 3:
			ventas_realizadas_empleado_mes();
			break;
		case 4:
			datos_cambios();
			break;
		case 5:
			productos_precio();
			break;

		default:
			break;
		}
	}

	private void productos_precio() {
		Juguete.mostrarPrecio();
	}

	private void datos_cambios() {
		Empleado empleado;
		Cambio.datosEmpleado(empleado.getId());
	}

	private void ventas_realizadas_empleado_mes() {
		Empleado empleado;
		int mes;
		Venta.ventasMesEmpleado(empleado.getId(), mes);
		
	}

	private void ventas_realizadas_mes() {
		int mes;
		Venta.ventasMes(mes);
	}

	private void juguetes_disponibles_stand() {
		Stand stand;
		Juguete.mostrarPorStand(stand.getId());
	}
	
	/*
	 * TODO 
	 * ● Juguetes: 
	 * ○ Registrar un nuevo juguete 
	 * ○ Modificar los datos de los juguetes 
	 * ○ Eliminar juguetes 
	 * ○ Salir 
	 * ● Empleado 
	 * ○ Registra un nuevo empleado 
	 * ○ Modificar los datos de un nuevo empleado 
	 * ○ Eliminar el empleado 
	 * ○ Salir 
	 * ● Ventas 
	 * ○ Realizar una venta 
	 * ○ Realizar una devolución 
	 * ○ Producto más vendido ( los cincos primeros) 
	 * ○ Los empleados que más venden 
	 * ○ Salir 
	 * ● Obtener datos de la tienda
	 * ○ Obtener todos los juguetes que están disponibles en un stand específicos 
	 * ○ Obtener los datos de las ventas realizadas en un mes 
	 * ○ Obtener los datos de las ventas realizadas por un empleado en un mes. 
	 * ○ Obtener los datos de los cambios de los empleados y el motivo del cambio 
	 * ○ Lista de los productos ordenados por precio
	 * ○ Salir 
	 * ● Salir 
	 */
}
