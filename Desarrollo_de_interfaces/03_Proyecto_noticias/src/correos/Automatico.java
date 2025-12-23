package correos;

import java.time.LocalTime;

import javax.swing.JOptionPane;

import txt.LeerTxt;
import usuarios.Usuario;
import ventanas.Evento;
import ventanas.Ventana;

/*
 * Trabajo realizado por Arnau Blanch Manero
 */

public class Automatico extends Email implements Runnable{
	Usuario usuario;
	
	public Automatico(Usuario usuario) {
		super(usuario.getCorreo(), Evento.generarMensajeCorreo(usuario, Ventana.titulares));
		// Por usuario
		this.usuario = usuario;
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
					try {
						this.mensaje = Evento.generarMensajeCorreo(usuario, Ventana.titulares);
						if(!this.enviar())
							JOptionPane.showMessageDialog(null, "No se ha enviado el correo automático", "ERROR", 2);
					} catch (NullPointerException | IndexOutOfBoundsException e) {
						JOptionPane.showMessageDialog(null, "No se ha enviado el correo automático", "ERROR", 2);
					}
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
