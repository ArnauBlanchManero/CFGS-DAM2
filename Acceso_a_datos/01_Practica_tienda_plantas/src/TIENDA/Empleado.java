package TIENDA;

import java.io.Serializable;

public class Empleado implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
	String nombre;
	String passwd;
	String cargo;
	public Empleado(int id, String nombre, String passwd, String cargo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.passwd = passwd;
		this.cargo = cargo;
	}
	@Override
	public String toString() {
		return "Empleado [id=" + id + ", nombre=" + nombre + ", passwd=" + passwd + ", cargo=" + cargo + "]";
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
	
}
