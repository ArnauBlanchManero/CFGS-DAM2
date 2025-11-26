package hibernate;

import java.awt.Robot;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class Principal {

	public static void main(String[] args) {
		/**
		 * -- MySQL Workbench Forward Engineering
		 * 
		 *
		 *	SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
		 *	SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
		 *	SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
		 *	
		 *	-- -----------------------------------------------------
		 *	-- Schema mydb
		 *	-- -----------------------------------------------------
		 *	-- -----------------------------------------------------
		 *	-- Schema hibernate_db
		 *	-- -----------------------------------------------------
		 *	
		 *	-- -----------------------------------------------------
		 *	-- Schema hibernate_db
		 *	-- -----------------------------------------------------
		 *	CREATE SCHEMA IF NOT EXISTS `hibernate_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
		 *	USE `hibernate_db` ;
		 *	
		 *	-- -----------------------------------------------------
		 *	-- Table `hibernate_db`.`personas`
		 *	-- -----------------------------------------------------
		 *	CREATE TABLE IF NOT EXISTS `hibernate_db`.`personas` (
		 *	  `idPersonas` INT UNSIGNED NOT NULL AUTO_INCREMENT,
		 *	  `nombre` VARCHAR(45) NULL DEFAULT NULL,
		 *	  `edad` INT UNSIGNED NULL DEFAULT NULL,
		 *	  PRIMARY KEY (`idPersonas`))
		 *	ENGINE = InnoDB
		 *	AUTO_INCREMENT = 2
		 *	DEFAULT CHARACTER SET = utf8mb4
		 *	COLLATE = utf8mb4_0900_ai_ci;
		 *	
		 *	
		 *	SET SQL_MODE=@OLD_SQL_MODE;
		 *	SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
		 *	SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
		 *
		 */
		Session sesion = HibernateUtil.getSf().openSession();
		Transaction t = sesion.beginTransaction();
		insertarDatos(sesion, t);
		int id = 1;
		leerDato(sesion, t, 3);
		
		modificarDato(sesion, t, 4);
		eliminarDato(sesion, t, 5);
		try {
			t.commit();
		} catch (Exception e) {
			t.rollback();
		}
		sesion.close();
		HibernateUtil.shutdown();
	}

	private static void eliminarDato(Session sesion, Transaction t, int id) {
		Persona personaBorrar = sesion.get(Persona.class, id);
		if(personaBorrar!=null) {
			sesion.remove(personaBorrar);
		}
	}

	private static void modificarDato(Session sesion, Transaction t, int id) {
		Persona personaModificar = sesion.get(Persona.class, id);
		sesion.merge(personaModificar);
		if(personaModificar!=null) {
			personaModificar.setEdad(personaModificar.getEdad()+1);
			System.out.println(personaModificar.toString());
		}
	}

	private static void leerDato(Session sesion, Transaction t, int id) {
		Persona personaResultante = sesion.get(Persona.class, id);
		if(personaResultante!=null)
			System.out.println(personaResultante.toString());
	}

	private static void insertarDatos(Session sesion, Transaction t) {
		Persona arnau = new Persona("Arnau", 19);
		sesion.save(arnau);
	}

}
