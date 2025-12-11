package correos;

import java.time.LocalTime;

import txt.LeerTxt;

public class Automatico extends Email implements Runnable{
	
	public Automatico(String correoDestino, String mensaje) {
		super(correoDestino, mensaje);
		// TODO Auto-generated constructor stub
	}
	
	LocalTime horaAhora = LocalTime.now();
	String horaEnvio = LeerTxt.leerHora();
	@Override
	public void run() {
		// TODO Con LocalTime para coger la hora y los minutos y un while infinito q compruebe si ya esa hora
		while(true) {
			String horaActual = horaAhora.getHour()+":"+horaAhora.getMinute();
			if(horaActual.equals(horaEnvio)) {
				this.enviar();
			}
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				
			}
			horaAhora = LocalTime.now();
		}
		
	}
}
