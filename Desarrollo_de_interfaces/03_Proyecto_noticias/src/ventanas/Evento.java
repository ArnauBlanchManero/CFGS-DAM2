package ventanas;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import correos.Email;
import txt.EscribirTxt;
import txt.LeerTxt;
import usuarios.Usuario;

/*
 * Trabajo realizado por Arnau Blanch Manero
 */

public class Evento implements ActionListener{
	private String accion;
	public static Usuario usuarioLogueado;
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
	private JLabel lblDatosAñadirCorrectos;
	private JLabel lblNombreEliminarCorrecto;
	
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

	public Evento(String accion, JTextField txtNombreAñadir, JTextField txtContraseaAñadir, JTextField txtCorreoAñadir, JLabel lblDatosAñadirIncorrectos, JLabel lblNombreAñadir, JLabel lblContraseñaAñadir, JLabel lblCorreoAñadir, JLabel lblDatosAñadirCorrectos) {
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
		this.lblDatosAñadirCorrectos = lblDatosAñadirCorrectos;
		
	}

	public Evento(String accion, JTextField txtNombreEliminar, JLabel lblNombreEliminarIncorrecto, JLabel lblNombreEliminar, JLabel lblNombreEliminarCorrecto) {
		// Elimiar usuario
		super();
		this.accion = accion;
		this.txtNombreEliminar = txtNombreEliminar;
		this.lblNombreEliminarIncorrecto = lblNombreEliminarIncorrecto;
		this.lblNombreEliminar = lblNombreEliminar;
		this.lblNombreEliminarCorrecto = lblNombreEliminarCorrecto;
	}

