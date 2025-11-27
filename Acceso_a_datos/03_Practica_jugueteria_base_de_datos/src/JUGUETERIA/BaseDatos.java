package JUGUETERIA;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BaseDatos {
	private String nombre;
	private static Connection conexion;

	public BaseDatos(String nombreSchema, String[] argumentos) {
		super();
		this.nombre = nombreSchema;
		conexion = conectar(argumentos[0], argumentos[1], argumentos[2]);
	}

	private Connection conectar(String url, String usuario, String password) {
		Connection conexionTMP = null;
		String[] info = url.split("/");
		String nombreBBDD;
		if (info.length >= 4) {
			nombreBBDD = info[3];
		} else {
			nombreBBDD = nombre;
		}
		try {
			// Carga el drive de la BBDD
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Conectar
			conexionTMP = DriverManager.getConnection(url, usuario, password);
			System.out.println("Conexión realizada a "+nombreBBDD);
		} catch (ClassNotFoundException e) {
			System.out.println("Comprueba que tengas las librerías añadidas");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Conexión fallida a "+nombre);
			String confirmacion = Utilidades.preguntarString("\n¿Quieres crear la base de datos jugueteria? (si o no): ");
			if(confirmacion.equalsIgnoreCase("si")) {
				crearBBDDJugueteria();
				boolean [] tablasDatos = {false, false, true, true, false, false, false};
				importar_datos_por_defecto(tablasDatos);
			} else {
				System.out.println("No se ha creado la base de datos.");
			}
			//e.printStackTrace();
		}
		return conexionTMP;
	}

	public static int consultaModifica(String consulta) {
		try {
			PreparedStatement sentencia;
			sentencia = conexion.prepareStatement(consulta);
			return sentencia.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error al ejecutar la consulta.");
			e.printStackTrace();
			return -1;
		}
	}

	
	public static ResultSet consulta(String consulta) {
		Statement sentencia;
		ResultSet resultado_consulta = null;
		try {
			if(conexion==null) {
				System.out.println("Fatal Error.");
				System.out.println("Cerrando aplicación.");
				System.exit(1);
			}
			else {
				sentencia = conexion.createStatement();
				resultado_consulta = sentencia.executeQuery(consulta);
			}
		} catch (SQLException e) {
			System.out.println("No se ha podido realizar la consulta correctamente.");
			e.printStackTrace();
		}
		return resultado_consulta;
	}

	private void crearBBDDJugueteria() {
		if(conexion == null) {
			this.nombre = "localhost";
			conexion = conectar("jdbc:mysql://localhost:3306", "root", "cfgs");
		}
		if(consultaModifica("CREATE DATABASE IF NOT EXISTS jugueteria DEFAULT CHARACTER SET utf8")!=-1)
			System.out.println("Base de datos jugueteria creada correctamente");
		nombre = "jugueteria";
		conexion = conectar("jdbc:mysql://localhost:3306/jugueteria", "root", "cfgs");
		System.out.println("");
		// Crear tabla EMPLEADO
		if(consultaModifica(
		    "CREATE TABLE IF NOT EXISTS `jugueteria`.`empleados` (" +
		    "`idEmpleado` INT UNSIGNED NOT NULL AUTO_INCREMENT, " +
		    "`Nombre` VARCHAR(45) NOT NULL, " +
		    "`Cargo` ENUM('Jefe', 'Cajero') NOT NULL, " +
		    "`Fecha_ingreso` DATE NOT NULL, " +
		    "PRIMARY KEY (`idEmpleado`)) " +
		    "ENGINE=InnoDB;"
		)!=-1)
			System.out.println("Tabla empleados creada correctamente");
		// Crear tabla JUGUETE
		if(consultaModifica(
		    "CREATE TABLE IF NOT EXISTS `jugueteria`.`juguetes` (" +
		    "`idJuguete` INT NOT NULL AUTO_INCREMENT, " +
		    "`Nombre` VARCHAR(45) NOT NULL, " +
		    "`Descripcion` VARCHAR(150) NOT NULL, " +
		    "`Precio` DOUBLE NOT NULL, " +
		    "`Cantidad_stock` INT UNSIGNED NOT NULL, " +
		    "`Categoria` ENUM('Vehiculos', 'Muñecas', 'Electronicos', 'Libre', 'Accion', 'Mesa', 'Construccion', 'Peluches') NOT NULL, " +
		    "`Visible` BOOLEAN NOT NULL, " +
		    "PRIMARY KEY (`idJuguete`)) " +
		    "ENGINE=InnoDB;"
		)!=-1)
			System.out.println("Tabla juguetes creada correctamente");
	
		// Crear tabla ZONA
		if(consultaModifica(
		    "CREATE TABLE IF NOT EXISTS `jugueteria`.`zonas` (" +
		    "`idZona` INT NOT NULL AUTO_INCREMENT, " +
		    "`Nombre` VARCHAR(45) NOT NULL, " +
		    "`Descripcion` VARCHAR(150) NOT NULL, " +
		    "PRIMARY KEY (`idZona`)) " +
		    "ENGINE=InnoDB;"
		)!=-1)
			System.out.println("Tabla zonas creada correctamente");
	
		// Crear tabla STAND
		if(consultaModifica(
		    "CREATE TABLE IF NOT EXISTS `jugueteria`.`stands` (" +
		    "`idStand` INT NOT NULL AUTO_INCREMENT, " +
		    "`Nombre` VARCHAR(45) NOT NULL, " +
		    "`Descripcion` VARCHAR(150) NOT NULL, " +
		    "`zona_idZona` INT NOT NULL, " +
		    "PRIMARY KEY (`idStand`, `zona_idZona`), " +
		    "INDEX `fk_STAND_zona_idx` (`zona_idZona` ASC), " +
		    "CONSTRAINT `fk_STAND_zona` FOREIGN KEY (`zona_idZona`) REFERENCES `jugueteria`.`zonas`(`idZona`) " +
		    "ON DELETE CASCADE ON UPDATE CASCADE) " +
		    "ENGINE=InnoDB;"
		)!=-1)
			System.out.println("Tabla stands creada correctamente");
	
		// Crear tabla VENTA
		if(consultaModifica(
		    "CREATE TABLE IF NOT EXISTS `jugueteria`.`ventas` (" +
		    "`idVenta` INT NOT NULL AUTO_INCREMENT, " +
		    "`Fecha` DATE NOT NULL, " +
		    "`Monto` DOUBLE NOT NULL, " +
		    "`tipo_pago` ENUM('Efectivo', 'Tarjeta', 'Paypal') NOT NULL, " +
		    "`juguete_idJuguete` INT NOT NULL, " +
		    "`empleado_idEmpleado` INT UNSIGNED NULL, " +
		    "`STAND_idStand` INT NOT NULL, " +
		    "`STAND_zona_idZona` INT NOT NULL, " +
		    "PRIMARY KEY (`idVenta`), " +
		    "INDEX `fk_venta_juguete1_idx` (`juguete_idJuguete` ASC), " +
		    "INDEX `fk_venta_empleado1_idx` (`empleado_idEmpleado` ASC), " +
		    "INDEX `fk_venta_STAND1_idx` (`STAND_idStand` ASC, `STAND_zona_idZona` ASC), " +
		    "CONSTRAINT `fk_venta_juguete1` FOREIGN KEY (`juguete_idJuguete`) REFERENCES `jugueteria`.`juguetes`(`idJuguete`) ON DELETE RESTRICT ON UPDATE CASCADE, " +
		    "CONSTRAINT `fk_venta_empleado1` FOREIGN KEY (`empleado_idEmpleado`) REFERENCES `jugueteria`.`empleados`(`idEmpleado`) ON DELETE SET NULL ON UPDATE CASCADE, " +
		    "CONSTRAINT `fk_venta_STAND1` FOREIGN KEY (`STAND_idStand`, `STAND_zona_idZona`) REFERENCES `jugueteria`.`stands`(`idStand`, `zona_idZona`) ON DELETE RESTRICT ON UPDATE CASCADE) " +
		    "ENGINE=InnoDB;"
		)!=-1)
			System.out.println("Tabla ventas creada correctamente");
	
		// Crear tabla STOCK
		if(consultaModifica(
		    "CREATE TABLE IF NOT EXISTS `jugueteria`.`stocks` (" +
		    "`stand_idStand` INT NOT NULL, " +
		    "`stand_zona_idZona` INT NOT NULL, " +
		    "`juguete_idJuguete` INT NOT NULL, " +
		    "`cantidad` INT NOT NULL, " +
		    "PRIMARY KEY (`stand_idStand`, `stand_zona_idZona`, `juguete_idJuguete`), " +
		    "INDEX `fk_stand_has_juguete_juguete1_idx` (`juguete_idJuguete` ASC), " +
		    "INDEX `fk_stand_has_juguete_stand1_idx` (`stand_idStand` ASC, `stand_zona_idZona` ASC), " +
		    "CONSTRAINT `fk_stand_has_juguete_stand1` FOREIGN KEY (`stand_idStand`, `stand_zona_idZona`) REFERENCES `jugueteria`.`stands`(`idStand`, `zona_idZona`) ON DELETE RESTRICT ON UPDATE CASCADE, " +
		    "CONSTRAINT `fk_stand_has_juguete_juguete1` FOREIGN KEY (`juguete_idJuguete`) REFERENCES `jugueteria`.`juguetes`(`idJuguete`) ON DELETE RESTRICT ON UPDATE CASCADE) " +
		    "ENGINE=InnoDB;"
		)!=-1)
			System.out.println("Tabla stock creada correctamente");
	
		// Crear tabla CAMBIO
		if(consultaModifica(
			"CREATE TABLE IF NOT EXISTS `jugueteria`.`cambios` (" +
			"`idCambio` INT NOT NULL AUTO_INCREMENT, " +
			"`MOTIVO` VARCHAR(150) NOT NULL, " +
			"`Fecha` DATE NOT NULL, " +
			"`stock_stand_idStand_Original` INT NOT NULL, " +
			"`stock_stand_zona_idZona_Original` INT NOT NULL, " +
			"`stock_juguete_idJuguete_Original` INT NOT NULL, " +
			"`stock_stand_idStand_Nuevo` INT NOT NULL, " +
			"`stock_stand_zona_idZona_Nuevo` INT NOT NULL, " +
			"`stock_juguete_idJuguete_Nuevo` INT NOT NULL, " +
			"`empleado_idEmpleado` INT UNSIGNED NULL, " +
			"PRIMARY KEY (`idCambio`), " +
			"INDEX `fk_cambio_stock1_idx` (`stock_stand_idStand_Original`, `stock_stand_zona_idZona_Original`, `stock_juguete_idJuguete_Original` ASC), " +
			"INDEX `fk_cambio_stock2_idx` (`stock_stand_idStand_Nuevo`, `stock_stand_zona_idZona_Nuevo`, `stock_juguete_idJuguete_Nuevo` ASC), " +
			"INDEX `fk_cambio_empleado1_idx` (`empleado_idEmpleado` ASC), " +
			"CONSTRAINT `fk_cambio_stock1` FOREIGN KEY (`stock_stand_idStand_Original`, `stock_stand_zona_idZona_Original`, `stock_juguete_idJuguete_Original`) " +
			"REFERENCES `jugueteria`.`stocks`(`stand_idStand`, `stand_zona_idZona`, `juguete_idJuguete`) ON DELETE RESTRICT ON UPDATE CASCADE, " +
			"CONSTRAINT `fk_cambio_stock2` FOREIGN KEY (`stock_stand_idStand_Nuevo`, `stock_stand_zona_idZona_Nuevo`, `stock_juguete_idJuguete_Nuevo`) " +
			"REFERENCES `jugueteria`.`stocks`(`stand_idStand`, `stand_zona_idZona`, `juguete_idJuguete`) ON DELETE RESTRICT ON UPDATE CASCADE, " +
			"CONSTRAINT `fk_cambio_empleado1` FOREIGN KEY (`empleado_idEmpleado`) REFERENCES `jugueteria`.`empleados`(`idEmpleado`) ON DELETE SET NULL ON UPDATE CASCADE) " +
			"ENGINE=InnoDB;"

		)!=-1)
			System.out.println("Tabla cambios creada correctamente");
	}

	public void añadir_datos_por_defecto() {
		boolean [] tablasDatos = {true, true, true, true, true, true, true};
		ResultSet empleadoVacia = consulta("SELECT * FROM empleados");
		ResultSet jugueteVacia = consulta("SELECT * FROM juguetes WHERE Visible = TRUE");
		ResultSet zonaVacia = consulta("SELECT * FROM zonas");
		ResultSet standVacia = consulta("SELECT * FROM stands");
		ResultSet ventaVacia = consulta("SELECT * FROM ventas");
		ResultSet stockVacia = consulta("SELECT * FROM stocks");
		ResultSet cambioVacia = consulta("SELECT * FROM cambios");
		
		try {
		    if (!empleadoVacia.next()) 
		        tablasDatos[0] = false;
		    if (!jugueteVacia.next()) 
		        tablasDatos[1] = false;
		    if (!zonaVacia.next()) 
		        tablasDatos[2] = false;
		    if (!standVacia.next()) 
		        tablasDatos[3] = false;
		    if (!ventaVacia.next()) 
		        tablasDatos[4] = false;
		    if (!stockVacia.next()) 
		        tablasDatos[5] = false;
		    if (!cambioVacia.next()) 
		        tablasDatos[6] = false;
		} catch (NullPointerException e) {
			System.out.println("Algo falla en la estructura de la Base de Datos");
			crearBBDDJugueteria();
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			e.printStackTrace();
		}
		
		String confirmacion = Utilidades.preguntarString("\n¿Quieres añadir los datos por defecto? (si o no): ");
		if(confirmacion.equalsIgnoreCase("si")) {
			importar_datos_por_defecto(tablasDatos);
		} else {
			System.out.println("No se añadirán datos.");
		}		
	}

	public void importar_datos_por_defecto(boolean[] tablasDatos) {

		// INSERT EMPLEADOS
		if(tablasDatos[0])
			insertar_empleados();

		// INSERT JUGUETES
		if(tablasDatos[1])
			insertar_juguetes();

		// INSERT ZONAS
		if(tablasDatos[2])
			insertar_zonas();

		// INSERT STANDS
		if(tablasDatos[3])
			insertar_stands();

		// INSERT STOCKS
		if(tablasDatos[4])
			insertar_stocks();

		// INSERT VENTAS
		if(tablasDatos[5])
			insertar_ventas();

		// INSERT CAMBIOS
		if(tablasDatos[6])
			insertar_cambios();
	}

	public void insertar_cambios() {
		if(consultaModifica(
			"INSERT INTO `jugueteria`.`cambios` (`idCambio`, `MOTIVO`, `Fecha`, `stock_stand_idStand_Original`, `stock_stand_zona_idZona_Original`, `stock_juguete_idJuguete_Original`, `stock_stand_idStand_Nuevo`, `stock_stand_zona_idZona_Nuevo`, `stock_juguete_idJuguete_Nuevo`, `empleado_idEmpleado`) VALUES" +
			"(1, 'Reubicación por demanda', '2024-02-01', 5, 3, 1, 7, 4, 10, 1)," +
			"(2, 'Optimización de espacio', '2024-02-02', 3, 2, 4, 4, 2, 6, 4)," +
			"(3, 'Actualización de inventario', '2024-02-03', 2, 1, 2, 5, 3, 7, 2)," +
			"(4, 'Producto mal ubicado', '2024-02-04', 4, 2, 6, 1, 1, 9, 3)," +
			"(5, 'Cambio de categoría', '2024-02-05', 4, 2, 6, 9, 5, 5, 5);"

		)!=-1)
		    System.out.println("Datos insertados en CAMBIOS");
	}

	public void insertar_ventas() {
		if(consultaModifica(
		    "INSERT INTO `jugueteria`.`ventas` (`idVenta`, `Fecha`, `Monto`, `tipo_pago`, `juguete_idJuguete`, `empleado_idEmpleado`, `STAND_idStand`, `STAND_zona_idZona`) VALUES" +
		    "(1, '2024-01-10', 12.99, 'efectivo', 1, 2, 5, 3)," +
		    "(2, '2024-01-11', 35.00, 'tarjeta', 3, 3, 1, 1)," +
		    "(3, '2024-01-12', 18.50, 'paypal', 2, 5, 2, 1)," +
		    "(4, '2024-01-13', 27.40, 'efectivo', 6, 6, 4, 2)," +
		    "(5, '2024-01-14', 49.99, 'tarjeta', 9, 1, 1, 1)," +
		    "(6, '2024-01-15', 21.10, 'efectivo', 8, 4, 2, 1)," +
		    "(7, '2024-01-16', 29.90, 'paypal', 10, 7, 7, 4)," +
		    "(8, '2024-01-17', 14.99, 'efectivo', 7, 8, 5, 3)," +
		    "(9, '2024-01-18', 15.75, 'efectivo', 4, 9, 3, 2)," +
		    "(10, '2024-01-19', 22.90, 'tarjeta', 5, 10, 9, 5);"
		)!=-1)
		    System.out.println("Datos insertados en VENTAS");
	}

	public void insertar_stands() {
		if(consultaModifica(
		    "INSERT INTO `jugueteria`.`stands` (`idStand`, `Nombre`, `Descripcion`, `zona_idZona`) VALUES" +
		    "(1, 'Stand A', 'Juguetes premium', 1)," +
		    "(2, 'Stand B', 'Juguetes electrónicos', 1)," +
		    "(3, 'Stand A', 'Muñecas clásicas y modernas', 2)," +
		    "(4, 'Stand B', 'Peluche suaves', 2)," +
		    "(5, 'Stand A', 'Juegos de mesa familiares', 3)," +
		    "(6, 'Stand B', 'Juegos de mesa en pareja', 3)," +
		    "(7, 'Stand A', 'Autos y helicópteros', 4)," +
		    "(8, 'Stand B', 'Vehículos RC', 4)," +
		    "(9, 'Stand A', 'Coleccionables', 5)," +
		    "(10, 'Stand B', 'Juegos variados', 5)," +
		    "(11, 'Stand C', 'Figuras de acción', 5);"
		)!=-1)
		    System.out.println("Datos insertados en STANDS");
	}

	public void insertar_stocks() {
		if(consultaModifica(
		    "INSERT INTO `jugueteria`.`stocks` (`stand_idStand`, `stand_zona_idZona`, `juguete_idJuguete`, `cantidad`) VALUES" +
		    "(1, 1, 3, 10)," +
		    "(1, 1, 9, 6)," +
		    "(2, 1, 2, 12)," +
		    "(2, 1, 8, 8)," +
		    "(3, 2, 4, 15)," +
		    "(3, 2, 7, 5)," +
		    "(4, 2, 6, 17)," +
		    "(4, 2, 5, 9)," +
		    "(5, 3, 1, 20)," +
		    "(5, 3, 7, 11)," +
		    "(6, 3, 1, 18)," +
		    "(6, 3, 7, 4)," +
		    "(6, 3, 4, 1)," + 
		    "(7, 4, 10, 7)," +
		    "(8, 4, 5, 29)," +
		    "(9, 5, 5, 21)," +
		    "(10, 5, 5, 16)," +
		    "(11, 5, 5, 13);"
		)!=-1)
		    System.out.println("Datos insertados en STOCKS");
	}

	public void insertar_zonas() {
		if(consultaModifica(
		    "INSERT INTO `jugueteria`.`zonas` (`idZona`, `Nombre`, `Descripcion`) VALUES" +
		    "(1, 'Zona A', 'Sector principal de juguetes electrónicos')," +
		    "(2, 'Zona B', 'Sector de muñecas y peluches')," +
		    "(3, 'Zona C', 'Sector de juegos de mesa')," +
		    "(4, 'Zona D', 'Sector de vehículos')," +
		    "(5, 'Zona E', 'Sector misceláneo');"
		)!=-1)
		    System.out.println("Datos insertados en ZONAS");
	}

	public void insertar_juguetes() {
		if (consultaModifica(
			    "INSERT INTO `jugueteria`.`juguetes` (`idJuguete`, `Nombre`, `Descripcion`, `Precio`, `Cantidad_stock`, `Categoria`, `Visible`) VALUES" +
					    "(1, 'Auto de Carrera', 'Auto a fricción rojo', 12.99, 50, 'Vehículos', TRUE)," +
					    "(2, 'Muñeca Clara', 'Muñeca articulada con vestido', 18.50, 30, 'Muñecas', TRUE)," +
					    "(3, 'Robot Tekno', 'Robot con luces y sonidos', 35.00, 20, 'Electronicos', TRUE)," +
					    "(4, 'Peluche Oso', 'Oso de peluche marrón', 15.75, 40, 'Peluches', TRUE)," +
					    "(5, 'Set de Construcción MiniBlocks', 'Caja con 150 piezas', 22.90, 25, 'Construccion', TRUE)," +
					    "(6, 'Tablero de Ajedrez', 'Ajedrez de madera', 27.40, 15, 'Mesa', TRUE)," +
					    "(7, 'Helicóptero Militar', 'Helicóptero verde con hélice giratoria', 14.99, 35, 'Vehículos', TRUE)," +
					    "(8, 'Muñeca Sofi', 'Muñeca con accesorios', 21.10, 20, 'Muñecas', TRUE)," +
					    "(9, 'Consola MiniPlay', 'Consola portátil retro', 49.99, 12, 'Electronicos', TRUE)," +
					    "(10, 'Set de Superhéroes', 'Figuras articuladas', 29.90, 18, 'Accion', TRUE);"
			)!=-1)
		    System.out.println("Datos insertados en JUGUETES");

	}

	public void insertar_empleados() {
		if(consultaModifica(
		    "INSERT INTO `jugueteria`.`empleados` (`idEmpleado`, `Nombre`, `Cargo`, `Fecha_ingreso`) VALUES" +
    		"(1, 'Javier Morales', 'jefe', '2018-08-03')," +
    		"(2, 'Carlos Ruiz', 'jefe', '2019-11-20')," +
		    "(3, 'María López', 'jefe', '2020-03-15')," +
		    "(4, 'Héctor Silva', 'cajero', '2020-10-28')," +
		    "(5, 'Sofía Martínez', 'cajero', '2021-04-30')," +
		    "(6, 'Juan Pérez', 'cajero', '2021-06-10')," +
		    "(7, 'Ana Torres', 'cajero', '2022-01-05')," +
		    "(8, 'Diego Fernández', 'cajero', '2022-09-25')," +
		    "(9, 'Lucía Gómez', 'cajero', '2023-02-12')," +
		    "(10, 'Paula Ramírez', 'cajero', '2023-05-14');"
			)!=-1)
			    System.out.println("Datos insertados en EMPLEADOS");		
	}

	public static int modificaSeguro(ArrayList<Object> parametros, String consulta) {
		try {
			PreparedStatement sentencia;
			sentencia = conexion.prepareStatement(consulta);
			for (int i = 0; i < parametros.size(); i++) {
				sentencia.setObject(i+1, parametros.get(i));
			}
			return sentencia.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

}
