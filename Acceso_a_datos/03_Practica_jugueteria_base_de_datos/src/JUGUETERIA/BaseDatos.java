package JUGUETERIA;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDatos {
	private String nombre;
	Connection conexion;

	public BaseDatos(String nombreSchema, String[] argumentos) {
		super();
		this.nombre = nombreSchema;
		conexion = conectar(argumentos[0], argumentos[1], argumentos[2]);
	}
	// TODO conexion

	public Connection conectar(String url, String usuario, String password) {
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
	
	private int consultaModifica(String consulta) {
		try {
			PreparedStatement sentencia;
			sentencia = conexion.prepareStatement(consulta);
			return sentencia.executeUpdate();
			/*
			if(sentencia.executeUpdate()>0) {
				System.out.println("Datos actualizados correctamente");
			} else {
				System.out.println("No se ha actualizado ningún dato");
			}
			*/
		} catch (SQLException e) {
			System.out.println("Error al ejecutar la consulta.");
			e.printStackTrace();
			return -1;
		}
		
	}
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
			/*while (resultado_consulta.next()) {
				int id = resultado_consulta.getInt(1);
				System.out.println(id);
			}
			*/
		} catch (SQLException e) {
			System.out.println("No se ha podido realizar la consulta correctamente.");
			e.printStackTrace();
		}
		return resultado_consulta;
	}

	// TODO crear
	// TODO consultas(String)
	// TODO consultasComplejas(ArrayString)
	// TODO consultasUpdate/Delete
}
