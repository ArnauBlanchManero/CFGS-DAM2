package JUGUETERIA;

import java.util.Date;

public class Venta {
	private int id;
	private int idEmpleado;
	private int idJuguete;
	private String idStand;
	private Date fecha;
	private double monto;
	private TipoPago pago;
	
	public Venta(int id, int idEmpleado, int idJuguete, String idStand, Date fecha, double monto, TipoPago pago) {
		super();
		this.id = id;
		this.idEmpleado = idEmpleado;
		this.idJuguete = idJuguete;
		this.idStand = idStand;
		this.fecha = fecha;
		this.monto = monto;
		this.pago = pago;
	}

	@Override
	public String toString() {
		return "Venta [id=" + id + ", idEmpleado=" + idEmpleado + ", idJuguete=" + idJuguete + ", idStand=" + idStand
				+ ", fecha=" + fecha + ", monto=" + monto + ", pago=" + pago + "]";
	}

	public static void empleadosMasVenden() {
		// TODO Auto-generated method stub
		
	}

	public static void productosMasVendidos() {
		// TODO Auto-generated method stub
		
	}

	public void devolver() {
		// TODO Auto-generated method stub
		
	}

	public void vender() {
		// TODO Auto-generated method stub
		
	}

	public static void ventasMesEmpleado(int idEmpleado, int mes) {
		// TODO Auto-generated method stub
		
	}

	public static void ventasMes(int mes) {
		// TODO Auto-generated method stub
		
	}
	
}
