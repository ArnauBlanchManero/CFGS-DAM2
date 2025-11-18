package JUGUETERIA;

public class Stock {
	private int idJuguete;
	private String idStand;
	private String idStock;
	public Stock(int idJuguete, String idStand) {
		super();
		this.idJuguete = idJuguete;
		this.idStand = idStand;
		this.idStock = idJuguete + " " + idStand;
	}
	
}
