package n08_WaitNotify;

public class HiloJugador extends Thread {
	private String nombre;

	public void run() {

		for (int i = 0; i < 100; i++) {
			try {
				if (nombre.equals("PING..."))
					PingPong.s1.acquire();
				else
					PingPong.s2.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(nombre);
			// notify(PingPong.s2);
			if (nombre.equals("PING..."))
				PingPong.s2.release();
			else
				PingPong.s1.release();
		}
		/*
		 * while(true) { //wait(PingPong.s1); 
		 * try { if(nombre.equals("PING"))
		 * PingPong.s1.acquire(); else PingPong.s2.acquire(); } catch
		 * (InterruptedException e) { 
		 * e.printStackTrace(); } System.out.println(nombre); //notify(PingPong.s2);
		 * if(nombre.equals("PING")) PingPong.s2.release(); else PingPong.s1.release();
		 */

	}

	public HiloJugador(String nombre) {
		super();
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
