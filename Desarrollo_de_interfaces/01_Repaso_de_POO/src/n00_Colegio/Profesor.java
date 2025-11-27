package n00_Colegio;

public class Profesor extends Trabajador implements Comparable<Profesor>{
	int n_asignaturas;
	boolean tutor;
	
	public Profesor(String dni, String nombre, String apellidos, int salario, int n_asignaturas, boolean tutor) {
		super(dni, nombre, apellidos, salario);
		this.n_asignaturas = n_asignaturas;
		this.tutor = tutor;
	}
	@Override
	public String toString() {
		String esTutor = "no es tutor";
		if (tutor) {
			esTutor = "es tutor";
		}
		return "Profesor= DNI: "+dni+", Nombre: "+nombre+", Apellidos: "+apellidos+", Salario: "+salario+", Número de asignaturas: " + n_asignaturas + " y " + esTutor;
	}
	public int getN_asignaturas() {
		return n_asignaturas;
	}
	public void setN_asignaturas(int n_asignaturas) {
		this.n_asignaturas = n_asignaturas;
	}
	public boolean isTutor() {
		return tutor;
	}
	public void setTutor(boolean tutor) {
		this.tutor = tutor;
	}
	@Override
	public int getSalario() {
		return salario;
	}
	@Override
	public void setSalario(int salario) {
		this.salario = salario;
		
	}
	@Override
	public int compareTo(Profesor o) {
		if (salario > o.getSalario())
			return 1;
		if (salario < o.getSalario())
			return -1;
		
		return 0;
	}
	
}