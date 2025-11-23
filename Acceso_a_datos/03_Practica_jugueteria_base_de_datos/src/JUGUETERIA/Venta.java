package JUGUETERIA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Venta {
	private int id;
	private int idEmpleado;
	private int idJuguete;
	private String idStand;
	private Date fecha;
	private double monto;
	private TipoPago pago;
	
	public Venta(int id, int idEmpleado, int idJuguete, String idStand, Date fecha, double monto, String pago) {
		super();
		this.id = id;
		this.idEmpleado = idEmpleado;
		this.idJuguete = idJuguete;
		this.idStand = idStand;
		this.fecha = fecha;
		this.monto = monto;
		switch(pago.toLowerCase()) {
			case "devolucion":
				this.pago = TipoPago.DEVOLUCION;
				break;
			case "efectivo":
				this.pago = TipoPago.EFECTIVO;
				break;
			case "tarjeta":
				this.pago = TipoPago.TARJETA;
				break;
			case "Paypal":
				this.pago = TipoPago.PAYPAL;
				break;
			default:
				this.pago = null;
				break;
		}
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

	public void vender(int cantidad) {
		ArrayList<Object> param = new ArrayList<Object>();
		param.add(this.fecha);
		param.add(this.monto);
		param.add(this.pago.toString());
		param.add(this.idJuguete);
		param.add(this.idEmpleado);
		String[] idZonaStand = this.idStand.split(" ");
		param.add(Integer.parseInt(idZonaStand[1]));
		param.add(Integer.parseInt(idZonaStand[0]));
		boolean vendido = true;
		if(BaseDatos.modificaSeguro(param, "INSERT INTO ventas (Fecha, Monto, tipo_pago, juguete_idJuguete, empleado_idEmpleado, STAND_idStand, STAND_zona_idZona) VALUES (?, ?, ?, ?, ?, ?, ?)")==1) {
			ResultSet resultado = BaseDatos.consulta("SELECT cantidad FROM stocks WHERE stand_idStand = "+idZonaStand[1]+" AND stand_zona_idZona = "+idZonaStand[0]+" AND juguete_idJuguete = "+idJuguete);
			int stock = 0;
			try {
				if (resultado.next()) {
					stock = resultado.getInt(1);
				}
				int nuevoStock = stock - cantidad;
				if(BaseDatos.consultaModifica("UPDATE stocks SET cantidad = "+nuevoStock+" WHERE stand_idStand = "+idZonaStand[1]+" AND stand_zona_idZona = "+idZonaStand[0]+" AND juguete_idJuguete = "+idJuguete) == 1)
					System.out.println("Juguete vendido correctamente.");
				else 
					vendido = false;
			} catch (SQLException e) {
				System.out.println("Error en la ejecución de la consutla.");
				stock = 0;
				vendido = false;
				e.printStackTrace();
			}
		} else
			vendido = false;
		if (!vendido) {
			System.out.println("Error al vender el juguete.");
		}
	}

	public static void ventasMesEmpleado(int idEmpleado, int mes) {
		// TODO Auto-generated method stub
		
	}

	public static void ventasMes(int mes) {
		// TODO Auto-generated method stub
		
	}
	
}
