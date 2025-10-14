package Interbloqueo;

public class Cuenta {
	String numCuenta;
	int saldo;
	public Cuenta(String numCuenta, int saldo) {
		super();
		this.numCuenta = numCuenta;
		this.saldo = saldo;
	}
	public String getNumCuenta() {
		return numCuenta;
	}
	public void setNumCuenta(String numCuenta) {
		this.numCuenta = numCuenta;
	}
	public int getSaldo() {
		return saldo;
	}
	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}
	
}
