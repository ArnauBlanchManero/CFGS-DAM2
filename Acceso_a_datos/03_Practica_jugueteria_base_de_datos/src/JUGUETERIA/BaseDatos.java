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
		if(consultaModifica("CREATE DATABASE IF NOT EXISTS Jugueteria DEFAULT CHARACTER SET utf8")!=-1)
			System.out.println("Base de datos Juguetería creada correctamente");
		conexion = conectar("jdbc:mysql://localhost:3306/Jugueteria", "root", "cfgs");
		// Crear tabla EMPLEADO
		if(consultaModifica(
		    "CREATE TABLE IF NOT EXISTS `Jugueteria`.`EMPLEADO` (" +
		    "`idEMPLEADO` INT UNSIGNED NOT NULL AUTO_INCREMENT, " +
		    "`Nombre` VARCHAR(45) NULL, " +
		    "`Cargo` ENUM('jefe', 'cajero') NULL, " +
		    "`Fecha_ingreso` DATE NULL, " +
		    "PRIMARY KEY (`idEMPLEADO`)) " +
		    "ENGINE=InnoDB;"
		)!=-1)
			System.out.println("Tabla Empleado creada correctamente");
	
		// Crear tabla JUGUETE
		if(consultaModifica(
		    "CREATE TABLE IF NOT EXISTS `Jugueteria`.`JUGUETE` (" +
		    "`idJuguete` INT NOT NULL, " +
		    "`Nombre` VARCHAR(45) NULL, " +
		    "`Descripcion` VARCHAR(150) NULL, " +
		    "`Precio` DOUBLE NULL, " +
		    "`Cantidad_stock` INT UNSIGNED NOT NULL, " +
		    "PRIMARY KEY (`idJuguete`)) " +
		    "ENGINE=InnoDB;"
		)!=-1)
			System.out.println("Tabla Juguete creada correctamente");
	
		// Crear tabla ZONA
		if(consultaModifica(
		    "CREATE TABLE IF NOT EXISTS `Jugueteria`.`ZONA` (" +
		    "`idzona` INT NOT NULL, " +
		    "`Nombre` VARCHAR(45) NULL, " +
		    "`Descripcion` VARCHAR(150) NULL, " +
		    "PRIMARY KEY (`idzona`)) " +
		    "ENGINE=InnoDB;"
		)!=-1)
			System.out.println("Tabla Zona creada correctamente");
	
		// Crear tabla STAND
		if(consultaModifica(
		    "CREATE TABLE IF NOT EXISTS `Jugueteria`.`STAND` (" +
		    "`idStand` INT NOT NULL, " +
		    "`Nombre` VARCHAR(45) NULL, " +
		    "`Descripcion` VARCHAR(150) NULL, " +
		    "`ZONA_idzona` INT NOT NULL, " +
		    "PRIMARY KEY (`idStand`, `ZONA_idzona`), " +
		    "INDEX `fk_STAND_ZONA_idx` (`ZONA_idzona` ASC), " +
		    "CONSTRAINT `fk_STAND_ZONA` FOREIGN KEY (`ZONA_idzona`) REFERENCES `Jugueteria`.`ZONA`(`idzona`) " +
		    "ON DELETE NO ACTION ON UPDATE NO ACTION) " +
		    "ENGINE=InnoDB;"
		)!=-1)
			System.out.println("Tabla Stand creada correctamente");
	
		// Crear tabla VENTA
		if(consultaModifica(
		    "CREATE TABLE IF NOT EXISTS `Jugueteria`.`VENTA` (" +
		    "`idventa` INT NOT NULL, " +
		    "`Fecha` DATE NULL, " +
		    "`Monto` DOUBLE NULL, " +
		    "`tipo_pago` ENUM('efectivo', 'tarjeta', 'paypal') NULL, " +
		    "`JUGUETE_idJuguete` INT NOT NULL, " +
		    "`EMPLEADO_idEMPLEADO` INT UNSIGNED NOT NULL, " +
		    "`STAND_idStand` INT NOT NULL, " +
		    "`STAND_ZONA_idzona` INT NOT NULL, " +
		    "PRIMARY KEY (`idventa`), " +
		    "INDEX `fk_VENTA_JUGUETE1_idx` (`JUGUETE_idJuguete` ASC), " +
		    "INDEX `fk_VENTA_EMPLEADO1_idx` (`EMPLEADO_idEMPLEADO` ASC), " +
		    "INDEX `fk_VENTA_STAND1_idx` (`STAND_idStand` ASC, `STAND_ZONA_idzona` ASC), " +
		    "CONSTRAINT `fk_VENTA_JUGUETE1` FOREIGN KEY (`JUGUETE_idJuguete`) REFERENCES `Jugueteria`.`JUGUETE`(`idJuguete`) ON DELETE NO ACTION ON UPDATE NO ACTION, " +
		    "CONSTRAINT `fk_VENTA_EMPLEADO1` FOREIGN KEY (`EMPLEADO_idEMPLEADO`) REFERENCES `Jugueteria`.`EMPLEADO`(`idEMPLEADO`) ON DELETE NO ACTION ON UPDATE NO ACTION, " +
		    "CONSTRAINT `fk_VENTA_STAND1` FOREIGN KEY (`STAND_idStand`, `STAND_ZONA_idzona`) REFERENCES `Jugueteria`.`STAND`(`idStand`, `ZONA_idzona`) ON DELETE NO ACTION ON UPDATE NO ACTION) " +
		    "ENGINE=InnoDB;"
		)!=-1)
			System.out.println("Tabla Venta creada correctamente");
	
		// Crear tabla STOCK
		if(consultaModifica(
		    "CREATE TABLE IF NOT EXISTS `Jugueteria`.`STOCK` (" +
		    "`STAND_idStand` INT NOT NULL, " +
		    "`STAND_ZONA_idzona` INT NOT NULL, " +
		    "`JUGUETE_idJuguete` INT NOT NULL, " +
		    "`CANTIDAD` VARCHAR(45) NULL, " +
		    "PRIMARY KEY (`STAND_idStand`, `STAND_ZONA_idzona`, `JUGUETE_idJuguete`), " +
		    "INDEX `fk_STAND_has_JUGUETE_JUGUETE1_idx` (`JUGUETE_idJuguete` ASC), " +
		    "INDEX `fk_STAND_has_JUGUETE_STAND1_idx` (`STAND_idStand` ASC, `STAND_ZONA_idzona` ASC), " +
		    "CONSTRAINT `fk_STAND_has_JUGUETE_STAND1` FOREIGN KEY (`STAND_idStand`, `STAND_ZONA_idzona`) REFERENCES `Jugueteria`.`STAND`(`idStand`, `ZONA_idzona`) ON DELETE NO ACTION ON UPDATE NO ACTION, " +
		    "CONSTRAINT `fk_STAND_has_JUGUETE_JUGUETE1` FOREIGN KEY (`JUGUETE_idJuguete`) REFERENCES `Jugueteria`.`JUGUETE`(`idJuguete`) ON DELETE NO ACTION ON UPDATE NO ACTION) " +
		    "ENGINE=InnoDB;"
		)!=-1)
			System.out.println("Tabla Stock creada correctamente");
	
		// Crear tabla CAMBIO
		if(consultaModifica(
		    "CREATE TABLE IF NOT EXISTS `Jugueteria`.`CAMBIO` (" +
		    "`idCAMBIO` INT NOT NULL, " +
		    "`MOTIVO` VARCHAR(150) NULL, " +
		    "`Fecha` DATE NULL, " +
		    "`STOCK_STAND_idStand_Original` INT NOT NULL, " +
		    "`STOCK_STAND_ZONA_idzona_Original` INT NOT NULL, " +
		    "`STOCK_JUGUETE_idJuguete_Original` INT NOT NULL, " +
		    "`STOCK_STAND_idStand_Nuevo` INT NOT NULL, " +
		    "`STOCK_STAND_ZONA_idzona_Nuevo` INT NOT NULL, " +
		    "`STOCK_JUGUETE_idJuguete_Nuevo` INT NOT NULL, " +
		    "`EMPLEADO_idEMPLEADO` INT UNSIGNED NOT NULL, " +
		    "PRIMARY KEY (`idCAMBIO`), " +
		    "INDEX `fk_CAMBIO_STOCK1_idx` (`STOCK_STAND_idStand_Original`, `STOCK_STAND_ZONA_idzona_Original`, `STOCK_JUGUETE_idJuguete_Original` ASC), " +
		    "INDEX `fk_CAMBIO_STOCK2_idx` (`STOCK_STAND_idStand_Nuevo`, `STOCK_STAND_ZONA_idzona_Nuevo`, `STOCK_JUGUETE_idJuguete_Nuevo` ASC), " +
		    "INDEX `fk_CAMBIO_EMPLEADO1_idx` (`EMPLEADO_idEMPLEADO` ASC), " +
		    "CONSTRAINT `fk_CAMBIO_STOCK1` FOREIGN KEY (`STOCK_STAND_idStand_Original`, `STOCK_STAND_ZONA_idzona_Original`, `STOCK_JUGUETE_idJuguete_Original`) " +
		    "REFERENCES `Jugueteria`.`STOCK`(`STAND_idStand`, `STAND_ZONA_idzona`, `JUGUETE_idJuguete`) ON DELETE NO ACTION ON UPDATE NO ACTION, " +
		    "CONSTRAINT `fk_CAMBIO_STOCK2` FOREIGN KEY (`STOCK_STAND_idStand_Nuevo`, `STOCK_STAND_ZONA_idzona_Nuevo`, `STOCK_JUGUETE_idJuguete_Nuevo`) " +
		    "REFERENCES `Jugueteria`.`STOCK`(`STAND_idStand`, `STAND_ZONA_idzona`, `JUGUETE_idJuguete`) ON DELETE NO ACTION ON UPDATE NO ACTION, " +
		    "CONSTRAINT `fk_CAMBIO_EMPLEADO1` FOREIGN KEY (`EMPLEADO_idEMPLEADO`) REFERENCES `Jugueteria`.`EMPLEADO`(`idEMPLEADO`) ON DELETE NO ACTION ON UPDATE NO ACTION) " +
		    "ENGINE=InnoDB;"
		)!=-1)
			System.out.println("Tabla Cambio creada correctamente");
	}

	public void añadir_datos_por_defecto() {
		boolean [] tablasDatos = {true, true, true, true, true, true, true};
		ResultSet empleadoVacia = consulta("SELECT * FROM EMPLEADO");
		ResultSet jugueteVacia = consulta("SELECT * FROM JUGUETE");
		ResultSet zonaVacia = consulta("SELECT * FROM ZONA");
		ResultSet standVacia = consulta("SELECT * FROM STAND");
		ResultSet ventaVacia = consulta("SELECT * FROM VENTA");
		ResultSet stockVacia = consulta("SELECT * FROM STOCK");
		ResultSet cambioVacia = consulta("SELECT * FROM CAMBIO");
		
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

		// ====================== INSERTS TABLA EMPLEADO ======================
		if(tablasDatos[0] && consultaModifica(
				"INSERT INTO `Jugueteria`.`EMPLEADO` (Nombre, Cargo, Fecha_ingreso) VALUES " +
				"('Luis Ramírez', 'cajero', '2021-03-12')," +
				"('María Gómez', 'jefe', '2019-07-02')," +
				"('Carlos Peña', 'cajero', '2020-11-25')," +
				"('Ana Morales', 'cajero', '2022-01-08')," +
				"('Pedro Téllez', 'jefe', '2018-04-14')," +
				"('Claudia Ríos', 'cajero', '2021-09-19')," +
				"('Sofía Blanco', 'cajero', '2023-02-10')," +
				"('Javier Lozano', 'jefe', '2017-12-03')," +
				"('Daniel Herrera', 'cajero', '2020-05-29')," +
				"('Elena Castro', 'cajero', '2022-06-17');"
		)!=-1)
			System.out.println("Datos añadidos a la tabla Empleado correctamente.");
		else
			System.out.println("Ya había datos en la tabla Empleado.");


		// ====================== INSERTS TABLA JUGUETE ======================
		if(tablasDatos[1] && consultaModifica(
				"INSERT INTO `Jugueteria`.`JUGUETE` VALUES " +
				"(101, 'Auto Rojo', 'Carrito metálico a fricción', 9.99, 50)," +
				"(205, 'Muñeca Luna', 'Muñeca de tela suave', 14.50, 20)," +
				"(309, 'Robot Max', 'Robot con luces y sonidos', 39.90, 10)," +
				"(112, 'Pelota Futbol', 'Pelota infantil N°3', 7.40, 35)," +
				"(410, 'Set Dinosaurios', '4 piezas de plástico resistente', 12.99, 25)," +
				"(178, 'Rompecabezas 100p', 'Rompecabezas infantil', 5.99, 40)," +
				"(256, 'Bloques Mega', 'Caja de 50 bloques encastrables', 18.20, 15)," +
				"(333, 'Pistola Agua', 'Pistola ligera para exteriores', 6.80, 60)," +
				"(490, 'Tren Mini', 'Trencito de madera', 22.50, 12)," +
				"(521, 'Puzzle Animales', 'Puzzle de animales del bosque', 11.30, 30);"
		)!=-1)
			System.out.println("Datos añadidos a la tabla Juguete correctamente.");
		else
			System.out.println("Ya había datos en la tabla Juguete.");


		// ====================== INSERTS TABLA ZONA ======================
		if(tablasDatos[2] && consultaModifica(
				"INSERT INTO `Jugueteria`.`ZONA` VALUES " +
				"(1, 'Infantil', 'Zona para niños de 3 a 6 años')," +
				"(2, 'Educativa', 'Juguetes didácticos y educativos')," +
				"(3, 'Aire Libre', 'Juguetes para exteriores')," +
				"(4, 'Coleccionables', 'Figuras y juguetes de colección')," +
				"(5, 'Bebés', 'Juguetes para menores de 3 años');"
		)!=-1)
			System.out.println("Datos añadidos a la tabla Zona correctamente.");
		else
			System.out.println("Ya había datos en la tabla Zona.");


		// ====================== INSERTS TABLA STAND ======================
		if(tablasDatos[3] && consultaModifica(
				"INSERT INTO `Jugueteria`.`STAND` VALUES " +
				"(10, 'Stand A1', 'Stand principal infantil', 1)," +
				"(15, 'Stand B2', 'Material educativo básico', 2)," +
				"(22, 'Stand C1', 'Productos para exteriores', 3)," +
				"(30, 'Stand D4', 'Figuras premium', 4)," +
				"(18, 'Stand B3', 'Zona para bebés', 5)," +
				"(27, 'Stand C2', 'Juguetes de aire libre secundarios', 3)," +
				"(11, 'Stand A2', 'Soporte infantil secundario', 1)," +
				"(31, 'Stand D5', 'Coleccionables edición limitada', 4);"
		)!=-1)
			System.out.println("Datos añadidos a la tabla Stand correctamente.");
		else
			System.out.println("Ya había datos en la tabla Stand.");


		// ====================== INSERTS TABLA VENTA ======================
		if(tablasDatos[4] && consultaModifica(
				"INSERT INTO `Jugueteria`.`STOCK` VALUES " +
				"(10,1,101,'15')," +
				"(10,1,205,'5')," +
				"(11,1,112,'12')," +
				"(11,1,178,'20')," +
				"(15,2,256,'7')," +
				"(15,2,309,'3')," +
				"(18,5,333,'18')," +
				"(22,3,410,'10')," +
				"(27,3,490,'6')," +
				"(31,4,521,'8');"
		)!=-1)
			System.out.println("Datos añadidos a la tabla Venta correctamente.");
		else
			System.out.println("Ya había datos en la tabla Venta.");


		// ====================== INSERTS TABLA STOCK ======================
		if(tablasDatos[5] && consultaModifica(
				"INSERT INTO `Jugueteria`.`VENTA` VALUES " +
				"(9001,'2023-01-12',19.98,'efectivo',101,1,10,1)," +
				"(9005,'2023-02-05',14.50,'tarjeta',205,4,11,1)," +
				"(9009,'2023-03-22',39.90,'paypal',309,6,15,2)," +
				"(9012,'2023-04-01',7.40,'efectivo',112,3,22,3)," +
				"(9020,'2023-05-14',12.99,'tarjeta',410,2,30,4)," +
				"(9023,'2023-05-30',5.99,'efectivo',178,10,18,5)," +
				"(9031,'2023-07-18',22.50,'tarjeta',490,7,27,3)," +
				"(9035,'2023-08-09',11.30,'paypal',521,5,31,4)," +
				"(9044,'2023-09-25',6.80,'efectivo',333,9,22,3)," +
				"(9050,'2023-10-11',18.20,'tarjeta',256,8,15,2);"
		)!=-1)
			System.out.println("Datos añadidos a la tabla Stock correctamente.");
		else
			System.out.println("Ya había datos en la tabla Stock.");


		// ====================== INSERTS TABLA CAMBIO ======================
		if(tablasDatos[6] && consultaModifica(
				"INSERT INTO `Jugueteria`.`CAMBIO` VALUES " +
				"(5001,'Daño en empaque','2023-02-15',10,1,101,11,1,112,1)," +
				"(5002,'Cambio por color','2023-03-10',11,1,178,10,1,205,3)," +
				"(5003,'Producto defectuoso','2023-04-20',22,3,410,27,3,490,4)," +
				"(5004,'Intercambio por modelo','2023-05-05',15,2,256,15,2,309,7)," +
				"(5005,'Reubicación por stock','2023-06-01',31,4,521,31,4,521,2);"
		)!=-1)
			System.out.println("Datos añadidos a la tabla Cambio correctamente.");
		else
			System.out.println("Ya había datos en la tabla Cambio.");		
	}

}
