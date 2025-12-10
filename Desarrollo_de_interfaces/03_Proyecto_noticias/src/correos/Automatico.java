package correos;

import java.time.LocalTime;

import txt.LeerTxt;

public class Automatico implements Runnable{
	LocalTime horaEnvio = LocalTime.now();
	@Override
	public void run() {
		// TODO Con LocalTime para coger la hora y los minutos y un while infinito q compruebe si ya esa hora
		
	}
	public void imprimirHora() {
		String horaActual = horaEnvio.getHour()+":"+horaEnvio.getMinute();
		if(horaActual.equals(LeerTxt.leerHora())) {
			System.out.println("yes");
		}
		
	}
}
