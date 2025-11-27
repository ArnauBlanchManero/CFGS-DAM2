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
			case "efectivo":
				this.pago = TipoPago.EFECTIVO;
				break;
			case "tarjeta":
				this.pago = TipoPago.TARJETA;
				break;
			case "paypal":
				this.pago = TipoPago.PAYPAL;
				break;
			default:
				this.pago = null;
				break;
		}
	}

	

	public Venta(int id) {
		super();
		this.id = id;
		ResultSet datosVentas = BaseDatos.consulta("SELECT * FROM ventas WHERE idVenta = "+id);
		try {
			if(datosVentas.next()) {
				this.fecha = datosVentas.getDate(2);
				this.monto = datosVentas.getDouble(3);
				switch (datosVentas.getString(4)) {
					case "Efectivo":
						this.pago = TipoPago.EFECTIVO;
						break;
					case "Tarjeta":
						this.pago = TipoPago.TARJETA;
						break;
					case "Paypal":
						this.pago = TipoPago.PAYPAL;
						break;
					
					default:
						this.pago = null;
						break;
				}
				this.idJuguete = datosVentas.getInt(5);
				this.idEmpleado = datosVentas.getInt(6);
				this.idStand = datosVentas.getInt(8) + " " +datosVentas.getInt(7);
			}
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No hay datos para leer");
			e.printStackTrace();
		}
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getIdEmpleado() {
		return idEmpleado;
	}



	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}


	public int getIdJuguete() {
		return idJuguete;
	}



	public void setIdJuguete(int idJuguete) {
		this.idJuguete = idJuguete;
	}



	public String getIdStand() {
		return idStand;
	}



	public void setIdStand(String idStand) {
		this.idStand = idStand;
	}



	public Date getFecha() {
		return fecha;
	}



	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}



	public double getMonto() {
		return monto;
	}



	public void setMonto(double monto) {
		this.monto = monto;
	}



	public TipoPago getPago() {
		return pago;
	}



	public void setPago(TipoPago pago) {
		this.pago = pago;
	}



	@Override
	public String toString() {
		return "Venta " + id +
		           " (Juguete: " + idJuguete +
		           " • Empleado: " + idEmpleado +
		           " • Monto: " + monto + "€" +
		           " • Pago: " + pago +
		           " • Fecha: " + fecha +
		           ")";
	}

	public static void empleadosMasVenden() {
		ResultSet datosVentasMes = BaseDatos.consulta("SELECT empleado_idEmpleado, COUNT(empleado_idEmpleado) AS veces_vendido FROM ventas GROUP BY empleado_idEmpleado ORDER BY veces_vendido DESC LIMIT 5");
		try {
			while(datosVentasMes.next()) {
				Empleado.mostrarPorId(datosVentasMes.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No hay datos para leer");
			e.printStackTrace();
		}
	}

	public static void productosMasVendidos() {
		ResultSet datosVentasMes = BaseDatos.consulta("SELECT t2.idJuguete, (montoTotal/precioUnitario) AS cantidadVendida FROM (SELECT t1.idJuguete, precio AS precioUnitario, montoTotal FROM juguetes INNER JOIN (SELECT juguete_idJuguete AS idJuguete, SUM(monto) AS montoTotal FROM ventas GROUP BY juguete_idJuguete) t1 ON juguetes.idJuguete = t1.idJuguete) t2 ORDER BY cantidadVendida DESC LIMIT 5");
		try {
			while(datosVentasMes.next()) {
				Juguete.mostrarPorId(datosVentasMes.getInt(1));
				System.out.println("\tCantidad: "+datosVentasMes.getInt(2));
			}
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No hay datos para leer");
			e.printStackTrace();
		}
	}

	public void cambio(String idZonaStandOriginal, String idZonaStandNuevo, int idJugueteOriginal, int idJugueteNuevo, int idEmpleado) { 
		String motivo = Utilidades.preguntarString("¿Cuál es el motivo del cambio? ");
		Date fecha;
		fecha = new Date(System.currentTimeMillis());
		ArrayList<Object> param = new ArrayList<Object>();
		param.add(motivo);
		param.add(fecha);
		String[] idZonaStandO = idZonaStandOriginal.split(" ");
		String[] idZonaStandN = idZonaStandNuevo.split(" ");
		param.add(Integer.parseInt(idZonaStandO[1]));
		param.add(Integer.parseInt(idZonaStandO[0]));
		param.add(idJugueteOriginal);
		param.add(Integer.parseInt(idZonaStandN[1]));
		param.add(Integer.parseInt(idZonaStandN[0]));
		param.add(idJugueteNuevo);
		param.add(idEmpleado);
		int cantidad = 0;
		boolean cambiado = true;
		if(BaseDatos.modificaSeguro(param, "INSERT INTO cambios (MOTIVO, Fecha, stock_stand_idStand_Original, stock_stand_zona_idZona_Original, stock_juguete_idJuguete_Original, stock_stand_idStand_Nuevo, stock_stand_zona_idZona_Nuevo, stock_juguete_idJuguete_Nuevo, empleado_idEmpleado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")==1) {
			System.out.println("Cambio realizado");
			ResultSet resultado1 = BaseDatos.consulta("SELECT cantidad FROM stocks WHERE stand_idStand = "+idZonaStandO[1]+" AND stand_zona_idZona = "+idZonaStandO[0]+" AND juguete_idJuguete = "+idJugueteOriginal);
			ResultSet resultado2 = BaseDatos.consulta("SELECT cantidad FROM stocks WHERE stand_idStand = "+idZonaStandN[1]+" AND stand_zona_idZona = "+idZonaStandN[0]+" AND juguete_idJuguete = "+idJugueteNuevo);
			int stockO = 0;
			int stockN = 0;
			try {
				if (resultado1.next()) {
					stockO = resultado1.getInt(1);
				}
				if (resultado2.next()) {
					stockN = resultado2.getInt(1);
				}
				ResultSet resultado3 = BaseDatos.consulta("SELECT monto, precio FROM ventas INNER JOIN (SELECT precio, idJuguete FROM juguetes)t1 on ventas.juguete_idJuguete = t1.idJuguete WHERE idVenta = "+id);
				if (resultado3.next()) {
					cantidad = (int) (resultado3.getDouble(1)/resultado3.getDouble(2));
				}
				int nuevoStockO = stockO + cantidad;
				int nuevoStockN = stockN - cantidad;
				if(nuevoStockN < 0)
					nuevoStockN = 0;
				if(BaseDatos.consultaModifica("UPDATE stocks SET cantidad = "+nuevoStockO+" WHERE stand_idStand = "+idZonaStandO[1]+" AND stand_zona_idZona = "+idZonaStandO[0]+" AND juguete_idJuguete = "+idJugueteOriginal) == 1 && BaseDatos.consultaModifica("UPDATE stocks SET cantidad = "+nuevoStockN+" WHERE stand_idStand = "+idZonaStandN[1]+" AND stand_zona_idZona = "+idZonaStandN[0]+" AND juguete_idJuguete = "+idJugueteNuevo) == 1 )
					System.out.println("Juguete intercambiado correctamente.");
				else 
					cambiado = false;
			} catch (SQLException e) {
				System.out.println("Error en la ejecución de la consutla.");
				stockO = 0;
				cambiado = false;
				e.printStackTrace();
			}
		} else
			cambiado = false;
		if (!cambiado) {
			System.out.println("Error al intercambiar el juguete.");
		}
		
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

	public static void ventasMesEmpleado(int idEmpleadoBuscar, String mes) {
		ResultSet datosVentasMes = BaseDatos.consulta("SELECT * FROM ventas WHERE Fecha LIKE '%-"+mes+"-%' AND empleado_idEmpleado = "+idEmpleadoBuscar);
		try {
			while(datosVentasMes.next()) {
				Venta ventaTMP;
				int idVenta = datosVentasMes.getInt(1);
				Date fecha = datosVentasMes.getDate(2);
				double monto = datosVentasMes.getDouble(3);
				String tipoPago = datosVentasMes.getString(4);
				int idJuguete = datosVentasMes.getInt(5);
				int idEmpleado = datosVentasMes.getInt(6);
				int idStand = datosVentasMes.getInt(7);
				int idZona = datosVentasMes.getInt(8);
				String idZonaStand = idZona +" "+idStand;
				ventaTMP = new Venta(idVenta, idEmpleado, idJuguete, idZonaStand, fecha, monto, tipoPago);
				System.out.println(ventaTMP.toString());
			}
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No hay datos para leer");
			e.printStackTrace();
		}
	}

	public static void ventasMes(String mes) {
		ResultSet datosVentasMes = BaseDatos.consulta("SELECT * FROM ventas WHERE Fecha LIKE '%-"+mes+"-%'");
		try {
			while(datosVentasMes.next()) {
				Venta ventaTMP;
				int idVenta = datosVentasMes.getInt(1);
				Date fecha = datosVentasMes.getDate(2);
				double monto = datosVentasMes.getDouble(3);
				String tipoPago = datosVentasMes.getString(4);
				int idJuguete = datosVentasMes.getInt(5);
				int idEmpleado = datosVentasMes.getInt(6);
				int idStand = datosVentasMes.getInt(7);
				int idZona = datosVentasMes.getInt(8);
				String idZonaStand = idZona +" "+idStand;
				ventaTMP = new Venta(idVenta, idEmpleado, idJuguete, idZonaStand, fecha, monto, tipoPago);
				System.out.println(ventaTMP.toString());
			}
		} catch (SQLException e) {
			System.out.println("La consulta no se ha ejecutado correctamente.");
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No hay datos para leer");
			e.printStackTrace();
		}
	}

	public static void mostrarPorId(int idBuscar) {
		ResultSet datosVentas = BaseDatos.consulta("SELECT * FROM ventas WHERE idVenta = "+idBuscar);
		try {
			if(datosVentas.next()) {
				Venta ventaTMP;
				int idVenta = datosVentas.getInt(1);
				Date fecha = datosVentas.getDate(2);
				double monto = datosVentas.getDouble(3);
				String tipoPago = datosVentas.getString(4);
				int idJuguete = datosVentas.getInt(5);
				int idEmpleado = datosVentas.getInt(6);
				int idStand = datosVentas.getInt(7);
				int idZona = datosVentas.getInt(8);
				String idZonaStand = idZona +" "+idStand;
				ventaTMP = new Venta(idVenta, idEmpleado, idJuguete, idZonaStand, fecha, monto, tipoPago);
				System.out.println(ventaTMP.toString());
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
