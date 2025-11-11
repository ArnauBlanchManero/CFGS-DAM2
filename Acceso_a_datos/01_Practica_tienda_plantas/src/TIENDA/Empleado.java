package TIENDA;

import java.io.Serializable;

/*
 * Trabajo hecho por Arnau Blanch Manero
 */

public class Empleado implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String nombre;
	private String passwd;
	private String cargo;
	public Empleado(int id, String nombre, String passwd, String cargo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.passwd = passwd;
		this.cargo = cargo;
	}
	@Override
	public String toString() {
		return "Identificación del empleado: " + getIdCeros() + "\n\tNombre: " + nombre + "\n\tContraseña: " + passwd + "\n\tCargo: " + cargo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getIdCeros() {
		String codigo = this.id+"";
		String codigoCeros = "";
		for (int i = 0; i < 4-codigo.length(); i++) {
			codigoCeros += "0";
		}
		codigoCeros += codigo;
		return codigoCeros;
	}
	
}
