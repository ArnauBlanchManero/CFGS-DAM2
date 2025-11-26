package JUGUETERIA;

import java.util.ArrayList;
import java.sql.Date;
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
		String idSeleccionado = pedir_id("\nEscribe el id del juguete que quieres eliminar: ", "todos los juguetes disponibles","idJuguete", "juguetes", "WHERE Visible = TRUE");
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
		String idSeleccionado = pedir_id("\nEscrbe el id del juguete que quieres modificar: ", "todos los juguetes","idJuguete", "juguetes", "");
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
		int idJuguete = Juguete.registrarNuevoJuguete(nombre, desc, precio, cant, categoriaS);
		
		System.out.println("¿Dónde quieres colocar el juguete?");
		int idZona;
		int idStand;
		idZona = Integer.valueOf(pedir_id("\nEscribe el id de la zona donde quieras buscar: ", "las zonas","idZona", "zonas", ""));
		idStand = Integer.valueOf(pedir_id("\nEscribe el id del stand donde quieras buscar: ", "los stands de esa zona","idStand", "stands", " WHERE zona_idZona = "+idZona));
		Stock juguete = new Stock(idJuguete, idZona+" "+idStand, cant);
		if(juguete.guardarJuguete())
			System.out.println("Juguete guardado");
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
		String idSeleccionado = pedir_id("\nEscrbe el id del empleado que quieres eliminar: ", "todos los empleados","idEmpleado", "empleados", "");
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
		String idSeleccionado = pedir_id("\nEscrbe el id del empleado que quieres modificar: ", "todos los empleados","idEmpleado", "empleados", "");
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
			//System.out.println("No se puede modificar la fecha porque no cumple con el formato (yyyy-MM-dd)");
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
		} while (cargoS.equals(""));
		fecha = new Date(System.currentTimeMillis());
		Empleado.registrarNuevoEmpleado(nombre, cargoS, fecha);
		
	}

	private String pedir_id(String pregunta, String queMuestra, String nombreId, String tabla, String where) {
		System.out.println("Esta es la lista de "+queMuestra);
		ResultSet ids = BaseDatos.consulta("SELECT "+nombreId+" FROM "+tabla+" "+where);
		ArrayList<Integer> idsTotales = new ArrayList<Integer>();
		try {
			while (ids.next()) {
				if(nombreId.contains("idJuguete"))
					Juguete.mostrarPorId(ids.getInt(1));
				else if(nombreId.contains("idEmpleado"))
					Empleado.mostrarPorId(ids.getInt(1));
				else if(nombreId.contains("idZona"))
					Zona.mostrarPorId(ids.getInt(1));
				else if(nombreId.contains("idStand"))
					Stand.mostrarPorId(ids.getInt(1));
				else if(nombreId.contains("idVenta"))
					Venta.mostrarPorId(ids.getInt(1));
				idsTotales.add(ids.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No hay datos para leer");
			e.printStackTrace();
		}
		String idSeleccionado;
		if(idsTotales.size()!=0) {
			do {
				idSeleccionado = Utilidades.preguntarRegex("^[0-9]+$", pregunta, "un id que se haya mostrado");
			} while(!exisite_id(Integer.valueOf(idSeleccionado), idsTotales));
		} else {
			System.out.println("No se han encontrado datos.");
			return "0";
		}
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
			pedir_datos_devolucion(); // TODO hacer un cambio
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
		System.out.println("\nLos 5 empleados que más venden");
		System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
		Venta.empleadosMasVenden();
		
	}

	private void productos_mas_vendidos() {
		System.out.println("\nLos 5 juguetes más vendidos");
		System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
		Venta.productosMasVendidos();
		
	}

	private void pedir_datos_devolucion() {
		System.out.println("\nDevolver un juguete");
		System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
		Venta venta;
		int idJuguete;
		int idZona;
		int idStand;
		int idVenta = Integer.valueOf(pedir_id("\nEscribe el id de la venta que quieras devolver: ", "las ventas realizadas", "idVenta", "ventas", ""));
		venta = new Venta(idVenta);
		idJuguete = Integer.valueOf(pedir_id("\nEscribe el id del juguete por el que quieras cambiarlo: ", "todos los juguetes","idJuguete", "juguetes", " WHERE Visible = true"));
		idZona = Integer.valueOf(pedir_id("\nEscribe el id de la zona donde se encuentra el juguete: ", "las zonas con ese juguete","stand_zona_idZona", "stocks", " WHERE juguete_idJuguete = "+idJuguete+" GROUP BY stand_zona_idZona"));
		idStand = Integer.valueOf(pedir_id("\nEscribe el id del stand donde se encuentra el juguete: ", "los stands en la zona con ese juguete","stand_idStand", "stocks", " WHERE stand_zona_idZona = "+idZona+" AND juguete_idJuguete = "+idJuguete));
		String idZonaStand = idZona +" "+idStand;
		venta.cambio(venta.getIdStand(), idZonaStand, venta.getIdJuguete(), idJuguete, venta.getIdEmpleado());
		
	}

	private void pedir_datos_venta() {
		System.out.println("\nVender un juguete");
		System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
		Venta venta;
		Date fecha;
		String tipoPago;
		int idJuguete;
		int idEmpleado;
		int idZona;
		int idStand;
		String idZonaStand;
		int cantidad;
		double precioUnitario = 0;
		double monto;
		fecha = new Date(System.currentTimeMillis());
		idJuguete = Integer.valueOf(pedir_id("\nEscribe el id del juguete que quieras vender: ", "todos los juguetes","idJuguete", "juguetes", " WHERE Visible = true"));
		idEmpleado = Integer.valueOf(pedir_id("\nEscribe el id del empleado que está realizando la venta: ", "todos los empleados","idEmpleado", "empleados", ""));
		idZona = Integer.valueOf(pedir_id("\nEscribe el id de la zona donde se encuentra el juguete: ", "las zonas con ese juguete","stand_zona_idZona", "stocks", " WHERE juguete_idJuguete = "+idJuguete+" GROUP BY stand_zona_idZona"));
		idStand = Integer.valueOf(pedir_id("\nEscribe el id del stand donde se encuentra el juguete: ", "los stands en la zona con ese juguete","stand_idStand", "stocks", " WHERE stand_zona_idZona = "+idZona+" and juguete_idJuguete = "+idJuguete));
		if(idStand != 0) {
			idZonaStand = idZona + " " + idStand;
			do {
				cantidad = Integer.valueOf(Utilidades.preguntarRegex("^[0-9]+$", "¿Cuántas unidades quieres vender? ", "un número"));
			} while(!stock_suficiente(idStand, idZona, idJuguete, cantidad));
			ResultSet resultado = BaseDatos.consulta("SELECT precio FROM juguetes WHERE idJuguete = "+idJuguete);
			try {
				if (resultado.next()) {
					precioUnitario = resultado.getDouble(1);
				}
			} catch (SQLException e) {
				System.out.println("Error en la ejecución de la consutla.");
				precioUnitario = 0;
				e.printStackTrace();
			}
			monto = precioUnitario * Double.valueOf(cantidad);
			tipoPago = Utilidades.preguntarTipoPago();
			venta = new Venta(idStand, idEmpleado, idJuguete, idZonaStand, fecha, monto, tipoPago);
			venta.vender(cantidad);
		} else {
			System.out.println("Venta cancelada.");
		}
	}

	private boolean stock_suficiente(int idStand, int idZona, int idJuguete, int cantidad) {
		boolean suficiente = false;
		ResultSet resultado = BaseDatos.consulta("SELECT cantidad FROM stocks WHERE stand_idStand = "+idStand+" AND stand_zona_idZona = "+idZona+" AND juguete_idJuguete = "+idJuguete);
		int stock = 0;
		try {
			if (resultado.next()) {
				stock = resultado.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("Error en la ejecución de la consutla.");
			e.printStackTrace();
		}
		if((stock-cantidad) >= 0) {
			suficiente = true;
		} else {
			System.out.println("No hay tantas unidades, introduce un número menor a "+stock);
		}
		return suficiente;
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
		System.out.println("\nJuguetes ordenados por precio");
		System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
		Juguete.mostrarPrecio();
	}

	private void datos_cambios() {
		System.out.println("\nInformación de los cambios por empleado");
		System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
		int idEmpleado = Integer.valueOf(pedir_id("\nEscribe el id del empleado del que quieras ver sus cambios: ", "todos los empleados","idEmpleado", "empleados", ""));
		Cambio.datosEmpleado(idEmpleado);
	}

	private void ventas_realizadas_empleado_mes() {
		System.out.println("\nInformación de las ventas en un mes por empleado");
		System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
		String mes;
		int idEmpleado = Integer.valueOf(pedir_id("\nEscribe el id del empleado del que quieras ver sus ventas: ", "todos los empleados","idEmpleado", "empleados", ""));
		mes = Utilidades.preguntarRegex("^(0[1-9]|1[0-2])$", "Escribe el mes que quieres buscar en las ventas de ese empleado: ", "el número correspondiente a cada mes con dos dígitos");
		Venta.ventasMesEmpleado(idEmpleado, mes);
		
	}

	private void ventas_realizadas_mes() {
		System.out.println("\nInformación de las ventas en un mes");
		System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
		String mes;
		mes = Utilidades.preguntarRegex("^(0[1-9]|1[0-2])$", "Escribe el mes que quieres buscar en las ventas: ", "el número correspondiente a cada mes con dos dígitos");
		Venta.ventasMes(mes);
	}

	private void juguetes_disponibles_stand() {
		System.out.println("\nJuguetes de un stand");
		System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
		int idZona;
		int idStand;
		idZona = Integer.valueOf(pedir_id("\nEscribe el id de la zona donde quieras buscar: ", "las zonas","idZona", "zonas", ""));
		idStand = Integer.valueOf(pedir_id("\nEscribe el id del stand donde quieras buscar: ", "los stands de esa zona","idStand", "stands", " WHERE zona_idZona = "+idZona));
		Juguete.mostrarPorStand(idZona, idStand);
	}
	
	/*
	 *  
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
