package ventanas;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
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
	ArrayList<JButton> todosBotones;
	private boolean[] categorias;
	private Component[] componentesCategorias;
	private String correoEnviar;
	
	public Evento(String accion) {
		super();
		this.accion = accion;
	}
	
	public Evento(String accion, JTextField txtNombre, JTextField txtContrasea, JLabel lblSesionIncorrecta, ArrayList<Usuario> arrayUsuarios, JLayeredPane LayeredPane, ArrayList<JButton> todosBotones) {
		super();
		this.accion = accion;
		this.nombreUsuario = txtNombre;
		this.contraseñaUsuario = txtContrasea;
		this.lblSesionIncorrecta = lblSesionIncorrecta;
		usuarios = arrayUsuarios;
		todosPaneles = LayeredPane;
		this.todosBotones = todosBotones;
	}

	public Evento(String accion, JCheckBox[] checkboxes, JLabel lblCategoriasIncorrectas) {
		super();
		this.accion = accion;
		this.checkboxes = checkboxes;
		this.lblCategoriasIncorrectas = lblCategoriasIncorrectas;
	}

	public Evento(String accion, Component[] components) {
		super();
		this.accion = accion;
		this.componentesCategorias = components;
	}

	public Evento(String accion, String correo) {
		super();
		this.accion = accion;
		this.correoEnviar = correo;
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
		case "mostrar categorias":
			mostrar_categorias_seleccionadas();
			break;
		case "enviar categorias":
			enviar_categorias_seleccionadas();
			break;
		default:
			break;
		}
	}

	private void enviar_categorias_seleccionadas() {
		// TODO Auto-generated method stub
		
	}

	private void mostrar_categorias_seleccionadas() {
		for (int i = 1; i < componentesCategorias.length-1; i++) {
			// TODO si hay alguna noticia de una categoria mostrar el titulo y las noticias favoritas del usuario
			//if(usuarioLogueado.getCategorias()[i]) {
				componentesCategorias[i].setVisible(true);
				System.out.println("Mostrando..."+i);
			//}
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
			todosBotones.get(1).setEnabled(false);
			todosBotones.get(2).setEnabled(true);
//			EscribirTxt.sumarVecesLogueado(usuarioLogueado.getId(), usuarioLogueado.getCategorias());
			// TODO cambiar de panel
		}
		
	}

	private void comprobar_inicio_sesion() {
//		TODO pasar por parametro los Panel para cambiar y los tengo aquí para futuros botones.
		Usuario sesion = new Usuario(-1, nombreUsuario.getText(), null, contraseñaUsuario.getText(), false, null);
		int cargo = sesion.comprobarCredenciales(usuarios);
		if(cargo == -1) {
			lblSesionIncorrecta.setVisible(true);
		} else {
			usuarioLogueado = sesion;
			todosPaneles.setLayer(todosPaneles.getComponent(0), 0);
			if (cargo == 0){
				if (sesion.getCategorias()==null) {
					todosPaneles.setLayer(todosPaneles.getComponent(0), 5); // Esto del getComponent es inestable porque los valores van combiando xd
					todosBotones.get(1).setEnabled(true);
				} else {
					todosPaneles.setLayer(todosPaneles.getComponent(2), 5);
					todosBotones.get(2).setEnabled(true);
					System.out.println("Ya tienes categorias favoritas");
				}
			} else if (cargo == 1){
				// TODO poner los paneles del admin mas arriba y los del usuario mas abajo.
				todosPaneles.setLayer(todosPaneles.getComponent(1), 5);
			}
			todosBotones.get(0).setEnabled(false);
		}
	}


}
