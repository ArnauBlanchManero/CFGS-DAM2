package JUGUETERIA;

import java.util.Date;

public class Venta {
	private int id;
	private int idEmpleado;
	private int idJuguete;
	private String idStand;
	private Date fecha;
	private int monto;
	private TipoPago pago;
	
	public Venta(int id, int idEmpleado, int idJuguete, String idStand, Date fecha, int monto, TipoPago pago) {
		super();
		this.id = id;
		this.idEmpleado = idEmpleado;
		this.idJuguete = idJuguete;
		this.idStand = idStand;
		this.fecha = fecha;
		this.monto = monto;
		this.pago = pago;
	}
	
}
