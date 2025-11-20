package JUGUETERIA;

import java.util.Date;

public class Cambio {
	private int id;
	private int idEmpleado;
	private int idJugueteOriginal;
	private int idJugueteNuevo;
	private String motivo;
	private Date fecha;
	private String idStandOrigen;
	private String idStandDestino;
	
	public Cambio(int id, int idEmpleado, int idJugueteOriginal, int idJugueteNuevo, String motivo, Date fecha,
			String idStandOrigen, String idStandDestino) {
		super();
		this.id = id;
		this.idEmpleado = idEmpleado;
		this.idJugueteOriginal = idJugueteOriginal;
		this.idJugueteNuevo = idJugueteNuevo;
		this.motivo = motivo;
		this.fecha = fecha;
		this.idStandOrigen = idStandOrigen;
		this.idStandDestino = idStandDestino;
	}

	@Override
	public String toString() {
		return "Cambio [id=" + id + ", idEmpleado=" + idEmpleado + ", idJugueteOriginal=" + idJugueteOriginal
				+ ", idJugueteNuevo=" + idJugueteNuevo + ", motivo=" + motivo + ", fecha=" + fecha + ", idStandOrigen="
				+ idStandOrigen + ", idStandDestino=" + idStandDestino + "]";
	}
	
}
