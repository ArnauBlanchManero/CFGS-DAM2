package n00_Colegio;

public class Directivo extends Trabajador{
	boolean salesiano;
	Turno turno;

	public Directivo(String dni, String nombre, String apellidos, int salario, boolean salesiano, Turno turno) {
		super(dni, nombre, apellidos, salario);
		this.salesiano = salesiano;
		this.turno = turno;
	}
	@Override
	public String toString() {
		String esSalesiano = "no es Salesiano";
		if (salesiano) {
			esSalesiano = "es Salesiano";
		}
		return "Directivo= DNI: "+dni+", Nombre: "+nombre+", Apellidos: "+apellidos+", Salario: "+salario+", " + esSalesiano + " y tiene turno de " + turno;
	}
	
	public boolean isSalesiano() {
		return salesiano;
	}
	public void setSalesiano(boolean salesiano) {
		this.salesiano = salesiano;
	}
	public Turno getTurno() {
		return turno;
	}
	public void setTurno(Turno turno) {
		this.turno = turno;
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