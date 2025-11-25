package JUGUETERIA;

import java.util.ArrayList;

public class Stock {
	private int idJuguete;
	private String idStand;
	private String idStock;
	private int cantidad;
	
	public Stock(int idJuguete, String idStand, int cantidad) {
		super();
		this.idJuguete = idJuguete;
		this.idStand = idStand;
		this.cantidad = cantidad;
		this.idStock = idStand + " " + idJuguete;
	}

	@Override
	public String toString() {
		return "Stock [idJuguete=" + idJuguete + ", idStand=" + idStand + ", idStock=" + idStock + ", cantidad="
				+ cantidad + "]";
	}

	public boolean guardarJuguete() {
		ArrayList<Object> param = new ArrayList<Object>();
		int idS;
		int idZ;
		String [] idZonaStand = this.idStand.split(" ");
		idZ = Integer.parseInt(idZonaStand[0]);
		idS = Integer.parseInt(idZonaStand[1]);
		param.add(idS);
		param.add(idZ);
		param.add(this.idJuguete);
		param.add(this.cantidad);
		return (BaseDatos.modificaSeguro(param, "INSERT INTO stocks (stand_idStand, stand_zona_idZona, juguete_idJuguete, cantidad) VALUES (?, ?, ?, ?)")==1);
	}
	
}
