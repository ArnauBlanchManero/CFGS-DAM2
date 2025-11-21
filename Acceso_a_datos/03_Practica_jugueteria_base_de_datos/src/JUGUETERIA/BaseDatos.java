package JUGUETERIA;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDatos {
	private String nombre;
	private Connection conexion;

	public BaseDatos(String nombreSchema, String[] argumentos) {
		super();
		this.nombre = nombreSchema;
		conexion = conectar(argumentos[0], argumentos[1], argumentos[2]);
	}

	private Connection conectar(String url, String usuario, String password) {
		Connection conexionTMP = null;
		String[] info = url.split("/");
		String nombreBBDD = "";
		if (info.length >= 4) {
			nombreBBDD = "a "+info[3];
		}
		try {
			// Carga el drive de la BBDD
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Conectar
			conexionTMP = DriverManager.getConnection(url, usuario, password);
			System.out.println("Conexión realizada "+nombreBBDD);
		} catch (ClassNotFoundException e) {
			System.out.println("Comprueba que tengas las librerías añadidas");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Conexión fallida "+nombreBBDD);
			crearBBDDJugueteria();
			//e.printStackTrace();
		}
		return conexionTMP;
	}

	public int consultaModifica(String consulta) {
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

	// TODO consultasComplejas(Array String)
	
	public ResultSet consulta(String consulta) {
		Statement sentencia;
		ResultSet resultado_consulta = null;
		try {
			if(conexion==null)
				System.out.println("Reinicia la aplicacion");
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
		if(conexion == null)
			conexion = conectar("jdbc:mysql://localhost:3306", "root", "cfgs");
		if(consultaModifica("CREATE DATABASE IF NOT EXISTS jugueteria DEFAULT CHARACTER SET utf8")!=-1)
			System.out.println("Base de datos Juguetería creada correctamente");
		conexion = conectar("jdbc:mysql://localhost:3306/Jugueteria", "root", "cfgs");
		// Crear tabla EMPLEADO
		if(consultaModifica(
		    "CREATE TABLE IF NOT EXISTS `jugueteria`.`empleados` (" +
		    "`idEmpleado` INT UNSIGNED NOT NULL AUTO_INCREMENT, " +
		    "`Nombre` VARCHAR(45) NULL, " +
		    "`Cargo` ENUM('jefe', 'cajero') NULL, " +
		    "`Fecha_ingreso` DATE NULL, " +
		    "PRIMARY KEY (`idEmpleado`)) " +
		    "ENGINE=InnoDB;"
		)!=-1)
			System.out.println("Tabla Empleado creada correctamente");
		// Crear tabla JUGUETE
		if(consultaModifica(
		    "CREATE TABLE IF NOT EXISTS `jugueteria`.`juguetes` (" +
		    "`idJuguete` INT NOT NULL AUTO_INCREMENT, " +
		    "`Nombre` VARCHAR(45) NULL, " +
		    "`Descripcion` VARCHAR(150) NULL, " +
		    "`Precio` DOUBLE NULL, " +
		    "`Cantidad_stock` INT UNSIGNED NOT NULL, " +
		    "`Categoria` ENUM('Vehículos', 'Muñecas', 'Electronicos', 'Libre', 'Accion', 'Mesa', 'Construccion', 'Peluches') NULL, " +
		    "`Visible` BOOLEAN NULL, " +
		    "PRIMARY KEY (`idJuguete`)) " +
		    "ENGINE=InnoDB;"
		)!=-1)
			System.out.println("Tabla Juguete creada correctamente");
	
		// Crear tabla ZONA
		if(consultaModifica(
		    "CREATE TABLE IF NOT EXISTS `jugueteria`.`zonas` (" +
		    "`idZona` INT NOT NULL AUTO_INCREMENT, " +
		    "`Nombre` VARCHAR(45) NULL, " +
		    "`Descripcion` VARCHAR(150) NULL, " +
		    "PRIMARY KEY (`idZona`)) " +
		    "ENGINE=InnoDB;"
		)!=-1)
			System.out.println("Tabla Zona creada correctamente");
	
		// Crear tabla STAND
		if(consultaModifica(
		    "CREATE TABLE IF NOT EXISTS `jugueteria`.`stands` (" +
		    "`idStand` INT NOT NULL AUTO_INCREMENT, " +
		    "`Nombre` VARCHAR(45) NULL, " +
		    "`Descripcion` VARCHAR(150) NULL, " +
		    "`zona_idZona` INT NOT NULL, " +
		    "PRIMARY KEY (`idStand`, `zona_idZona`), " +
		    "INDEX `fk_STAND_zona_idx` (`zona_idZona` ASC), " +
		    "CONSTRAINT `fk_STAND_zona` FOREIGN KEY (`zona_idZona`) REFERENCES `jugueteria`.`zonas`(`idZona`) " +
		    "ON DELETE CASCADE ON UPDATE CASCADE) " +
		    "ENGINE=InnoDB;"
		)!=-1)
			System.out.println("Tabla Stand creada correctamente");
	
		// Crear tabla VENTA
		if(consultaModifica(
		    "CREATE TABLE IF NOT EXISTS `jugueteria`.`ventas` (" +
		    "`idVenta` INT NOT NULL AUTO_INCREMENT, " +
		    "`Fecha` DATE NULL, " +
		    "`Monto` DOUBLE NULL, " +
		    "`tipo_pago` ENUM('efectivo', 'tarjeta', 'paypal') NULL, " +
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
			System.out.println("Tabla Venta creada correctamente");
	
		// Crear tabla STOCK
		if(consultaModifica(
		    "CREATE TABLE IF NOT EXISTS `jugueteria`.`stocks` (" +
		    "`stand_idStand` INT NOT NULL, " +
		    "`stand_zona_idZona` INT NOT NULL, " +
		    "`juguete_idJuguete` INT NOT NULL, " +
		    "`CANTIDAD` VARCHAR(45) NULL, " +
		    "PRIMARY KEY (`stand_idStand`, `stand_zona_idZona`, `juguete_idJuguete`), " +
		    "INDEX `fk_stand_has_juguete_juguete1_idx` (`juguete_idJuguete` ASC), " +
		    "INDEX `fk_stand_has_juguete_stand1_idx` (`stand_idStand` ASC, `stand_zona_idZona` ASC), " +
		    "CONSTRAINT `fk_stand_has_juguete_stand1` FOREIGN KEY (`stand_idStand`, `stand_zona_idZona`) REFERENCES `jugueteria`.`stands`(`idStand`, `zona_idZona`) ON DELETE RESTRICT ON UPDATE CASCADE, " +
		    "CONSTRAINT `fk_stand_has_juguete_juguete1` FOREIGN KEY (`juguete_idJuguete`) REFERENCES `jugueteria`.`juguetes`(`idJuguete`) ON DELETE RESTRICT ON UPDATE CASCADE) " +
		    "ENGINE=InnoDB;"
		)!=-1)
			System.out.println("Tabla Stock creada correctamente");
	
		// Crear tabla CAMBIO
		if(consultaModifica(
		    "CREATE TABLE IF NOT EXISTS `jugueteria`.`cambios` (" +
		    "`idCambio` INT NOT NULL AUTO_INCREMENT, " +
		    "`MOTIVO` VARCHAR(150) NULL, " +
		    "`Fecha` DATE NULL, " +
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
			System.out.println("Tabla Cambio creada correctamente");
	}

	public void añadir_datos_por_defecto() {
		boolean [] tablasDatos = {true, true, true, true, true, true, true};
		ResultSet empleadoVacia = consulta("SELECT * FROM empleados");
		ResultSet jugueteVacia = consulta("SELECT * FROM juguetes");
		ResultSet zonaVacia = consulta("SELECT * FROM zonas");
		ResultSet standVacia = consulta("SELECT * FROM stands");
		ResultSet ventaVacia = consulta("SELECT * FROM ventas");
		ResultSet stockVacia = consulta("SELECT * FROM stocks");
		ResultSet cambioVacia = consulta("SELECT * FROM cambios");
		
		try {
			if(empleadoVacia.getFetchSize()==0)
				tablasDatos[0] = false;
			if(jugueteVacia.getFetchSize()==0)
				tablasDatos[1] = false;
			if(zonaVacia.getFetchSize()==0)
				tablasDatos[2] = false;
			if(standVacia.getFetchSize()==0)
				tablasDatos[3] = false;
			if(ventaVacia.getFetchSize()==0)
				tablasDatos[4] = false;
			if(stockVacia.getFetchSize()==0)
				tablasDatos[5] = false;
			if(cambioVacia.getFetchSize()==0)
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
		if(consultaModifica(
		    "INSERT INTO `jugueteria`.`empleados` (`Nombre`, `Cargo`, `Fecha_ingreso`) VALUES" +
		    "('María López', 'jefe', '2020-03-15')," +
		    "('Juan Pérez', 'cajero', '2021-06-10')," +
		    "('Ana Torres', 'cajero', '2022-01-05')," +
		    "('Carlos Ruiz', 'jefe', '2019-11-20')," +
		    "('Lucía Gómez', 'cajero', '2023-02-12')," +
		    "('Diego Fernández', 'cajero', '2022-09-25')," +
		    "('Sofía Martínez', 'cajero', '2021-04-30')," +
		    "('Javier Morales', 'jefe', '2018-08-03')," +
		    "('Paula Ramírez', 'cajero', '2023-05-14')," +
		    "('Héctor Silva', 'cajero', '2020-10-28');"
		)!=-1)
		    System.out.println("Datos insertados en EMPLEADOS");
		else
			System.out.println("Ya había datos en la tabla EMPLEADOS.");

		// INSERT JUGUETES
		if(consultaModifica(
		    "INSERT INTO `jugueteria`.`juguetes` (`Nombre`, `Descripcion`, `Precio`, `Cantidad_stock`, `Categoria`, `Visible`) VALUES" +
		    "('Auto de Carrera', 'Auto a fricción rojo', 12.99, 50, 'Vehículos', TRUE)," +
		    "('Muñeca Clara', 'Muñeca articulada con vestido', 18.50, 30, 'Muñecas', TRUE)," +
		    "('Robot Tekno', 'Robot con luces y sonidos', 35.00, 20, 'Electronicos', TRUE)," +
		    "('Peluche Oso', 'Oso de peluche marrón', 15.75, 40, 'Peluches', TRUE)," +
		    "('Set de Construcción MiniBlocks', 'Caja con 150 piezas', 22.90, 25, 'Construccion', TRUE)," +
		    "('Tablero de Ajedrez', 'Ajedrez de madera', 27.40, 15, 'Mesa', TRUE)," +
		    "('Helicóptero Militar', 'Helicóptero verde con hélice giratoria', 14.99, 35, 'Vehículos', TRUE)," +
		    "('Muñeca Sofi', 'Muñeca con accesorios', 21.10, 20, 'Muñecas', TRUE)," +
		    "('Consola MiniPlay', 'Consola portátil retro', 49.99, 12, 'Electronicos', TRUE)," +
		    "('Set de Superhéroes', 'Figuras articuladas', 29.90, 18, 'Accion', TRUE);"
		)!=-1)
		    System.out.println("Datos insertados en JUGUETES");
		else
			System.out.println("Ya había datos en la tabla JUGUETES.");

		// INSERT ZONAS
		if(consultaModifica(
		    "INSERT INTO `jugueteria`.`zonas` (`Nombre`, `Descripcion`) VALUES" +
		    "('Zona A', 'Sector principal de juguetes electrónicos')," +
		    "('Zona B', 'Sector de muñecas y peluches')," +
		    "('Zona C', 'Sector de juegos de mesa')," +
		    "('Zona D', 'Sector de vehículos')," +
		    "('Zona E', 'Sector misceláneo');"
		)!=-1)
		    System.out.println("Datos insertados en ZONAS");
		else
			System.out.println("Ya había datos en la tabla ZONAS.");

		// INSERT STANDS
		if(consultaModifica(
		    "INSERT INTO `jugueteria`.`stands` (`Nombre`, `Descripcion`, `zona_idZona`) VALUES" +
		    "('Stand 1', 'Juguetes electrónicos premium', 1)," +
		    "('Stand 2', 'Muñecas clásicas y modernas', 2)," +
		    "('Stand 3', 'Peluche suaves', 2)," +
		    "('Stand 4', 'Juegos de mesa familiares', 3)," +
		    "('Stand 5', 'Autos y helicópteros', 4)," +
		    "('Stand 6', 'Vehículos RC', 4)," +
		    "('Stand 7', 'Juegos variados', 5)," +
		    "('Stand 8', 'Coleccionables', 5)," +
		    "('Stand 9', 'Figuras de acción', 5);"
		)!=-1)
		    System.out.println("Datos insertados en STANDS");
		else
			System.out.println("Ya había datos en la tabla STANDS.");

		// INSERT STOCKS
		if(consultaModifica(
		    "INSERT INTO `jugueteria`.`stocks` (`stand_idStand`, `stand_zona_idZona`, `juguete_idJuguete`, `CANTIDAD`) VALUES" +
		    "(1, 1, 3, '10')," +
		    "(1, 1, 9, '6')," +
		    "(2, 2, 2, '12')," +
		    "(2, 2, 8, '8')," +
		    "(3, 2, 4, '15')," +
		    "(4, 3, 6, '5')," +
		    "(4, 3, 10, '7')," +
		    "(5, 4, 1, '20')," +
		    "(5, 4, 7, '10')," +
		    "(6, 4, 1, '8')," +
		    "(7, 5, 5, '9')," +
		    "(8, 5, 10, '4')," +
		    "(9, 5, 10, '3');"
		)!=-1)
		    System.out.println("Datos insertados en STOCKS");
		else
			System.out.println("Ya había datos en la tabla STOCKS.");

		// INSERT VENTAS
		if(consultaModifica(
		    "INSERT INTO `jugueteria`.`ventas` (`Fecha`, `Monto`, `tipo_pago`, `juguete_idJuguete`, `empleado_idEmpleado`, `STAND_idStand`, `STAND_zona_idZona`) VALUES" +
		    "('2024-01-10', 12.99, 'efectivo', 1, 2, 5, 4)," +
		    "('2024-01-11', 35.00, 'tarjeta', 3, 3, 1, 1)," +
		    "('2024-01-12', 18.50, 'paypal', 2, 5, 2, 2)," +
		    "('2024-01-13', 27.40, 'efectivo', 6, 6, 4, 3)," +
		    "('2024-01-14', 49.99, 'tarjeta', 9, 1, 1, 1)," +
		    "('2024-01-15', 21.10, 'efectivo', 8, 4, 2, 2)," +
		    "('2024-01-16', 29.90, 'paypal', 10, 7, 9, 5)," +
		    "('2024-01-17', 14.99, 'efectivo', 7, 8, 5, 4)," +
		    "('2024-01-18', 15.75, 'efectivo', 4, 9, 3, 2)," +
		    "('2024-01-19', 22.90, 'tarjeta', 5, 10, 7, 5);"
		)!=-1)
		    System.out.println("Datos insertados en VENTAS");
		else
			System.out.println("Ya había datos en la tabla VENTAS.");

		// INSERT CAMBIOS
		if(consultaModifica(
		    "INSERT INTO `jugueteria`.`cambios` (`MOTIVO`, `Fecha`, `stock_stand_idStand_Original`, `stock_stand_zona_idZona_Original`, `stock_juguete_idJuguete_Original`, `stock_stand_idStand_Nuevo`, `stock_stand_zona_idZona_Nuevo`, `stock_juguete_idJuguete_Nuevo`, `empleado_idEmpleado`) VALUES" +
		    "('Reubicación por demanda', '2024-02-01', 5, 4, 1, 6, 4, 1, 1)," +
		    "('Optimización de espacio', '2024-02-02', 3, 2, 4, 5, 4, 4, 4)," +
		    "('Actualización de inventario', '2024-02-03', 2, 2, 2, 7, 5, 2, 2)," +
		    "('Producto mal ubicado', '2024-02-04', 4, 3, 6, 1, 1, 6, 3)," +
		    "('Cambio de categoría', '2024-02-05', 7, 5, 5, 8, 5, 5, 5);"
		)!=-1)
		    System.out.println("Datos insertados en CAMBIOS");
		else
			System.out.println("Ya había datos en la tabla CAMBIOS.");		
	}

}
