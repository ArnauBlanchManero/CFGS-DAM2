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
		ResultSet datosZonas = jugueteriaBBDD.consulta("SELECT * FROM ZONA");
		ResultSet datosStands = jugueteriaBBDD.consulta("SELECT * FROM STAND");
		ResultSet datosVentas = jugueteriaBBDD.consulta("SELECT * FROM VENTA");
		ResultSet datosEmpleadoStocks = jugueteriaBBDD.consulta("SELECT * FROM STOCK");
		ResultSet datosCambios = jugueteriaBBDD.consulta("SELECT * FROM CAMBIO");
		
	}

	private static BaseDatos conectarseBBDD() {
		String[] datosConectar = {"jdbc:mysql://localhost:3306/Jugueteria", "root", "cfgs"};
		BaseDatos jugueteriaBBDD = new BaseDatos("Jugueteria", datosConectar);
		jugueteriaBBDD.añadir_datos_por_defecto();
		return jugueteriaBBDD;
	}

}