	public Evento(String accion, JTextField txtNombreAñadir, JTextField txtContraseaAñadir, JTextField txtCorreoAñadir, JLabel lblDatosAñadirIncorrectos, JLabel lblNombreAñadir, JLabel lblContraseñaAñadir, JLabel lblCorreoAñadir, JTextField txtNombreEliminar, JLabel lblNombreEliminarIncorrecto, JLabel lblNombreEliminar, JLabel lblDatosAñadirCorrectos, JLabel lblNombreEliminarCorrecto) {
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
		this.lblDatosAñadirCorrectos = lblDatosAñadirCorrectos;
		this.lblNombreEliminarCorrecto = lblNombreEliminarCorrecto;
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
			case "guardar titulares":
				guardar_categorias_seleccionadas();
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
			case "mostrar categorias admin":
				mostrar_titlares_admin();
				break;
			case "atras admin titulares":
				atras_titlares_admin();
				break;
			case "mostrar hora":
				mostrar_hora();
				break;
			default:
				break;
		}
	}


	private void mostrar_hora() {
		// Muestra la hora
		JOptionPane.showMessageDialog(null, "La hora del envío automático es: "+LeerTxt.leerHora(), "HORA", 3);
	}

	private void atras_titlares_admin() {

		// Lo vuelvo a colocar detrás del panel del admin y del gestor de usuarios
		todosPaneles.setLayer(todosPaneles.getComponent(0), 6);

		todosBotones.get(15).setVisible(true);
		todosBotones.get(15).setEnabled(true);
		todosBotones.get(17).setVisible(true);
		todosBotones.get(17).setEnabled(true);
		todosBotones.get(16).setVisible(false);
		todosBotones.get(16).setEnabled(false);
		todosBotones.get(18).setVisible(false);
		todosBotones.get(18).setEnabled(false);
		todosBotones.get(4).setVisible(true);
		todosBotones.get(4).setEnabled(true);
		todosBotones.get(3).setVisible(true);
		todosBotones.get(3).setEnabled(true);
		mostrar_categorias_seleccionadas();
	}

	private void mostrar_titlares_admin() {

		// Pongo el panel de mostrar los titulares delante del todo
		todosPaneles.setLayer(todosPaneles.getComponent(2), 10);


		todosBotones.get(15).setVisible(false);
		todosBotones.get(15).setEnabled(false);
		todosBotones.get(17).setVisible(false);
		todosBotones.get(17).setEnabled(false);
		todosBotones.get(16).setVisible(true);
		todosBotones.get(16).setEnabled(true);
		todosBotones.get(18).setVisible(true);
		todosBotones.get(18).setEnabled(true);
		mostrar_categorias_seleccionadas();
		todosBotones.get(4).setVisible(false);
		todosBotones.get(4).setEnabled(false);
		todosBotones.get(3).setVisible(false);
		todosBotones.get(3).setEnabled(false);
	}

	private void aceptar_añadir_eliminar() {
		// Añado o elimino al usuario
		if(txtNombreAñadir.isVisible()) {
			añadir_usuario(txtNombreAñadir.getText(), txtContraseñaAñadir.getText(), txtCorreoAñadir.getText());
		} else if(txtNombreEliminar.isVisible()) {
			eliminar_usuario(txtNombreEliminar.getText());
		}
	}

	private void añadir_usuario(String nombre, String contraseña, String correo) {
		lblDatosAñadirCorrectos.setVisible(false);
		lblDatosAñadirIncorrectos.setVisible(false);
		// Comprobar que los campos no estén vacíos
		if (nombre.isEmpty() || nombre.isBlank() || contraseña.isEmpty() || contraseña.isBlank() || correo.isEmpty() || correo.isBlank()) {
			lblDatosAñadirIncorrectos.setText("Por favor, rellena los campos");
			lblDatosAñadirIncorrectos.setVisible(true);
		} else
			// Comprobar que ningun atributo tenga el patrón que uso para dividirlos en el txt ·!·
			if(nombre.contains("!") || contraseña.contains("!") || correo.contains("!")) {
				lblDatosAñadirIncorrectos.setText("Ningún campo puede tener el caracter '!'");
				lblDatosAñadirIncorrectos.setVisible(true);
			} else
				// Comprobar que el nombre no esté repetido
				if(nombre_repetido(nombre)) {
					lblDatosAñadirIncorrectos.setText("No puede usar ese nombre");
					lblDatosAñadirIncorrectos.setVisible(true);
				} else
					// Comprobar que haya espacio para otro usuario
					if (usuarios.size()>10) {
						lblDatosAñadirIncorrectos.setText("No puedes añadir más usuarios");
						lblDatosAñadirIncorrectos.setVisible(true);
					} else {
						// Buscar un id para el usuario
						int nuevoID = buscar_id();
						// Escribir en el txt de usuarios
						if (nuevoID != -1) {
							if(EscribirTxt.guardarUsuario(nuevoID, nombre, contraseña, correo)) {
								usuarios.add(new Usuario(nuevoID, nombre, correo, contraseña, false, null));
								txtNombreAñadir.setText("");
								txtContraseñaAñadir.setText("");
								txtCorreoAñadir.setText("");
								lblDatosAñadirCorrectos.setVisible(true);
							} else {
								lblDatosAñadirIncorrectos.setText("No se ha podido guardar el usuario");
								lblDatosAñadirIncorrectos.setVisible(true);
							}
						} else {
							lblDatosAñadirIncorrectos.setText("No se ha encontrado un id disponible");
							lblDatosAñadirIncorrectos.setVisible(true);
						}
					}
	}

	private int buscar_id() {
		int id = -1;
		boolean existe = false;
		for (int i = 10; i >= 0; i--) {
			existe = false;
			for (Usuario usuario : usuarios) {
				if (usuario.getId() == i) {
					existe = true;
				}
			}
			if(!existe) {
				id = i;
			}
		}
		return id;
	}

	private boolean nombre_repetido(String nombre) {
		boolean repetido = false;
		for (Usuario usuario : usuarios) {
			if(usuario.getNombre().equals(nombre)) {
				repetido = true;
			}
		}
		return repetido;
	}

	private void eliminar_usuario(String nombre) {
		lblNombreEliminarIncorrecto.setVisible(false);
		lblNombreEliminarCorrecto.setVisible(false);
		// Busco el id del usuario con ese nombre
		Usuario usuarioBorrar = null;
		int cantidadAdmin = 0;
		int cantidadUsuario = 0;
		boolean idEncontrado = false;
		for (Usuario u : usuarios) {
			if (!u.isAdmin()) {
				cantidadUsuario++;
				if (u.getNombre().equals(nombre)) {
					usuarioBorrar = u;
					idEncontrado = true;
				}
			} else {
				cantidadAdmin++;
			}
		}
		if (idEncontrado && usuarioBorrar != null) {
			// Compruebo que se pueda eliminar (al menos 1 admin y 3 usuarios)
			if(cantidadAdmin == 1 && cantidadUsuario > 3) {
				// Eliminar el usuario con ese id en el fichero de usuarios y en el de categorías favoritas
				if(EscribirTxt.eliminarUsuario(usuarioBorrar.getId())) {
					usuarios.remove(usuarioBorrar);
					txtNombreEliminar.setText("");
					lblNombreEliminarCorrecto.setVisible(true);
				} else {
					lblNombreEliminarIncorrecto.setText("Error en la eliminación del usuario");
					lblNombreEliminarIncorrecto.setVisible(true);
				}
			} else {
				lblNombreEliminarIncorrecto.setText("No se pueden eliminar más usuarios");
				lblNombreEliminarIncorrecto.setVisible(true);
			}
		} else {
			lblNombreEliminarIncorrecto.setText("No se ha encontrado el usuario");
			lblNombreEliminarIncorrecto.setVisible(true);
		}
	}

	private void cancelar_añadir_eliminar() {
		lblDatosAñadirCorrectos.setVisible(false);
		lblNombreEliminarCorrecto.setVisible(false);
		// Cancelar proceso de añadir o eliminar un usuario
		lblNombreEliminarIncorrecto.setVisible(false);
		lblDatosAñadirIncorrectos.setVisible(false);
		
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
		lblNombreEliminarIncorrecto.setVisible(false);
		
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
		lblDatosAñadirIncorrectos.setVisible(false);
		
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
		todosPaneles.setLayer(todosPaneles.getComponent(0), 7); // Muevo el panel de gestion de usuarios hacia atrás para que se vea el panel de administrador
		todosBotones.get(7).setEnabled(true); // Habilito el botón de cerrar sesion
		todosBotones.get(8).setEnabled(true); // Habilito el botón de gestionar usuarios
		todosBotones.get(15).setEnabled(true); // Habilito el botón de enviar correo
		todosBotones.get(17).setEnabled(true); // Habilito el botón de mirar la hora de envío
		todosBotones.get(9).setEnabled(false); // Deshabilito el botón de cerrar sesión del panel de gestion de usuarios
		todosBotones.get(10).setEnabled(false); // Deshabilito el botón de Atrás
		todosBotones.get(11).setEnabled(false); // Deshabilito el botón de Añadir usuarios
		todosBotones.get(12).setEnabled(false); // Deshabilito el botón de Eliminar usuarios
		
	}

	private void gestionar_usuarios() {
		todosPaneles.setLayer(todosPaneles.getComponent(1), 9); // Muevo el panel de gestion de usuarios arriba del todo
		todosBotones.get(7).setEnabled(false); // Deshabilito el botón de cerrar sesion
		todosBotones.get(8).setEnabled(false); // Deshabilito el botón de gestionar usuarios
		todosBotones.get(15).setEnabled(false); // Deshabilito el botón de enviar correo
		todosBotones.get(17).setEnabled(false); // Deshabilito el botón de mirar la hora de envío
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

	private void guardar_categorias_seleccionadas() {
		// Muy parecido a cómo envío el email pero lo guardo en una misma línea y lo escribo en un txt
		String guardar = "#"+usuarioLogueado.getId();
		String [] categorias = {"Economía", "Deportes", "Nacional", "Internacional", "Música", "Medio Ambiente"};
		int i; // El orden de las categorías del usuario
		int j = 0; // El orden de todos los titulares
		LocalDate fecha = LocalDate.now();
        int dia = fecha.getDayOfMonth();
        int mes = fecha.getMonthValue();
        int año = fecha.getYear();
        String fechaEntera = dia+"-"+mes+"-"+año;
        guardar += "["+fechaEntera+"]";
		for (i = 0; i < categorias.length; i++) {
			
			// Si el usuario tiene alguna de las tres noticias, se muestra su titular
			if(usuarioLogueado.getCategorias()[j] || usuarioLogueado.getCategorias()[j+1] || usuarioLogueado.getCategorias()[j+2]) {
				
				guardar += ".:"+categorias[i]+":.";
			}
			
			if(usuarioLogueado.getCategorias()[j]) {
			
				guardar += "::"+titulares.get(j) +"::";
			}
			
			j++;
			if(usuarioLogueado.getCategorias()[j]) {
				
				guardar += "::"+titulares.get(j) +"::";
			}
			
			j++;
			if(usuarioLogueado.getCategorias()[j]) {
				
				guardar += "::"+titulares.get(j) +"::";
			}
			j++; // Aumento para estar posicionado en la primera noticia de la siguiente categoría
		}
	
		EscribirTxt.guardarTitulares(guardar);
		todosPaneles.getComponent(0).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
	
	private void enviar_categorias_seleccionadas() {
		titulares = LeerTxt.leerTodasNoticias();
		if (titulares == null || titulares.size()==0) {
			JOptionPane.showMessageDialog(null, "No se han encontrado alguno de los titulares", "ERROR", 2);
			System.exit(2);
		}
		
		// Envía correo con las categorías del usuario
		String mensaje = generarMensajeCorreo(usuarioLogueado, titulares);
		
		Email email = new Email(usuarioLogueado.getCorreo(), mensaje);
		todosPaneles.getComponent(0).setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		if(email.enviar()) {
			JOptionPane.showMessageDialog(null, "Revisa tu correo con los titulares", "ENVIADO", 1);
		} else {
			JOptionPane.showMessageDialog(null, "El correo no se ha enviado correctamente", "ERROR", 2);
		}
		todosPaneles.getComponent(0).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	public static String generarMensajeCorreo(Usuario u, ArrayList<String> t) throws NullPointerException, IndexOutOfBoundsException {
		String mensaje = "<html>";
		String [] categorias = {"Economía", "Deportes", "Nacional", "Internacional", "Música", "Medio Ambiente"};
		int i; // El orden de las categorías del usuario
		int j = 0; // El orden de todos los titulares
		LocalDateTime fecha = LocalDateTime.now();
		int dia = fecha.getDayOfMonth();
		int mes = fecha.getMonthValue();
		int año = fecha.getYear();
		int hora = fecha.getHour();
		int minuto = fecha.getMinute();
		String fechaEntera = dia + "-" + mes + "-" + año + " " + hora + ":" + minuto;
		mensaje += "<p>Las noticias del <i>" + fechaEntera + "</i>.</p>";

		for (i = 0; i < categorias.length; i++) {
			
			// Si el usuario tiene alguna de las tres noticias, se muestra su titular
			if(u.getCategorias()[j] || u.getCategorias()[j+1] || u.getCategorias()[j+2]) {
				mensaje += "<h2>";
				mensaje += categorias[i];
				mensaje += "</h2>";
			}
			
			if(u.getCategorias()[j]) {
				mensaje += "<p>";
				mensaje += t.get(j);
				mensaje += "</p>";
			}
			
			j++;
			if(u.getCategorias()[j]) {
				mensaje += "<p>";
				mensaje += t.get(j);
				mensaje += "</p>";
			}
			
			j++;
			if(u.getCategorias()[j]) {
				mensaje += "<p>";
				mensaje += t.get(j);
				mensaje += "</p>";
			}
			j++; // Aumento para estar posicionado en la primera noticia de la siguiente categoría
		}
		mensaje += "</html>";
		return mensaje;
	}

	private void mostrar_categorias_seleccionadas() {
		// Esta función muestra o esconde las noticias
		int i; // El orden de todos los componentes del panel
		int j = 0; // El orden de las categorías del usuario
		// Esta variable determina la altura en la que se coloca la información para que no haya huecos en blanco
		int y = 20;
		componentesCategorias[1].setVisible(!componentesCategorias[1].isVisible()); // El título "Tus noticias"
		for (i = 2; i < componentesCategorias.length-6; i++) {
			
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
		
//		titulares = LeerTxt.leerTodasNoticias();
		
//		Ventana.titulares = titulares;
		
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
				// Pongo los paneles que usa el admin mas arriba
				todosPaneles.setLayer(todosPaneles.getComponent(1), 8); // El panel principal del admin lo pongo arriba
				todosPaneles.setLayer(todosPaneles.getComponent(2), 7); // El panel de gestionar usuarios justo debajo
				todosPaneles.setLayer(todosPaneles.getComponent(3), 6); // El panel de mostrar los titulares justo debajo
				todosBotones.get(7).setEnabled(true); // Habilito el botón de cerrar sesión
				todosBotones.get(8).setEnabled(true); // Habilito el botón de gestion de usuarios
				todosBotones.get(15).setEnabled(true); // Habilito el botón de enviar correo
				todosBotones.get(17).setEnabled(true); // Habilito el botón de mirar la hora de envío automático
			}
			
			todosBotones.get(0).setEnabled(false); // Deshabilito el botón de iniciar sesión
		}
	}


}
