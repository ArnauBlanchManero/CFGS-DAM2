package ventanas;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;

import txt.EscribirTxt;
import usuarios.Usuario;

public class Evento implements ActionListener{
	private String accion;
	static Usuario usuarioLogueado;
	static ArrayList<Usuario> usuarios;
	static JLayeredPane todosPaneles;
	JTextField nombreUsuario;
	JTextField contraseñaUsuario;
	JLabel lblSesionIncorrecta;
	JCheckBox[] checkboxes;
	JLabel lblCategoriasIncorrectas;
	
	public Evento(String accion, JLayeredPane LayeredPane) {
		super();
		this.accion = accion;
		todosPaneles = LayeredPane;
	}
	
	public Evento(String accion, JTextField txtNombre, JTextField txtContrasea, JLabel lblSesionIncorrecta, ArrayList<Usuario> arrayUsuarios, JLayeredPane LayeredPane) {
		super();
		this.accion = accion;
		this.nombreUsuario = txtNombre;
		this.contraseñaUsuario = txtContrasea;
		this.lblSesionIncorrecta = lblSesionIncorrecta;
		usuarios = arrayUsuarios;
		todosPaneles = LayeredPane;
	}

	public Evento(String accion, JCheckBox[] checkboxes, JLabel lblCategoriasIncorrectas) {
		super();
		this.accion = accion;
		this.checkboxes = checkboxes;
		this.lblCategoriasIncorrectas = lblCategoriasIncorrectas;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (accion) {
		case "comprobar sesion":
			comprobar_inicio_sesion();
			break;
		case "guardar categorias":
			comprobar_categorias_seleccionadas();
			break;
		default:
			break;
		}
	}

	private void comprobar_categorias_seleccionadas() {
		int cantidadCategorias = 0;
		boolean [] periodicosUsuario = new boolean[Ventana.CANTIDAD_CATEGORIAS];
		for (int i = 0; i < Ventana.CANTIDAD_CATEGORIAS; i++) {
			if(checkboxes[i].isSelected()) {
				periodicosUsuario[i] = true;
				cantidadCategorias++;
			} else {
				periodicosUsuario[i] = false;
			}
		}
		if(cantidadCategorias == 0) {
			lblCategoriasIncorrectas.setVisible(true);
		} else {
			System.out.println(usuarioLogueado.getNombre());
			usuarioLogueado.setCategorias(periodicosUsuario);
			// TODO guardar periodicos favoritos
			EscribirTxt.escribirCategorias(usuarioLogueado.getId(), periodicosUsuario);
			// TODO cambiar de panel
		}
		
	}

	private void comprobar_inicio_sesion() {
//		TODO pasar por parametro los Panel para cambiar y los tengo aquí para futuros botones.
		Usuario sesion = new Usuario(-1, nombreUsuario.getText(), null, contraseñaUsuario.getText(), false, 0, null);
		int cargo = sesion.comprobarCredenciales(usuarios);
		if(cargo == -1) {
			lblSesionIncorrecta.setVisible(true);
		} else {
			usuarioLogueado = sesion;
			todosPaneles.setLayer(todosPaneles.getComponent(0), 0);
			if (cargo == 0){
				if (sesion.getVecesLogueado()==0) {
					todosPaneles.setLayer(todosPaneles.getComponent(0), 5); // Esto del getComponent es inestable porque los valores van combiando xd
				}
			} else if (cargo == 1){
				todosPaneles.setLayer(todosPaneles.getComponent(1), 5);
			}
		}
		//TODO devolver los datos del usuario que ha iniciado sesion?
		//return sesion;
	}


}
