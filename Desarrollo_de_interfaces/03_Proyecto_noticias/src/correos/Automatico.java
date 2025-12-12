package correos;

import java.time.LocalTime;

import javax.swing.JOptionPane;

import txt.LeerTxt;
import ventanas.Evento;
import ventanas.Ventana;

/*
 * Trabajo realizado por Arnau Blanch Manero
 */

public class Automatico extends Email implements Runnable{
	
	public Automatico(String correoDestino, String mensaje) {
		super(correoDestino, mensaje);
		// Hereda los atributos y métodos de su padre
	}
	
	LocalTime horaAhora = LocalTime.now();
	String horaEnvio = LeerTxt.leerHora();
	// Necesito que sea un hilo para que la ejecución del programa principal no se quede bloqueada
	@Override
	public void run() {
		// Creo un bucle infinito que comprueba si es la hora de enviar el correo
		while(true) {
			String horaActual = horaAhora.getHour()+":"+horaAhora.getMinute();
			if(horaActual.equals(horaEnvio)) {
				Ventana.titulares = LeerTxt.leerTodasNoticias();
				if (Ventana.titulares == null || Ventana.titulares.size()==0) {
					JOptionPane.showMessageDialog(null, "No se han encontrado alguno de los titulares", "ERROR", 2);
				} else {
					this.mensaje = Evento.generarMensajeCorreo(Evento.usuarioLogueado, Ventana.titulares);
					this.enviar();
				}
			}
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				
			}
			horaAhora = LocalTime.now();
		}
		
	}
}
