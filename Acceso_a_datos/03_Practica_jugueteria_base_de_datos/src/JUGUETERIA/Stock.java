package JUGUETERIA;

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
	
}
