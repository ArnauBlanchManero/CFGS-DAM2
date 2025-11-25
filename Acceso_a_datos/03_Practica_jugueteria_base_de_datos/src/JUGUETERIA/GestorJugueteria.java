package JUGUETERIA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GestorJugueteria {

	public static void main(String[] args) {
		System.out.println("  ☺  ʕ•ᴥ•ʔ  [°_°]    ☺  ʕ•ᴥ•ʔ  [°_°]  ");
		System.out.println(" /|\\ ( • ) /|___|\\  /|\\ ( • ) /|___|\\ ");
		System.out.println(" / \\ () ()   | |    / \\ () ()   | |   ");
		System.out.println("BIENVENIDO AL GESTOR DE LA JUGUETERIA\n");
		BaseDatos jugueteriaBBDD = conectarseBBDD();
		crear_objetosBBDD(jugueteriaBBDD);
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
		ResultSet datosEmpleados= BaseDatos.consulta("SELECT * FROM empleados");
		try {
			if(datosEmpleados.next()) {
				System.out.println("Datos de los empleados recolectados.");
			} else {
				jugueteriaBBDD.añadir_datos_por_defecto();
			}
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			jugueteriaBBDD.añadir_datos_por_defecto();
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No hay datos para leer");
			e.printStackTrace();
		}
		
		ResultSet datosJuguetes = BaseDatos.consulta("SELECT * FROM juguetes");
		try {
			if(datosJuguetes.next()) {
				System.out.println("Datos de los juguetes recolectados.");
			} else {
				jugueteriaBBDD.añadir_datos_por_defecto();
			}
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			jugueteriaBBDD.añadir_datos_por_defecto();
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No hay datos para leer");
			e.printStackTrace();
		}
		
		ResultSet datosZonas = BaseDatos.consulta("SELECT * FROM zonas");
		try {
			if(datosZonas.next()) {
				System.out.println("Datos de las zonas recolectados.");
			} else {
				jugueteriaBBDD.añadir_datos_por_defecto();
			}
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			jugueteriaBBDD.añadir_datos_por_defecto();
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No hay datos para leer");
			e.printStackTrace();
		}
		
		ResultSet datosStands = BaseDatos.consulta("SELECT * FROM stands");
		try {
			if(datosStands.next()) {
				System.out.println("Datos de los stands recolectados.");
			} else {
				jugueteriaBBDD.añadir_datos_por_defecto();
			}
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			jugueteriaBBDD.añadir_datos_por_defecto();
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No hay datos para leer");
			e.printStackTrace();
		}
		
		ResultSet datosVentas = BaseDatos.consulta("SELECT * FROM ventas");
		try {
			if(datosVentas.next()) {
				System.out.println("Datos de las ventas recolectados.");
			} else {
				jugueteriaBBDD.añadir_datos_por_defecto();
			}
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			jugueteriaBBDD.añadir_datos_por_defecto();
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No hay datos para leer");
			e.printStackTrace();
		}
		
		ResultSet datosStocks = BaseDatos.consulta("SELECT * FROM stocks");
		try {
			if(datosStocks.next()) {
				System.out.println("Datos de los stocks recolectados.");
			} else {
				jugueteriaBBDD.añadir_datos_por_defecto();
			}
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			jugueteriaBBDD.añadir_datos_por_defecto();
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No hay datos para leer");
			e.printStackTrace();
		}
		
		ResultSet datosCambios = BaseDatos.consulta("SELECT * FROM cambios");
		try {
			if(datosCambios.next()) {
				System.out.println("Datos de los cambios recolectados.");
			} else {
				jugueteriaBBDD.añadir_datos_por_defecto();
			}
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
