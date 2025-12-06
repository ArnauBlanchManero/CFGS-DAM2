package ventanas;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import txt.EscribirTxt;
import usuarios.Usuario;

public class Evento implements ActionListener{
	private String accion;
	static Usuario usuarioLogueado;
	static ArrayList<Usuario> usuarios;
	static JLayeredPane todosPaneles;
	static JTextField nombreUsuario;
	static JPasswordField contraseñaUsuario;
	static JLabel lblSesionIncorrecta;
	JCheckBox[] checkboxes;
	JLabel lblCategoriasIncorrectas;
	ArrayList<JButton> todosBotones;
	private Component[] componentesCategorias;
	private String correoEnviar;
	private JRadioButton mostrarContrasenia;
	
	public Evento(String accion) {
		super();
		this.accion = accion;
	}
	
	public Evento(String accion, JTextField txtNombre, JPasswordField pswdContrasea, JLabel lblSesionNoCorrecta, ArrayList<Usuario> arrayUsuarios, JLayeredPane LayeredPane, ArrayList<JButton> todosBotones, JCheckBox[] checkboxes) {
		super();
		this.accion = accion;
		nombreUsuario = txtNombre;
		contraseñaUsuario = pswdContrasea;
		lblSesionIncorrecta = lblSesionNoCorrecta;
		usuarios = arrayUsuarios;
		todosPaneles = LayeredPane;
		this.todosBotones = todosBotones;
		this.checkboxes = checkboxes;
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

	public Evento(String accion, ArrayList<JButton> todosBotones) {
		super();
		this.accion = accion;
		this.todosBotones = todosBotones;
	}

	public Evento(String accion, JRadioButton mostrarContrasenia,JPasswordField pwdContrasenia) {
		super();
		this.accion = accion;
		this.mostrarContrasenia = mostrarContrasenia;
		contraseñaUsuario = pwdContrasenia;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (accion) {
		case "mostrar ocultar contraseña":
			mostrar_ocultar_contrasenia();
			break;
		case "comprobar sesion":
			comprobar_inicio_sesion();
			break;
		case "guardar categorias":
			comprobar_categorias_seleccionadas();
			break;
		case "mostrar categorias":
			mostrar_categorias_seleccionadas();
			break;
		case "cerrar sesion":
			cerrar_sesion();
			break;
		case "enviar categorias":
			enviar_categorias_seleccionadas();
			break;
		default:
			break;
		}
	}

	private void mostrar_ocultar_contrasenia() {
		if(contraseñaUsuario.getEchoChar() == 0) {
			contraseñaUsuario.setEchoChar('•');
			mostrarContrasenia.setText("Mostrar");
		} else {
			contraseñaUsuario.setEchoChar((char)0);
			mostrarContrasenia.setText("Esconder");
		}
	}

	private void cerrar_sesion() {
		// TODO Auto-generated method stub
		usuarioLogueado = null;
		todosPaneles.setLayer(todosPaneles.getComponent(0), 4);
		todosPaneles.setLayer(todosPaneles.getComponent(1), 3);
		todosPaneles.setLayer(todosPaneles.getComponent(2), 2);
		todosPaneles.setLayer(todosPaneles.getComponent(3), 5);
		todosBotones.get(0).setEnabled(true);
		for (int i = 1; i < todosBotones.size(); i++) {
			todosBotones.get(i).setEnabled(false);
		}
		nombreUsuario.setText("");
		contraseñaUsuario.setText("");
		lblSesionIncorrecta.setVisible(false);
	}

	private void enviar_categorias_seleccionadas() {
		// TODO Auto-generated method stub
		
	}

	private void mostrar_categorias_seleccionadas() {
		int y = 20;
		int i, j = 0;
		componentesCategorias[0].setVisible(!componentesCategorias[0].isVisible());
		for (i = 1; i < componentesCategorias.length-4; i++) {
			if(usuarioLogueado.getCategorias()[j] || usuarioLogueado.getCategorias()[j+1] || usuarioLogueado.getCategorias()[j+2]) {
				componentesCategorias[i].setVisible(!componentesCategorias[i].isVisible());
				componentesCategorias[i].setBounds(30, y, componentesCategorias[i].getWidth(), componentesCategorias[i].getHeight());
				y += componentesCategorias[i].getHeight();
				System.out.println("Mostrando..."+i);
			}
			i++;
			if(usuarioLogueado.getCategorias()[j]) {
				componentesCategorias[i].setVisible(!componentesCategorias[i].isVisible());
				componentesCategorias[i].setBounds(30, y, componentesCategorias[i].getWidth(), componentesCategorias[i].getHeight());
				y += componentesCategorias[i].getHeight();
				System.out.println("Mostrando..."+i);
			}
			j++;
			i++;
			if(usuarioLogueado.getCategorias()[j]) {
				componentesCategorias[i].setVisible(!componentesCategorias[i].isVisible());
				componentesCategorias[i].setBounds(30, y, componentesCategorias[i].getWidth(), componentesCategorias[i].getHeight());
				y += componentesCategorias[i].getHeight();
				System.out.println("Mostrando..."+i);
			}
			j++;
			i++;
			if(usuarioLogueado.getCategorias()[j]) {
				componentesCategorias[i].setVisible(!componentesCategorias[i].isVisible());
				componentesCategorias[i].setBounds(30, y, componentesCategorias[i].getWidth(), componentesCategorias[i].getHeight());
				y += componentesCategorias[i].getHeight();
				System.out.println("Mostrando..."+i);
			}
			j++;
		}
		componentesCategorias[i].setVisible(!componentesCategorias[i].isVisible());
		componentesCategorias[i].setEnabled(!componentesCategorias[i].isEnabled());
		i++;
		componentesCategorias[i].setVisible(!componentesCategorias[i].isVisible());
		componentesCategorias[i].setEnabled(!componentesCategorias[i].isEnabled());
		i++;
		componentesCategorias[i].setVisible(!componentesCategorias[i].isVisible());
		componentesCategorias[i].setEnabled(!componentesCategorias[i].isEnabled());
		i++;
		componentesCategorias[i].setVisible(!componentesCategorias[i].isVisible());
		componentesCategorias[i].setEnabled(!componentesCategorias[i].isEnabled());
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
					todosBotones.get(6).setEnabled(true);
					for (JCheckBox chck : checkboxes) {
						chck.setEnabled(true);
						chck.setSelected(false);
					}
				} else {
					todosPaneles.setLayer(todosPaneles.getComponent(2), 5);
					todosBotones.get(2).setEnabled(true);
					todosBotones.get(5).setEnabled(true);
					System.out.println("Ya tienes categorias favoritas");
				}
			} else if (cargo == 1){
				// TODO poner los paneles del admin mas arriba y los del usuario mas abajo.
				todosPaneles.setLayer(todosPaneles.getComponent(1), 5);
				todosBotones.get(7).setEnabled(true);
			}
			todosBotones.get(0).setEnabled(false);
		}
	}


}
