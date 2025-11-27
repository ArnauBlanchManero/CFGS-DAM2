package JUGUETERIA;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Empleado {
	private int id;
	private String nombre;
	private CargoEmpleado cargo;
	private Date fechaIngreso;

	public Empleado(int id, String nombre, String cargo, Date fechaIngreso) {
		super();
		this.id = id;
		this.nombre = nombre;
		CargoEmpleado cargoE;
		if (cargo.equalsIgnoreCase("cajero")) {
			cargoE = CargoEmpleado.CAJERO;
		} else if (cargo.equalsIgnoreCase("jefe")) {
			cargoE = CargoEmpleado.JEFE;
		} else {
			cargoE = null;
		}
		this.cargo = cargoE;
		this.fechaIngreso = fechaIngreso;
	}

	public Empleado(int id) {
		super();
		this.id = id;
		ResultSet datosEmpleados = BaseDatos.consulta("SELECT * FROM empleados WHERE idEmpleado = "+id);
		try {
			if(datosEmpleados.next()) {
				this.nombre = datosEmpleados.getString(2);
				switch (datosEmpleados.getString(3)) {
					case "Cajero":
						this.cargo = CargoEmpleado.CAJERO;
						break;
					case "Jefe":
						this.cargo = CargoEmpleado.JEFE;
						break;
					
					default:
						this.cargo = null;
						break;
				}
				this.fechaIngreso = datosEmpleados.getDate(4);
			}
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No hay datos para leer");
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return nombre + " (ID: " + id +
		           " • Cargo: " + cargo +
		           " • Fecha de ingreso: " + fechaIngreso +
		           ")";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public boolean eliminar() {
		return (BaseDatos.consultaModifica("DELETE FROM empleados WHERE idEmpleado = "+id)==1);
	}

	public void modificar(ArrayList<String> columnas, ArrayList<Object> datos, int id) {
		ArrayList<Object> param = new ArrayList<Object>();
		String seters = "";
		int i;
		for (i = 0; i < columnas.size()-1; i++) {
			seters += columnas.get(i)+" = ";
			param.add(datos.get(i));
			seters += "?, ";
		}
		seters += columnas.get(i)+" = ";
		param.add(datos.get(i));
		seters += "? ";
		param.add(id);
		if(BaseDatos.modificaSeguro(param, "UPDATE empleados SET "+seters+"WHERE idEmpleado = ?")==1)
			System.out.println("Empleado modificado correctamente.");
		else {
			System.out.println("Error al modificar el empleado.");
		}
	}

	public static void registrarNuevoEmpleado(String nombreNuevo, String cargoNuevo, Date fechaNueva) {
		ArrayList<Object> param = new ArrayList<Object>();
		param.add(nombreNuevo);
		param.add(cargoNuevo);
		param.add(fechaNueva);
		if (BaseDatos.modificaSeguro(param, "INSERT INTO empleados (Nombre, Cargo, Fecha_ingreso) VALUES (?, ?, ?)")==1)
			System.out.println("Empleado añadido correctamente.");
		else
			System.out.println("Error al añadir el nuevo empleado.");

	}

	public static void mostrarPorId(int idBuscar) {
		ResultSet datosEmpleados = BaseDatos.consulta("SELECT * FROM empleados WHERE idEmpleado = "+idBuscar);
		try {
			if(datosEmpleados.next()) {
				Empleado empleadoTMP;
				int id = datosEmpleados.getInt(1);
				String nombre = datosEmpleados.getString(2);
				String cargo = datosEmpleados.getString(3);
				Date fecha = datosEmpleados.getDate(4);
				
				empleadoTMP = new Empleado(id, nombre, cargo, fecha);
				System.out.println(empleadoTMP.toString());
			}
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No hay datos para leer");
			e.printStackTrace();
		}
	}

}
