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

/*
 * Trabajo realizado por Arnau Blanch Manero
 */

public class Evento implements ActionListener{
	private String accion;
	static Usuario usuarioLogueado;
	static ArrayList<Usuario> usuarios;
	static JLayeredPane todosPaneles;
	static JTextField nombreUsuario;
	static JPasswordField contraseñaUsuario;
	static JLabel lblSesionIncorrecta;
	static ArrayList<JButton> todosBotones;
	JCheckBox[] checkboxes;
	JLabel lblCategoriasIncorrectas;
	private Component[] componentesCategorias;
	private String correoEnviar;
	private JRadioButton mostrarContrasenia;
	
	public Evento(String accion) {
		// El evento para cerrar sesión desde cualquier panel
		super();
		this.accion = accion;
	}
	
	public Evento(String accion, JTextField txtNombre, JPasswordField pswdContrasea, JLabel lblSesionNoCorrecta, ArrayList<Usuario> arrayUsuarios, JLayeredPane LayeredPane, ArrayList<JButton> todosLosBotones, JCheckBox[] checkboxes) {
		// El evento del inicio de sesión
		super();
		this.accion = accion;
		nombreUsuario = txtNombre;
		contraseñaUsuario = pswdContrasea;
		lblSesionIncorrecta = lblSesionNoCorrecta;
		usuarios = arrayUsuarios;
		todosPaneles = LayeredPane;
		todosBotones = todosLosBotones;
		this.checkboxes = checkboxes;
	}

	public Evento(String accion, JCheckBox[] checkboxes, JLabel lblCategoriasIncorrectas) {
		// El evento de guardar las categorías favoritas
		super();
		this.accion = accion;
		this.checkboxes = checkboxes;
		this.lblCategoriasIncorrectas = lblCategoriasIncorrectas;
	}

	public Evento(String accion, Component[] components) {
		// El evento de mostrar/ocultar los titulares
		super();
		this.accion = accion;
		this.componentesCategorias = components;
	}

	public Evento(String accion, String correo) {
		super();
		this.accion = accion;
		this.correoEnviar = correo;
	}

	public Evento(String accion, JRadioButton mostrarContrasenia,JPasswordField pwdContrasenia) {
		// El evento para mostrar/ocultar la contraseña
		super();
		this.accion = accion;
		this.mostrarContrasenia = mostrarContrasenia;
		contraseñaUsuario = pwdContrasenia;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Hago un switch case para que dependiendo de la acción, haga una función u otra
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
		// Si está el texto en claro lo oculto con •
		if(contraseñaUsuario.getEchoChar() == 0) {
			contraseñaUsuario.setEchoChar('•');
			mostrarContrasenia.setText("Mostrar");
		} else {
			// Para poner el texto en claro hay que ponerlo a 0
			contraseñaUsuario.setEchoChar((char)0);
			mostrarContrasenia.setText("Esconder");
		}
	}

	private void cerrar_sesion() {
		// Muestro el panel del login y ordeno el resto para el siguiente inicio de sesión
		usuarioLogueado = null;
		// TODO No se ni cómo funciona pero si funciona no lo toco
		todosPaneles.setLayer(todosPaneles.getComponent(0), 4); // El que está arriba, lo mando para atrás
		todosPaneles.setLayer(todosPaneles.getComponent(1), 3); // El segundo, lo pongo segundo?
		todosPaneles.setLayer(todosPaneles.getComponent(2), 2); // El tercero, lo pongo casi arriba o arriba del todo
		todosPaneles.setLayer(todosPaneles.getComponent(3), 5); // El último lo pongo detrás del todo?
		
		// Habilito el botón de iniciar sesión y deshabilito el resto
		todosBotones.get(0).setEnabled(true);
		for (int i = 1; i < todosBotones.size(); i++) {
			todosBotones.get(i).setEnabled(false);
		}
		
		// Reseteo los campos
		nombreUsuario.setText("");
		contraseñaUsuario.setText("");
		lblSesionIncorrecta.setVisible(false);
	}

	private void enviar_categorias_seleccionadas() {
		// TODO Enviar correo con las categorías del usuario
		
	}

