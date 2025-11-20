package JUGUETERIA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

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
		
		System.out.println("\nAplicación cerrada.");
	}

	private static void crear_objetosBBDD(BaseDatos jugueteriaBBDD) {
		System.out.println("\nLeyendo datos de la base de datos...\n");
		ResultSet datosEmpleados= jugueteriaBBDD.consulta("SELECT * FROM EMPLEADO");
		try {
			while(datosEmpleados.next()) {
				Empleado empleadoTMP;
				int id = datosEmpleados.getInt(1);
				String nombre = datosEmpleados.getString(2);
				String cargo = datosEmpleados.getString(3);
				Date fecha = datosEmpleados.getDate(4);
				CargoEmpleado cargoE;
				if (cargo.equals("cajero")) {
					cargoE = CargoEmpleado.CAJERO;
				} else if (cargo.equals("jefe")) {
					cargoE = CargoEmpleado.JEFE;
				} else {
					cargoE = null;
				}
				empleadoTMP = new Empleado(id, nombre, cargoE, fecha);
				System.out.println(empleadoTMP.toString());
				empleados.add(empleadoTMP);
			}
			System.out.println("Datos de los empleados recolectados.\n");
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			e.printStackTrace();
		}
		
		ResultSet datosJuguetes = jugueteriaBBDD.consulta("SELECT * FROM JUGUETE");
		try {
			while(datosJuguetes.next()) {
				Juguete jugueteTMP;
				int id = datosJuguetes.getInt(1);
				String nombre = datosJuguetes.getString(2);
				String descripcion = datosJuguetes.getString(3);
				Double precio = datosJuguetes.getDouble(4);
				int cantidad = datosJuguetes.getInt(5);
				
				jugueteTMP = new Juguete(id, nombre, descripcion, precio, cantidad);
				System.out.println(jugueteTMP.toString());
				juguetes.add(jugueteTMP);
			}
			System.out.println("Datos de los juguetes recolectados.\n");
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			e.printStackTrace();
		}
		
		ResultSet datosZonas = jugueteriaBBDD.consulta("SELECT * FROM ZONA");
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
			System.out.println("Datos de las zonas recolectados.\n");
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			e.printStackTrace();
		}
		
		ResultSet datosStands = jugueteriaBBDD.consulta("SELECT * FROM STAND");
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
			System.out.println("Datos de las Stands recolectados.\n");
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			e.printStackTrace();
		}
		
		ResultSet datosVentas = jugueteriaBBDD.consulta("SELECT * FROM VENTA");
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
			System.out.println("Datos de las ventas recolectados.\n");
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			e.printStackTrace();
		}
		
		ResultSet datosStocks = jugueteriaBBDD.consulta("SELECT * FROM STOCK");
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
			System.out.println("Datos de los stocks recolectados.\n");
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			e.printStackTrace();
		}
		
		ResultSet datosCambios = jugueteriaBBDD.consulta("SELECT * FROM CAMBIO");
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
			System.out.println("Datos de los cambios recolectados.\n");
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			e.printStackTrace();
		}
		
	}

	private static BaseDatos conectarseBBDD() {
		String[] datosConectar = {"jdbc:mysql://localhost:3306/Jugueteria", "root", "cfgs"};
		BaseDatos jugueteriaBBDD = new BaseDatos("Jugueteria", datosConectar);
		jugueteriaBBDD.añadir_datos_por_defecto();
		return jugueteriaBBDD;
	}

}
