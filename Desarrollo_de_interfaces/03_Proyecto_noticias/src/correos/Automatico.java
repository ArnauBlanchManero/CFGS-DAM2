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
		System.out.println("Soy "+usuario.getNombre());// TODO borrar
		// Creo un bucle infinito que comprueba si es la hora de enviar el correo
		while(true) {
			String horaActual = horaAhora.getHour()+":"+horaAhora.getMinute();
			System.out.println("Comprobando hora...");// TODO borrar
			if(horaActual.equals(horaEnvio)) {
				System.out.println("Es la hora");// TODO borrar
				Ventana.titulares = LeerTxt.leerTodasNoticias();
				System.out.println("Noticias leídas?");// TODO borrar
				if (Ventana.titulares == null || Ventana.titulares.size()==0) {
					JOptionPane.showMessageDialog(null, "No se han encontrado alguno de los titulares", "ERROR", 2);
				} else {
					try {
						System.out.println("Enviando correo a "+usuario.getCorreo());// TODO borrar
						this.mensaje = Evento.generarMensajeCorreo(usuario, Ventana.titulares);
<<<<<<< HEAD
						if(!this.enviar())
							JOptionPane.showMessageDialog(null, "No se ha enviado el correo automático", "ERROR", 2);
					} catch (NullPointerException e) {
=======
						this.enviar();
					} catch (NullPointerException | IndexOutOfBoundsException e) {
>>>>>>> 39ffd6fdf81871b179c01ced5db86e475166fd83
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
