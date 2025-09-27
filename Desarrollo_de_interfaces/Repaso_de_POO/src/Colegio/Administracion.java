package Colegio;

public class Administracion extends Trabajador{
	String estudios;
	int antiguedad;
	
	public Administracion(String dni, String nombre, String apellidos, int salario, String estudios,
			int antiguedad) {
		super(dni, nombre, apellidos, salario);
		this.estudios = estudios;
		this.antiguedad = antiguedad;
	}
	@Override
	public String toString() {
		return "Administracion= DNI: "+dni+", Nombre: "+nombre+", Apellidos: "+apellidos+", Salario: "+salario+", Estudios: " + estudios + ", Antiguedad: " + antiguedad;
	}
	public String getEstudios() {
		return estudios;
	}
	public void setEstudios(String estudios) {
		this.estudios = estudios;
	}
	public int getAntiguedad() {
		return antiguedad;
	}
	public void setAntiguedad(int antiguedad) {
		this.antiguedad = antiguedad;
	}
	@Override
	public int getSalario() {
		return salario;
	}
	public int compareTo(Trabajador t) {
		if (salario > t.getSalario())
			return 1;
		if (salario < t.getSalario())
			return -1;
		
		return 0;
	}
	@Override
	public void setSalario(int salario) {
		this.salario = salario;
		
	}
}