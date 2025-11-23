package JUGUETERIA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public class GestorJugueteria {

	private static ArrayList<Empleado> empleados = new ArrayList<Empleado>();
	private static ArrayList<Juguete> juguetes = new ArrayList<Juguete>();
	private static ArrayList<Zona> zonas = new ArrayList<Zona>();
	private static ArrayList<Stand> stands = new ArrayList<Stand>();
	private static ArrayList<Venta> ventas = new ArrayList<Venta>();
	private static ArrayList<Stock> stocks = new ArrayList<Stock>();
	private static ArrayList<Cambio> cambios = new ArrayList<Cambio>();
	
	public static void main(String[] args) {
		System.out.println("  ☺  ʕ•ᴥ•ʔ  [°_°]    ☺  ʕ•ᴥ•ʔ  [°_°]  ");
		System.out.println(" /|\\ ( • ) /|___|\\  /|\\ ( • ) /|___|\\ ");
		System.out.println(" / \\ () ()   | |    / \\ () ()   | |   ");
		System.out.println("BIENVENIDO AL GESTOR DE LA JUGUETERIA\n");
		// TODO conectarse a la bbdd
		BaseDatos jugueteriaBBDD = conectarseBBDD();
		// TODO crear variables esenciales
		crear_objetosBBDD(jugueteriaBBDD);
		// TODO delegar el trabajo a los menus
		ArrayList<String> opcionesMenuGeneral = new ArrayList<String>();
		opcionesMenuGeneral.add("Gestionar Juguetes");
		opcionesMenuGeneral.add("Gestionar Empleados");
		opcionesMenuGeneral.add("Gestionar Ventas");
		opcionesMenuGeneral.add("Obtener datos de la tienda");
		Menu menuGeneral = new Menu(opcionesMenuGeneral, 1, "Menú general");
		int numRespuesta;
		while((numRespuesta = menuGeneral.respuesta("escribe un número del 1 al 5")) !=5) {
			subMenus(numRespuesta);
		}
		System.out.println("\nAplicación cerrada.");
	}

	private static void subMenus(int numRespuesta) {
		System.out.println(numRespuesta);

		ArrayList<String> opcionesMenuJuguetes = new ArrayList<String>();
		opcionesMenuJuguetes.add("Registrar un nuevo juguete");
		opcionesMenuJuguetes.add("Modificar los datos de los juguetes");
		opcionesMenuJuguetes.add("Eliminar juguetes");
		Menu menuJuguetes = new Menu(opcionesMenuJuguetes, 2, "Menú Juguetes");
		ArrayList<String> opcionesMenuEmpleados = new ArrayList<String>();
		opcionesMenuEmpleados.add("Registra un nuevo empleado");
		opcionesMenuEmpleados.add("Modificar los datos de un nuevo empleado");
		opcionesMenuEmpleados.add("Eliminar el empleado");
		Menu menuEmpleados = new Menu(opcionesMenuEmpleados, 2, "Menú Empleados");
		ArrayList<String> opcionesMenuVentas = new ArrayList<String>();
		opcionesMenuVentas.add("Realizar una venta");
		opcionesMenuVentas.add("Realizar una devolución");
		opcionesMenuVentas.add("Producto más vendido");
		opcionesMenuVentas.add("Los empleados que más venden");
		Menu menuVentas = new Menu(opcionesMenuVentas, 2, "Menú Ventas");
		ArrayList<String> opcionesMenuDatos = new ArrayList<String>();
		opcionesMenuDatos.add("Obtener todos los juguetes que están disponibles en un stand específicos");
		opcionesMenuDatos.add("Obtener los datos de las ventas realizadas en un mes");
		opcionesMenuDatos.add("Obtener los datos de las ventas realizadas por un empleado en un mes");
		opcionesMenuDatos.add("Obtener los datos de los cambios de los empleados y el motivo del cambio");
		opcionesMenuDatos.add("Lista de los productos ordenados por precio");
		Menu menuDatos = new Menu(opcionesMenuDatos, 2, "Menú Datos");
		
		int numRespuesta2;
		switch (numRespuesta) {
		case 1:
			while((numRespuesta2 = menuJuguetes.respuesta("escribe un número del 1 al 4")) !=4) {
				menuJuguetes.opcionesJuguetes(numRespuesta2);
			}
			break;
		case 2:
			while((numRespuesta2 = menuEmpleados.respuesta("escribe un número del 1 al 4")) !=4) {
				menuEmpleados.opcionesEmpleados(numRespuesta2);
			}
			break;
		case 3:
			while((numRespuesta2 = menuVentas.respuesta("escribe un número del 1 al 5")) !=5) {
				menuVentas.opcionesVentas(numRespuesta2);
			}
			break;
		case 4:
			while((numRespuesta2 = menuDatos.respuesta("escribe un número del 1 al 6")) !=6) {
				menuDatos.opcionesDatos(numRespuesta2);
			}
			break;

		default:
			break;
		}
	}

	private static void crear_objetosBBDD(BaseDatos jugueteriaBBDD) {
		System.out.println("\nLeyendo datos de la base de datos...\n");
		ResultSet datosEmpleados= jugueteriaBBDD.consulta("SELECT * FROM empleados");
		try {
			while(datosEmpleados.next()) {
				Empleado empleadoTMP;
				int id = datosEmpleados.getInt(1);
				String nombre = datosEmpleados.getString(2);
				String cargo = datosEmpleados.getString(3);
				Date fecha = datosEmpleados.getDate(4);
				empleadoTMP = new Empleado(id, nombre, cargo, fecha);
				System.out.println(empleadoTMP.toString());
				empleados.add(empleadoTMP);
			}
			System.out.println("Datos de los empleados recolectados.");
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			jugueteriaBBDD.añadir_datos_por_defecto();
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No hay datos para leer");
			e.printStackTrace();
		}
		
		ResultSet datosJuguetes = jugueteriaBBDD.consulta("SELECT * FROM juguetes");
		try {
			while(datosJuguetes.next()) {
				Juguete jugueteTMP;
				int id = datosJuguetes.getInt(1);
				String nombre = datosJuguetes.getString(2);
				String descripcion = datosJuguetes.getString(3);
				Double precio = datosJuguetes.getDouble(4);
				int cantidad = datosJuguetes.getInt(5);
				String categoria = datosJuguetes.getString(6);
				
				jugueteTMP = new Juguete(id, nombre, descripcion, precio, cantidad, categoria);
				System.out.println(jugueteTMP.toString());
				juguetes.add(jugueteTMP);
			}
			System.out.println("Datos de los juguetes recolectados.");
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			jugueteriaBBDD.añadir_datos_por_defecto();
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No hay datos para leer");
			e.printStackTrace();
		}
		
		ResultSet datosZonas = jugueteriaBBDD.consulta("SELECT * FROM zonas");
		try {
			while(datosZonas.next()) {
				Zona zonaTMP;
				int id = datosZonas.getInt(1);
				String nombre = datosZonas.getString(2);
				String descripcion = datosZonas.getString(3);
				zonaTMP = new Zona(id, nombre, descripcion);
				System.out.println(zonaTMP.toString());
				zonas.add(zonaTMP);
			}
			System.out.println("Datos de las zonas recolectados.");
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			jugueteriaBBDD.añadir_datos_por_defecto();
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No hay datos para leer");
			e.printStackTrace();
		}
		
		ResultSet datosStands = jugueteriaBBDD.consulta("SELECT * FROM stands");
		try {
			while(datosStands.next()) {
				Stand standTMP;
				int idStand = datosStands.getInt(1);
				String nombre = datosStands.getString(2);
				String descripcion = datosStands.getString(3);
				int idZona = datosStands.getInt(4);
				standTMP = new Stand(idStand, idZona, nombre, descripcion);
				System.out.println(standTMP.toString());
				stands.add(standTMP);
			}
			System.out.println("Datos de las stands recolectados.");
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			jugueteriaBBDD.añadir_datos_por_defecto();
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No hay datos para leer");
			e.printStackTrace();
		}
		
		ResultSet datosVentas = jugueteriaBBDD.consulta("SELECT * FROM ventas");
		try {
			while(datosVentas.next()) {
				Venta ventaTMP;
				int idVenta = datosVentas.getInt(1);
				Date fecha = datosVentas.getDate(2);
				double monto = datosVentas.getDouble(3);
				String tipoPagoS = datosVentas.getString(4);
				int idJuguete = datosVentas.getInt(5);
				int idEmpleado = datosVentas.getInt(6);
				int idStand = datosVentas.getInt(7);
				int idZona = datosVentas.getInt(8);
				TipoPago tipoPago;
				if (tipoPagoS.equals("efectivo")) {
					tipoPago = TipoPago.EFECTIVO;
				} else if (tipoPagoS.equals("tarjeta")) {
					tipoPago = TipoPago.TARJETA;
				} else if (tipoPagoS.equals("paypal")) {
					tipoPago = TipoPago.PAYPAL;
				} else {
					tipoPago = null;
				}
				String idS = idZona +" "+idStand;
				ventaTMP = new Venta(idVenta, idEmpleado, idJuguete, idS, fecha, monto, tipoPago);
				System.out.println(ventaTMP.toString());
				ventas.add(ventaTMP);
			}
			System.out.println("Datos de las ventas recolectados.");
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			jugueteriaBBDD.añadir_datos_por_defecto();
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No hay datos para leer");
			e.printStackTrace();
		}
		
		ResultSet datosStocks = jugueteriaBBDD.consulta("SELECT * FROM stocks");
		try {
			while(datosStocks.next()) {
				Stock stockTMP;
				int idStand = datosStocks.getInt(1);
				int idZona = datosStocks.getInt(2);
				int idJuguete = datosStocks.getInt(3);
				int cantidad = datosStocks.getInt(4);
				String idS = idZona +" "+idStand;
				stockTMP = new Stock(idJuguete, idS, cantidad);
				System.out.println(stockTMP.toString());
				stocks.add(stockTMP);
			}
			System.out.println("Datos de los stocks recolectados.");
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			jugueteriaBBDD.añadir_datos_por_defecto();
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No hay datos para leer");
			e.printStackTrace();
		}
		
		ResultSet datosCambios = jugueteriaBBDD.consulta("SELECT * FROM cambios");
		try {
			while(datosCambios.next()) {
				Cambio cambioTMP;
				int idCambio = datosCambios.getInt(1);
				String motivo = datosCambios.getString(2);
				Date fecha = datosCambios.getDate(3);
				int idStandOriginal = datosCambios.getInt(4);
				int idZonaOriginal = datosCambios.getInt(5);
				int idJugueteOriginal = datosCambios.getInt(6);
				int idStandNuevo = datosCambios.getInt(7);
				int idZonaNuevo = datosCambios.getInt(8);
				int idJugueteNuevo = datosCambios.getInt(9);
				int idEmpleado = datosCambios.getInt(10);
				String idSOrigen = idZonaOriginal +" "+idStandOriginal;
				String idSDestino = idZonaNuevo +" "+idStandNuevo;
				cambioTMP = new Cambio(idCambio, idEmpleado, idJugueteOriginal, idJugueteNuevo, motivo, fecha, idSOrigen, idSDestino);
				System.out.println(cambioTMP.toString());
				cambios.add(cambioTMP);
			}
			System.out.println("Datos de los cambios recolectados.");
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			jugueteriaBBDD.añadir_datos_por_defecto();
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No hay datos para leer");
			e.printStackTrace();
		}
		
	}

	private static BaseDatos conectarseBBDD() {
		String[] datosConectar = {"jdbc:mysql://localhost:3306/jugueteria", "root", "cfgs"};
		BaseDatos jugueteriaBBDD = new BaseDatos("jugueteria", datosConectar);
		return jugueteriaBBDD;
	}

}
