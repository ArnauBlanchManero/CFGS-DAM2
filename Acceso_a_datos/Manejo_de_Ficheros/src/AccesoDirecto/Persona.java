package AccesoDirecto;

public class Persona {
	char[] nombre = new char[10];
	int edad;
	public Persona(char[] nombre, int edad) {
		super();
		this.nombre = nombre;
		this.edad = edad;
	}
	public char[] getNombre() {
		return nombre;
	}
	public void setNombre(char[] nombre) {
		this.nombre = nombre;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	
}
