package Colegio;

import java.util.List;

public class Alumno extends Trabajador{
	String fehca_nac;
	Sexo sexo;
	boolean repetidor;
	List<Modulo> modulos;
	
	public Alumno(String dni, String nombre, String apellidos, int salario, String fehca_nac, Sexo sexo,
			boolean repetidor, List<Modulo> modulos) {
		super(dni, nombre, apellidos);
		this.salario = 0;
		this.fehca_nac = fehca_nac;
		this.sexo = sexo;
		this.repetidor = repetidor;
		this.modulos = modulos;
	}
	
	
	@Override
	public String toString() {
		String esRepetidor = "no es repetidor";
		if (repetidor) {
			esRepetidor = "es repetidor";
		}
		return "Alumno= DNI: "+dni+", Nombre: "+nombre+", Apellidos: "+apellidos+", Fehca de nacimiento: " + fehca_nac + ", es " + sexo + ", " + esRepetidor + " y los módulos que tiene: "
				+modulos+ "]";
	}


	public String getFehca_nac() {
		return fehca_nac;
	}
	public void setFehca_nac(String fehca_nac) {
		this.fehca_nac = fehca_nac;
	}
	public Sexo getSexo() {
		return sexo;
	}
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	public boolean isRepetidor() {
		return repetidor;
	}
	public void setRepetidor(boolean repetidor) {
		this.repetidor = repetidor;
	}
	public List<Modulo> getModulos() {
		return modulos;
	}
	public void setModulos(List<Modulo> modulos2) {
		this.modulos = modulos2;
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
	public void setSalario(int Salario) {
		
	}
}