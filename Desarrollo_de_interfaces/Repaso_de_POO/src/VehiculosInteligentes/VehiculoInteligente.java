package VehiculosInteligentes;

import java.time.LocalDate;

public class VehiculoInteligente {
	int id;
	String modelo;
	boolean sensoresActivos;
	double bateriaNivel;
	LocalDate fechaActivacion;
	
	/*public VehiculoInteligente() {
	}*/
	public VehiculoInteligente(int id, String modelo, boolean sensoresActivos, double bateriaNivel,
			LocalDate fechaActivacion) {
		super();
		this.id = id;
		this.modelo = modelo;
		this.sensoresActivos = sensoresActivos;
		this.bateriaNivel = bateriaNivel;
		this.fechaActivacion = fechaActivacion;
	}
	
	public void mostrarInfo() {
		System.out.println( "VehiculoInteligente [id=" + id + ", modelo=" + modelo + ", sensoresActivos=" + sensoresActivos
				+ ", bateriaNivel=" + bateriaNivel + ", fechaActivacion=" + fechaActivacion + "]");
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public boolean isSensoresActivos() {
		return sensoresActivos;
	}
	public void setSensoresActivos(boolean sensoresActivos) {
		this.sensoresActivos = sensoresActivos;
	}
	public double getBateriaNivel() {
		return bateriaNivel;
	}
	public void setBateriaNivel(double bateriaNivel) {
		this.bateriaNivel = bateriaNivel;
	}
	public LocalDate getFechaActivacion() {
		return fechaActivacion;
	}
	public void setFechaActivacion(LocalDate fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}
	
}
