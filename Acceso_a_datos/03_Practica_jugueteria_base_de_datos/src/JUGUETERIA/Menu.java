package JUGUETERIA;

import java.util.ArrayList;
import java.util.Date;

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
		int numRespuesta;
		String regex = "^[1-"+(opciones.size()+1)+"]$";
		numRespuesta = Utilidades.preguntarIntRegex(regex, "Opcion: ", especificacion);
		return numRespuesta;
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
		Juguete jugueteEliminar;
		jugueteEliminar.eliminar();
		
	}

	private void pedir_datos_juguete_modificar() {
		Juguete jugueteModificar;
		String nombre;
		String desc;
		double precio;
		int cant;
		jugueteModificar.modificar(nombre, desc, precio, cant);
	}

	private void pedir_datos_juguete_nuevo() {
		System.out.println("Por favor, rellena los siguientes campos");
		String nombre;
		String desc;
		double precio;
		int cant;
		String categoriaS;
		nombre = Utilidades.preguntarLongitud(45, "Nombre: ", "menos de 45 caracteres");
		desc = Utilidades.preguntarLongitud(150, "Descripción: ", "menos de 150 caracteres");
		precio = Utilidades.preguntarDouble("Precio: ", "un simple número con decimales");
		cant = Utilidades.preguntarIntRegex("^[0-9]*$", "Cantidad: ", "un numero entero");
		categoriaS = Utilidades.preguntarCategoriaJuguete();
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
		Empleado empleadoEliminar;
		empleadoEliminar.eliminar();
	}

	private void pedir_datos_empleado_modificar() {
		Empleado empleadoModificar;
		String nombre;
		CargoEmpleado cargo;
		Date fecha;
		empleadoModificar.modificar(nombre, cargo, fecha);
	}

	private void pedir_datos_empleado_nuevo() {
		String nombre;
		CargoEmpleado cargo;
		Date fecha;
		Empleado.registrarNuevoEmpleado(nombre, cargo, fecha);
		
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
		Juguete.mostarPrecio();
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
		Juguete.mostrar(stand.getId());
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
