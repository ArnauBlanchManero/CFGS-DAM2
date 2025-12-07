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
//	private String correoEnviar;
	private JRadioButton mostrarContrasenia;
	private ArrayList<String> titulares;
	private JTextField txtNombreAñadir;
	private JTextField txtContraseñaAñadir;
	private JTextField txtCorreoAñadir;
	private JLabel lblDatosAñadirIncorrectos;
	private JLabel lblNombreAñadir;
	private JLabel lblContraseñaAñadir;
	private JLabel lblCorreoAñadir;
	private JTextField txtNombreEliminar;
	private JLabel lblNombreEliminarIncorrecto;
	private JLabel lblNombreEliminar;
	
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
/*
	public Evento(String accion, String correo) {
		super();
		this.accion = accion;
		this.correoEnviar = correo;
	}
*/
	public Evento(String accion, JRadioButton mostrarContrasenia,JPasswordField pwdContrasenia) {
		// El evento para mostrar/ocultar la contraseña
		super();
		this.accion = accion;
		this.mostrarContrasenia = mostrarContrasenia;
		contraseñaUsuario = pwdContrasenia;
	}

	public Evento(String accion, ArrayList<String> todosTitulares) {
		// Para enviar los titulares al correo
		super();
		this.accion = accion;
		titulares = todosTitulares;
	}

	public Evento(String accion, JTextField txtNombreAñadir, JTextField txtContraseaAñadir, JTextField txtCorreoAñadir, JLabel lblDatosAñadirIncorrectos, JLabel lblNombreAñadir, JLabel lblContraseñaAñadir, JLabel lblCorreoAñadir) {
		// Añadir usuario
		super();
		this.accion = accion;
		this.txtNombreAñadir = txtNombreAñadir;
		this.txtContraseñaAñadir = txtContraseaAñadir;
		this.txtCorreoAñadir = txtCorreoAñadir;
		this.lblDatosAñadirIncorrectos = lblDatosAñadirIncorrectos;
		this.lblNombreAñadir = lblNombreAñadir;
		this.lblContraseñaAñadir = lblContraseñaAñadir;
		this.lblCorreoAñadir = lblCorreoAñadir;
		
	}

	public Evento(String accion, JTextField txtNombreEliminar, JLabel lblNombreEliminarIncorrecto, JLabel lblNombreEliminar) {
		// Elimiar usuario
		super();
		this.accion = accion;
		this.txtNombreEliminar = txtNombreEliminar;
		this.lblNombreEliminarIncorrecto = lblNombreEliminarIncorrecto;
		this.lblNombreEliminar = lblNombreEliminar;
	}

	public Evento(String accion, JTextField txtNombreAñadir, JTextField txtContraseaAñadir, JTextField txtCorreoAñadir, JLabel lblDatosAñadirIncorrectos, JLabel lblNombreAñadir, JLabel lblContraseñaAñadir, JLabel lblCorreoAñadir, JTextField txtNombreEliminar, JLabel lblNombreEliminarIncorrecto, JLabel lblNombreEliminar) {
		// Cancelar añadir/eliminar usuario
		super();
		this.accion = accion;
		this.txtNombreAñadir = txtNombreAñadir;
		this.txtContraseñaAñadir = txtContraseaAñadir;
		this.txtCorreoAñadir = txtCorreoAñadir;
		this.lblDatosAñadirIncorrectos = lblDatosAñadirIncorrectos;
		this.lblNombreAñadir = lblNombreAñadir;
		this.lblContraseñaAñadir = lblContraseñaAñadir;
		this.lblCorreoAñadir = lblCorreoAñadir;
		this.txtNombreEliminar = txtNombreEliminar;
		this.lblNombreEliminarIncorrecto = lblNombreEliminarIncorrecto;
		this.lblNombreEliminar = lblNombreEliminar;
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
			case "gestion usuarios":
				gestionar_usuarios();
				break;
			case "atras admin":
				atras_admin();
				break;
			case "añadir usuario":
				preparar_aniadir_usuario();
				break;
			case "eliminar usuario":
				preparar_eliminar_usuario();
				break;
			case "esconder info":
				cancelar_añadir_eliminar();
				break;
			case "aceptar info":
				aceptar_añadir_eliminar();
				break;
			default:
				break;
		}
	}

	private void aceptar_añadir_eliminar() {
		// TODO Añado o elimino al usuario
		if(txtNombreAñadir.isVisible()) {
			añadir_usuario(txtNombreAñadir.getText(), txtContraseñaAñadir.getText(), txtCorreoAñadir.getText());
		} else if(txtNombreEliminar.isVisible()) {
			eliminar_usuario(txtNombreEliminar.getText());
		}
	}

	private void añadir_usuario(String nombre, String contraseña, String correo) {
		// TODO Comprobar que ningun atributo tenga el patrón que uso para dividirlos en el txt ·!·
		// TODO Comprobar que el nombre no esté repetido
		// TODO Comprobar que haya espacio para otro usuario
		// TODO Buscar un id para el usuario
		// TODO Escribir en el txt de usuarios
	}

	private void eliminar_usuario(String nombre) {
		// TODO Pillar el id del usuario con ese nombre
		// TODO Comprobar que se pueda eliminar (al menos 1 admin y 3 usuarios)
		// TODO Eliminar el usuario con ese id en el fichero de usuarios y en el de categorías favoritas
	}

	private void cancelar_añadir_eliminar() {
		// Cancelar proceso de añadir o eliminar un usuario
		
		// Oculto todos los JTextField
		txtNombreAñadir.setVisible(false);
		txtContraseñaAñadir.setVisible(false);
		txtCorreoAñadir.setVisible(false);
		txtNombreEliminar.setVisible(false);
		
		// Oculto los JLabel
		lblNombreEliminar.setVisible(false);
		lblNombreAñadir.setVisible(false);
		lblContraseñaAñadir.setVisible(false);
		lblCorreoAñadir.setVisible(false);
		
		// Muestro los botones del panel
		todosBotones.get(9).setEnabled(true);
		todosBotones.get(9).setVisible(true);
		todosBotones.get(10).setEnabled(true);
		todosBotones.get(10).setVisible(true);
		todosBotones.get(12).setEnabled(true);
		todosBotones.get(12).setVisible(true);
		todosBotones.get(11).setEnabled(true);
		todosBotones.get(11).setVisible(true);
		
		// Oculto los botones de Aceptar y Cancelar
		todosBotones.get(13).setEnabled(false);
		todosBotones.get(13).setVisible(false);
		todosBotones.get(14).setEnabled(false);
		todosBotones.get(14).setVisible(false);
	}

	private void preparar_eliminar_usuario() {
		// Configuro el panel para eliminar un usuario
		
		// Muestro el JTextField y el JLabel del nombre
		txtNombreEliminar.setVisible(true);
		txtNombreEliminar.setText("");
		
		lblNombreEliminar.setVisible(true);
		
		// Oculto los botones que no quiero que el usuario pulse
		todosBotones.get(9).setEnabled(false);
		todosBotones.get(9).setVisible(false);
		todosBotones.get(10).setEnabled(false);
		todosBotones.get(10).setVisible(false);
		todosBotones.get(11).setEnabled(false);
		todosBotones.get(11).setVisible(false);
		
		// Muestro los botones de Aceptar y Cancelar
		todosBotones.get(13).setEnabled(true);
		todosBotones.get(13).setVisible(true);
		todosBotones.get(14).setEnabled(true);
		todosBotones.get(14).setVisible(true);
	}

	private void preparar_aniadir_usuario() {
		// Configuro el panel para añadir un usuario
		
		// Muestro los JTextField y los JLabel 
		txtNombreAñadir.setVisible(true);
		txtNombreAñadir.setText("");
		txtContraseñaAñadir.setVisible(true);
		txtContraseñaAñadir.setText("");
		txtCorreoAñadir.setVisible(true);
		txtCorreoAñadir.setText("");
		
		lblNombreAñadir.setVisible(true);
		lblContraseñaAñadir.setVisible(true);
		lblCorreoAñadir.setVisible(true);

		// Oculto los botones que no quiero que el usuario pulse
		todosBotones.get(9).setEnabled(false);
		todosBotones.get(9).setVisible(false);
		todosBotones.get(10).setEnabled(false);
		todosBotones.get(10).setVisible(false);
		todosBotones.get(12).setEnabled(false);
		todosBotones.get(12).setVisible(false);

		// Muestro los botones de Aceptar y Cancelar
		todosBotones.get(13).setEnabled(true);
		todosBotones.get(13).setVisible(true);
		todosBotones.get(14).setEnabled(true);
		todosBotones.get(14).setVisible(true);
	}

	private void atras_admin() {
		todosPaneles.setLayer(todosPaneles.getComponent(0), 6); // Muevo el panel de gestion de usuarios hacia atrás para que se vea el panel de administrador
		todosBotones.get(7).setEnabled(true); // Habilito el botón de cerrar sesion
		todosBotones.get(8).setEnabled(true); // Habilito el botón de gestionar usuarios
		todosBotones.get(9).setEnabled(false); // Deshabilito el botón de cerrar sesión del panel de gestion de usuarios
		todosBotones.get(10).setEnabled(false); // Deshabilito el botón de Atrás
		todosBotones.get(11).setEnabled(false); // Deshabilito el botón de Añadir usuarios
		todosBotones.get(12).setEnabled(false); // Deshabilito el botón de Eliminar usuarios
		
	}

	private void gestionar_usuarios() {
		todosPaneles.setLayer(todosPaneles.getComponent(1), 8); // Muevo el panel de gestion de usuarios arriba del todo
		todosBotones.get(7).setEnabled(false); // Deshabilito el botón de cerrar sesion
		todosBotones.get(8).setEnabled(false); // Deshabilito el botón de gestionar usuarios
		todosBotones.get(9).setEnabled(true); // Habilito el botón de cerrar sesión del panel de gestion de usuarios
		todosBotones.get(10).setEnabled(true); // Habilito el botón de Atrás
		todosBotones.get(11).setEnabled(true); // Habilito el botón de Añadir usuarios
		todosBotones.get(12).setEnabled(true); // Habilito el botón de Eliminar usuarios

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
		// Ordenar los paneles para que sea como en el inicio de sesión original
		for (int i = 0; i < todosPaneles.getComponentCount(); i++) {
			if (todosPaneles.getComponent(i).getName().equals("Inicio")) {
				todosPaneles.setLayer(todosPaneles.getComponent(i), 14);
			}
		}
		for (int i = 0; i < todosPaneles.getComponentCount(); i++) {
			if (todosPaneles.getComponent(i).getName().equals("Favoritos")) {
				todosPaneles.setLayer(todosPaneles.getComponent(i), 13);
			}
		}
		for (int i = 0; i < todosPaneles.getComponentCount(); i++) {
			if (todosPaneles.getComponent(i).getName().equals("Titulares")) {
				todosPaneles.setLayer(todosPaneles.getComponent(i), 12);
			}
		}
		for (int i = 0; i < todosPaneles.getComponentCount(); i++) {
			if (todosPaneles.getComponent(i).getName().equals("Admin")) {
				todosPaneles.setLayer(todosPaneles.getComponent(i), 11);
			}
		}
		for (int i = 0; i < todosPaneles.getComponentCount(); i++) {
			if (todosPaneles.getComponent(i).getName().equals("Gestion")) {
				todosPaneles.setLayer(todosPaneles.getComponent(i), 10);
			}
		}
		// Los pongo entre las capas 1 y 5 para que las capas en todos los inicios de sesión sean el mismo
		todosPaneles.setLayer(todosPaneles.getComponent(4), 1);
		todosPaneles.setLayer(todosPaneles.getComponent(3), 2);
		todosPaneles.setLayer(todosPaneles.getComponent(2), 3);
		todosPaneles.setLayer(todosPaneles.getComponent(1), 4);
		todosPaneles.setLayer(todosPaneles.getComponent(0), 5);

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
		lblCategoriasIncorrectas.setVisible(false);
		
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
				todosBotones.get(5).setEnabled(true); // Habilito el botón de cerrar sesión
				// Configuro todos los checkboxes para que no se puedan seleccionar y no haya ninguno seleccionado
				for (JCheckBox chck : checkboxes) {
					chck.setEnabled(false);
					chck.setSelected(false);
				}
				// Cambio al panel del usuario para mostrar los titulares
				todosPaneles.setLayer(todosPaneles.getComponent(3), 7); // Ni idea de lo que hago con los paneles
			}
		}
		
	}

	private void comprobar_inicio_sesion() {
		
		// Creo un usuario con el nombre y la contraseña
		Usuario sesion = new Usuario(-1, nombreUsuario.getText(), null, contraseñaUsuario.getText(), false, null); // Es una mala práctica usar getText en la contraseña
		
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
					todosPaneles.setLayer(todosPaneles.getComponent(0), 6); // El panel donde el usuario elige sus categorías favoritas lo pongo arriba (ya es el primero)
					todosBotones.get(1).setEnabled(true); // Habilito el botón de guardar las categorías
					todosBotones.get(6).setEnabled(true); // Habilito el botón de cerrar sesión
					
					// Configuro todos los checkboxes para que se puedan seleccionar y no haya ninguno seleccionado
					for (JCheckBox chck : checkboxes) {
						chck.setEnabled(true);
						chck.setSelected(false);
					}
					
				} else {

					todosPaneles.setLayer(todosPaneles.getComponent(3), 6); // El panel del usuario donde ve los titulares lo pongo arriba
					todosBotones.get(2).setEnabled(true); // Habilito el botón de mostrar categorías
					todosBotones.get(5).setEnabled(true); // Habilito el botón de cerrar sesión
					
				}
			} else if (cargo == 1){
				// TODO poner los paneles del admin mas arriba y los del usuario mas abajo.
				todosPaneles.setLayer(todosPaneles.getComponent(1), 7); // El panel principal del admin lo pongo arriba
				todosPaneles.setLayer(todosPaneles.getComponent(2), 6); // El panel de gestionar usuarios justo debajo
				todosBotones.get(7).setEnabled(true); // Habilito el botón de cerrar sesión
				todosBotones.get(8).setEnabled(true); // Habilito el botón de gestion de usuarios
			}
			
			todosBotones.get(0).setEnabled(false); // Deshabilito el botón de iniciar sesión
		}
	}


}