	private void mostrar_categorias_seleccionadas() {
		// Esta función muestra o esconde las noticias
		int i; // El orden de todos los componentes del panel
		int j = 0; // El orden de las categorías del usuario
		// Esta variable determina la altura en la que se coloca la información para que no haya huecos en blanco
		int y = 20;
		componentesCategorias[0].setVisible(!componentesCategorias[0].isVisible()); // El título "Tus noticias"
		for (i = 1; i < componentesCategorias.length-4; i++) {
			
			// Si el usuario tiene alguna de las tres noticias, se muestra su titular
			if(usuarioLogueado.getCategorias()[j] || usuarioLogueado.getCategorias()[j+1] || usuarioLogueado.getCategorias()[j+2]) {
				componentesCategorias[i].setVisible(!componentesCategorias[i].isVisible()); // El título de la categoría
				componentesCategorias[i].setBounds(30, y, componentesCategorias[i].getWidth(), componentesCategorias[i].getHeight()); // Su posición
				y += componentesCategorias[i].getHeight(); // Aumento la altura para el siguiente componente
			}
			
			i++; // Paso a la primera noticia de esa categoría
			if(usuarioLogueado.getCategorias()[j]) {
				componentesCategorias[i].setVisible(!componentesCategorias[i].isVisible());
				componentesCategorias[i].setBounds(30, y, componentesCategorias[i].getWidth(), componentesCategorias[i].getHeight());
				y += componentesCategorias[i].getHeight();
			}
			
			j++;
			i++; // Paso a la segunda noticia de esa categoría
			if(usuarioLogueado.getCategorias()[j]) {
				componentesCategorias[i].setVisible(!componentesCategorias[i].isVisible());
				componentesCategorias[i].setBounds(30, y, componentesCategorias[i].getWidth(), componentesCategorias[i].getHeight());
				y += componentesCategorias[i].getHeight();
			}
			
			j++;
			i++; // Paso a la tercera noticia de esa categoría
			if(usuarioLogueado.getCategorias()[j]) {
				componentesCategorias[i].setVisible(!componentesCategorias[i].isVisible());
				componentesCategorias[i].setBounds(30, y, componentesCategorias[i].getWidth(), componentesCategorias[i].getHeight());
				y += componentesCategorias[i].getHeight();
			}
			
			j++; // Aumento para estar posicionado en la primera noticia de la siguiente categoría
		}
		
		// Cuando ya he mostrado/ocultado los titulares, tengo que mostrar/ocultar y habilitar/deshabilitar algunos botones
			// Botón Mostrar
		componentesCategorias[i].setVisible(!componentesCategorias[i].isVisible());
		componentesCategorias[i].setEnabled(!componentesCategorias[i].isEnabled());
		i++; // Botón Atrás
		componentesCategorias[i].setVisible(!componentesCategorias[i].isVisible());
		componentesCategorias[i].setEnabled(!componentesCategorias[i].isEnabled());
		i++; // Botón Guardar
		componentesCategorias[i].setVisible(!componentesCategorias[i].isVisible());
		componentesCategorias[i].setEnabled(!componentesCategorias[i].isEnabled());
		i++; // Botón Cerrar sesión
		componentesCategorias[i].setVisible(!componentesCategorias[i].isVisible());
		componentesCategorias[i].setEnabled(!componentesCategorias[i].isEnabled());
	}

	private void comprobar_categorias_seleccionadas() {
		int cantidadCategorias = 0;
		boolean [] periodicosUsuario = new boolean[Ventana.CANTIDAD_CATEGORIAS];
		
		// Voy guardando los periódicos que el usuario ha seleccionado y compruebo que al menos haya seleccionado uno
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
			// Escribo en el txt las categorías que ha seleccionado
			if(EscribirTxt.escribirCategorias(usuarioLogueado.getId(), periodicosUsuario)) {
				usuarioLogueado.setCategorias(periodicosUsuario);
				todosBotones.get(1).setEnabled(false); // Deshabilito el botón que guarda las categorías favoritas del usuario
				todosBotones.get(2).setEnabled(true); // Habilito el botón que muestra los titulares
				// TODO cambiar de panel
			}
		}
		
	}

	private void comprobar_inicio_sesion() {
		
		// Creo un usuario con el nombre y la contraseña
		Usuario sesion = new Usuario(-1, nombreUsuario.getText(), null, contraseñaUsuario.getText(), false, null);
		
		// Si no encuentra el usuario me devuelve -1
		int cargo = sesion.comprobarCredenciales(usuarios);
		
		if(cargo == -1) {
			lblSesionIncorrecta.setVisible(true);
			
		} else {
			// Si encuentra el usuario lo guardo para poder consultar sus datos en otras funciones
			usuarioLogueado = sesion;
			
			// Ordeno los paneles según el cargo del usuario
			todosPaneles.setLayer(todosPaneles.getComponent(0), 0); // El panel de inicio de sesión lo pongo al fondo
			if (cargo == 0){
				if (sesion.getCategorias()==null) {
					// TODO Esto del getComponent es inestable porque los valores van combiando xd
					todosPaneles.setLayer(todosPaneles.getComponent(0), 5); // El panel donde el usuario elige sus categorías favoritas lo pongo arriba (ya es el primero)
					todosBotones.get(1).setEnabled(true); // Habilito el botón de guardar las categorías
					todosBotones.get(6).setEnabled(true); // Habilito el botón de cerrar sesión
					
					// Configuro todos los checkboxes para que se puedan seleccionar y no haya ninguno seleccionado
					for (JCheckBox chck : checkboxes) {
						chck.setEnabled(true);
						chck.setSelected(false);
					}
					
				} else {
					
					todosPaneles.setLayer(todosPaneles.getComponent(2), 5); // El panel del usuario donde ve los titulares lo pongo arriba
					todosBotones.get(2).setEnabled(true); // Habilito el botón de mostrar categorías
					todosBotones.get(5).setEnabled(true); // Habilito el botón de cerrar sesión
					
				}
			} else if (cargo == 1){
				// TODO poner los paneles del admin mas arriba y los del usuario mas abajo.
				todosPaneles.setLayer(todosPaneles.getComponent(1), 5); // El panel principal del admin lo pongo arriba
				todosBotones.get(7).setEnabled(true); // Habilito el botón de cerrar sesión
			}
			
			todosBotones.get(0).setEnabled(false); // Deshabilito el botón de iniciar sesión
		}
	}


}
