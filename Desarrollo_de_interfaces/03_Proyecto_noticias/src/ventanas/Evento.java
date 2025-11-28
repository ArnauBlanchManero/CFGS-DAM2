package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

public class Evento implements ActionListener{
	String accion;
	String nombreUsuario;
	String contraseñaUsuario;
	JLabel lblSesionIncorrecta;
	
	public Evento(String accion) {
		super();
		this.accion = accion;
	}
	
	public Evento(String accion, String nombre, String contraseña, JLabel lblSesionIncorrecta) {
		super();
		this.accion = accion;
		this.nombreUsuario = nombre;
		this.contraseñaUsuario = contraseña;
		this.lblSesionIncorrecta = lblSesionIncorrecta;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (accion) {
		case "comprobar sesion":
			comprobar_inicio_sesion();
			break;
		default:
			break;
		}
	}

	private void comprobar_inicio_sesion() {
//		leer_txt();
		lblSesionIncorrecta.setVisible(true);
	}


}
