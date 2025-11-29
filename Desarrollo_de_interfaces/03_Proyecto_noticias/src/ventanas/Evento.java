package ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;

import txt.LeerTxt;
import usuarios.Usuario;

public class Evento implements ActionListener{
	private String accion;
	JTextField nombreUsuario;
	JTextField contraseñaUsuario;
	JLabel lblSesionIncorrecta;
	ArrayList<Usuario> usuarios;
	JLayeredPane todosPaneles;
	
	public Evento(String accion, JLayeredPane todosPaneles) {
		super();
		this.accion = accion;
		this.todosPaneles = todosPaneles;
	}
	
	public Evento(String accion, JTextField txtNombre, JTextField txtContrasea, JLabel lblSesionIncorrecta, ArrayList<Usuario> usuarios, JLayeredPane todosPaneles) {
		super();
		this.accion = accion;
		this.nombreUsuario = txtNombre;
		this.contraseñaUsuario = txtContrasea;
		this.lblSesionIncorrecta = lblSesionIncorrecta;
		this.usuarios = usuarios;
		this.todosPaneles = todosPaneles;
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
		Usuario sesion = new Usuario(-1, nombreUsuario.getText(), null, contraseñaUsuario.getText(), false, 0);
		int cargo = sesion.comprobarCredenciales(usuarios);
		if(cargo == -1) {
			lblSesionIncorrecta.setVisible(true);
		} else {
			todosPaneles.setLayer(todosPaneles.getComponent(0), 0);
			if (cargo == 0){
				if (sesion.getVecesLogueado()==0) {
					todosPaneles.setLayer(todosPaneles.getComponent(0), 5); // Esto del getComponent es inestable porque los valores van combiando xd
				}
			} else if (cargo == 1){
				todosPaneles.setLayer(todosPaneles.getComponent(1), 5);
			}
		}
	}


}
