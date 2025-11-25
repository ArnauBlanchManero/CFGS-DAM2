package JUGUETERIA;

import java.sql.ResultSet;
import java.sql.SQLException;
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

	public static void datosEmpleado(int idEmpleadoBuscar) {
		ResultSet datosCambiosEmpleado = BaseDatos.consulta("SELECT * FROM cambios WHERE empleado_idEmpleado = "+idEmpleadoBuscar);
	try {
		while(datosCambiosEmpleado.next()) {
			Cambio cambioTMP;
			int idCambio = datosCambiosEmpleado.getInt(1);
			String motivo = datosCambiosEmpleado.getString(2);
			Date fecha = datosCambiosEmpleado.getDate(3);
			int idStandOriginal = datosCambiosEmpleado.getInt(4);
			int idZonaOriginal = datosCambiosEmpleado.getInt(5);
			int idJugueteOriginal = datosCambiosEmpleado.getInt(6);
			int idStandNuevo = datosCambiosEmpleado.getInt(7);
			int idZonaNueva = datosCambiosEmpleado.getInt(8);
			int idJugueteNuevo = datosCambiosEmpleado.getInt(9);
			int idEmpleado = datosCambiosEmpleado.getInt(10);
			String idZonaStandOrigen = idZonaOriginal +" "+idStandOriginal;
			String idZonaStandDestino = idZonaNueva +" "+idStandNuevo;
			cambioTMP = new Cambio(idCambio, idEmpleado, idJugueteOriginal, idJugueteNuevo, motivo, fecha, idZonaStandOrigen, idZonaStandDestino);
			System.out.println(cambioTMP.toString());
		}
	} catch (SQLException e) {
		System.out.println("La consulta no se ha ejecutado correctamente.");
		e.printStackTrace();
	} catch (NullPointerException e) {
		System.out.println("No hay datos para leer");
		e.printStackTrace();
	}
	}
	
}
