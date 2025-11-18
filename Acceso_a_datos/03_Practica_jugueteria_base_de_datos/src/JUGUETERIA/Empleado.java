package JUGUETERIA;

import java.util.Date;

public class Empleado {
	private int id;
	private String nombre;
	private CargoEmpleado cargo;
	private Date fechaIngreso;
	
	public Empleado(int id, String nombre, CargoEmpleado cargo, Date fechaIngreso) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.cargo = cargo;
		this.fechaIngreso = fechaIngreso;
	}
	
}
