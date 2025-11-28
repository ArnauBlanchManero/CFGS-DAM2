package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTextField;

import txt.LeerTxt;
import usuarios.Usuario;

public class Evento implements ActionListener{
	private String accion;
	JTextField nombreUsuario;
	JTextField contraseñaUsuario;
	JLabel lblSesionIncorrecta;
	ArrayList<Usuario> usuarios;
	
	public Evento(String accion) {
		super();
		this.accion = accion;
	}
	
	public Evento(String accion, JTextField txtNombre, JTextField txtContrasea, JLabel lblSesionIncorrecta, ArrayList<Usuario> usuarios) {
		super();
		this.accion = accion;
		this.nombreUsuario = txtNombre;
		this.contraseñaUsuario = txtContrasea;
		this.lblSesionIncorrecta = lblSesionIncorrecta;
		this.usuarios = usuarios;
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
//		TODO pasar por parametro los Panel para cambiar y los tengo aquí para futuros botones.
		Usuario sesion = new Usuario(nombreUsuario.getText(), null, contraseñaUsuario.getText(), false, 0);
		int cargo = sesion.comprobarCredenciales(usuarios);
		if(cargo == -1) {
			lblSesionIncorrecta.setVisible(true);
		} else {
			Ventana.rolUsuario = cargo;
		}
	}


}
