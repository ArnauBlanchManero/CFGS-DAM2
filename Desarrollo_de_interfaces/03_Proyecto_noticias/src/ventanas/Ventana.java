package ventanas;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;

import javax.swing.border.SoftBevelBorder;

import correos.Automatico;
import txt.LeerTxt;
import usuarios.Usuario;

import javax.swing.border.BevelBorder;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Checkbox;

import javax.swing.JCheckBox;
import javax.swing.border.MatteBorder;
import javax.swing.JTextArea;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;

/*
 * Trabajo realizado por Arnau Blanch Manero
 */

public class Ventana extends JFrame{
	public static final int CANTIDAD_CATEGORIAS = 18;
	private JTextField txtNombre;
//	private JTextField txtContrasea;
	private JPasswordField pwdContrasenia;
	private JLabel lblSesionIncorrecta;
	private JLabel lblCategoriasIncorrectas;
	private JTextField txtNombreAñadir;
	private JTextField txtContraseaAñadir;
	private JTextField txtCorreoAñadir;
	private JTextField txtNombreEliminar;
	private JLabel lblDatosAñadirIncorrectos;
	private JLabel lblNombreEliminarIncorrecto;
	private Timer tiempo;
	private JLayeredPane todosPaneles;
	private JLabel lblNombreAñadir;
	private JLabel lblContraseaaadir;
	private JLabel lblCorreoaadir;
	private JLabel lblNombreEliminar;
	private JLabel lblDatosAñadirCorrectos;
	private JLabel lblNombreEliminarCorrecto;
	public static ArrayList<Usuario> usuarios;
	public static ArrayList<String> titulares;
	public static int rolUsuario = -1;
	
	public Ventana(int x, int y) {
		// Este es el constructor de la ventana de carga
		super();
		setUndecorated(true);
		setSize(x, y);
		setResizable(false);
		setLocationRelativeTo(null);
		try {
			setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("newsLogo.png")));
		} catch (NullPointerException e) {

		}
		// Es obligatorio poner una imagen en este panel
		Panel panelCarga = buscarImagen();
		getContentPane().add(panelCarga);
		panelCarga.setVisible(true);
		JProgressBar barraProgreso = new JProgressBar();
		barraProgreso.setStringPainted(true);
		barraProgreso.setBounds(10, 300, 620, 20);
		panelCarga.add(barraProgreso);
		// Configuro que la barra de progreso aumente 4 cada 200 segundos lo que da un total de 5 segundos
		tiempo = new Timer(200, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				barraProgreso.setValue(barraProgreso.getValue()+4);
				if(barraProgreso.getValue()==100) {
					// Cuando ha acabado de cargar elimino esta ventana y creo la vntana principal de la aplicación.
					dispose();
					Ventana ventanaPrincipal = new Ventana("Noticias diarias");
					ventanaPrincipal.setVisible(true);
					tiempo.stop();
				}
				if(barraProgreso.getValue()==80) {
					// Cuando lleva 4 segundos cargando leo los ficheros de los usuarios y de las noticias para comprobar que está todo bien.
					try {
						setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						usuarios = LeerTxt.leerTodosUsuarios();
						titulares = LeerTxt.leerTodasNoticias();
					} catch (IOException e1) {
						usuarios = new ArrayList<Usuario>();
						e1.printStackTrace();
					}
					// Si alguna de las dos lecturas falla, muestro una ventana de error.
					if (usuarios.size() < 4 || !contarAdminsUsuarios(usuarios)) {
						tiempo.stop();
						barraProgreso.setValue(100);
						dispose();
						JOptionPane.showMessageDialog(null, "No se han cargado los usuarios correctamente", "ERROR", 2);
						System.exit(1);
					}
					if (titulares == null || titulares.size()==0) {
						tiempo.stop();
						barraProgreso.setValue(100);
						dispose();
						JOptionPane.showMessageDialog(null, "No se he encontrado alguno de los titulares", "ERROR", 2);
						System.exit(2);
					}
				}
			}
		});
		tiempo.start();
	}

	protected boolean contarAdminsUsuarios(ArrayList<Usuario> usuarios) {
		int cantidadAdmin = 0;
		int cantidadUsuario = 0;
		for (Usuario usuario : usuarios) {
			if (usuario.isAdmin()) {
				cantidadAdmin++;
			} else {
				cantidadUsuario++;
			}
		}
		// Compruebo que haya un admin y 3 o más usuarios 
		return cantidadAdmin == 1 && cantidadUsuario >= 3 && cantidadUsuario <= 10;
	}

	private Panel buscarImagen() {
		// En esta función le asigno una imagen al panel
		BufferedImage fondo = null;
		Panel panelConFondo = null;
		File ruta = new File("src/ventanas/fondoPlaya.jpg");
		ruta.setReadable(true);
		try {
			fondo = ImageIO.read(ruta);
			Image foto = fondo;
			panelConFondo=new Panel(true) {
				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.drawImage(foto.getScaledInstance(640, 323, Image.SCALE_SMOOTH), 0, 0, 640, 323, null);
				}
			};
		} catch (IOException e) {
			// Si hay un error al gargar la imagen lo pongo en un texto y el resto de la aplicación sigue funcionando.
			panelConFondo=new Panel("Error al cargar la imagen");
		}
		return panelConFondo;
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public Ventana(String title) throws HeadlessException {
		// La ventana principal
		super();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        int opcion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que quieres cerrar?", "SALIR", JOptionPane.YES_NO_OPTION);
		        if (opcion == JOptionPane.YES_OPTION) {
		            dispose();
		            System.exit(0);
		        }
		    }
		});
		for (Usuario usuario : usuarios) {
			try {
				Automatico auto = new Automatico(usuario.getCorreo(), Evento.generarMensajeCorreo(usuario, titulares));
				Thread hilo = new Thread(auto);
				hilo.start();
			} catch (NullPointerException e) {
				
			}
		}
		Dimension monitor = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = (int) monitor.getWidth() / 2 - 700 / 2;
		int alto = (int) monitor.getHeight() / 2 - 700 / 2;
		setBounds(ancho, alto, 700, 700);
		setResizable(false);
		try {
			setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("newsLogo.png")));
		} catch (NullPointerException e) {

		}
		setTitle(title);
		
		// Organizo los paneles en un JLayeredPane
		todosPaneles = new JLayeredPane();
		todosPaneles.setSize(700, 700);
		todosPaneles.setLayout(null);
		getContentPane().add(todosPaneles);
		Panel panelInicioSesion = new Panel(false);
		todosPaneles.setLayer(panelInicioSesion, 5);
		Panel panelCategoriasFavoritas = new Panel(false);
		todosPaneles.setLayer(panelCategoriasFavoritas, 4);
		Panel panelMostrarCategorias = new Panel(false);
		todosPaneles.setLayer(panelCategoriasFavoritas, 3);
		Panel panelGeneralAdmin = new Panel(false);
		todosPaneles.setLayer(panelGeneralAdmin, 2);
		Panel panelGestionUsuariosAdmin = new Panel(false);
		todosPaneles.setLayer(panelGestionUsuariosAdmin, 1);
		
		// Le pongo los componentes al panel de iniciar sesión
		iniciar_sesion(panelInicioSesion);

		// Le pongo los componentes al panel de seleccionar las categorías favoritas
		JCheckBox[] todosCheckboxes = categorias_favoritas(panelCategoriasFavoritas);

		// Le pongo los componentes al panel de mostrar las categorías
		mostrar_categorias_usuario(panelMostrarCategorias);

		// Le pongo los componentes al panel de administración
		panel_administracion(panelGeneralAdmin);

		// Le pongo los componentes al panel de gestión de usuarios
		gestion_usuarios_admin(panelGestionUsuariosAdmin);
		
		// Organizo los botones de cada panel y los deshabilito para que no se puedan pulsar desde otros paneles
		
		// Este boton lo añado al panel de seleccionar las categorías favoritas
		JButton btnGuardarCategorias = new JButton("Guardar");
		btnGuardarCategorias.setBounds(314, 615, 117, 25);
		btnGuardarCategorias.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelCategoriasFavoritas.add(btnGuardarCategorias);
		btnGuardarCategorias.setEnabled(false);
		// Añado el evento del botón para guardar sus categorias favoritas en un txt
		Evento guardarCategorias = new Evento("guardar categorias", todosCheckboxes, lblCategoriasIncorrectas);
		btnGuardarCategorias.addActionListener(guardarCategorias);

		// Este botón muestra los titulares de las categorías favoriatas del usuario
		JButton btnMostrarCategorias = new JButton("Mostrar");
		btnMostrarCategorias.setBounds(250, 300, 180, 75);
		btnMostrarCategorias.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelMostrarCategorias.add(btnMostrarCategorias);
		btnMostrarCategorias.setEnabled(false);
		// Este botón esconde los titulares y vuelve a mostrar el botón de Mostrar
		JButton btnEsconderCategorias = new JButton("Atrás");
		btnEsconderCategorias.setBounds(70, 630, 110, 25);
		panelMostrarCategorias.add(btnEsconderCategorias);
		btnEsconderCategorias.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEsconderCategorias.setEnabled(false);
		btnEsconderCategorias.setVisible(false);
		// Este botón escribe en un txt los titulares actuales en forma de histórico para el usuario y le envía un correo con esos titulares
		JButton btnGuardarTitulares = new JButton("Guardar");
		btnGuardarTitulares.setBounds(300, 630, 110, 25);
		btnGuardarTitulares.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelMostrarCategorias.add(btnGuardarTitulares);
		btnGuardarTitulares.setEnabled(false);
		btnGuardarTitulares.setVisible(false);
		
		// Envía el correo electrónico con los titulares
		Evento enviarCategorias = new Evento("guardar titulares", titulares);
		btnGuardarTitulares.addActionListener(enviarCategorias);
		
		// Muestra los botones para gestionar a los usuarios
		JButton btnGestionarusuarios = new JButton("Gestionar usuarios");
		btnGestionarusuarios.setBounds(260, 300, 180, 75);
		btnGestionarusuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelGeneralAdmin.add(btnGestionarusuarios);
		btnGestionarusuarios.setEnabled(false);
		
		// Este evento cambia de panel para gestionar a los usuarios
		Evento gestionarUsuarios = new Evento("gestion usuarios");
		btnGestionarusuarios.addActionListener(gestionarUsuarios);

		// Wnvia al admin todos los titulares
		JButton btnMostrarCategoriasAdmin = new JButton("Test titulares");
		btnMostrarCategoriasAdmin.setBounds(70, 300, 180, 75);
		btnMostrarCategoriasAdmin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelGeneralAdmin.add(btnMostrarCategoriasAdmin);
		btnMostrarCategoriasAdmin.setEnabled(false);

		// Muestra los botones para gestionar a los usuarios
		JButton btnMostrarHora = new JButton("Ver hora de envío");
		btnMostrarHora.setBounds(450, 300, 180, 75);
		btnMostrarHora.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelGeneralAdmin.add(btnMostrarHora);
		btnMostrarHora.setEnabled(false);
		Evento mostrarHora = new Evento("mostrar hora");
		btnMostrarHora.addActionListener(mostrarHora);
		
		// Añadir o eliminar usuarios
		JButton btnAadir = new JButton("Añadir");
		btnAadir.setBounds(180, 231, 117, 45);
		btnAadir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAadir.setEnabled(false);
		panelGestionUsuariosAdmin.add(btnAadir);
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(415, 231, 117, 45);
		btnEliminar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEliminar.setEnabled(false);
		panelGestionUsuariosAdmin.add(btnEliminar);
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(374, 589, 117, 25);
		btnAceptar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAceptar.setEnabled(false);
		btnAceptar.setVisible(false);
		panelGestionUsuariosAdmin.add(btnAceptar);
		JButton btnAtras = new JButton("Atrás");
		btnAtras.setBounds(58, 589, 117, 25);
		btnAtras.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAtras.setEnabled(false);
		panelGestionUsuariosAdmin.add(btnAtras);
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(226, 589, 117, 25);
		btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCancelar.setEnabled(false);
		btnCancelar.setVisible(false);
		panelGestionUsuariosAdmin.add(btnCancelar);
		
		Evento atrasAdmin = new Evento("atras admin");
		btnAtras.addActionListener(atrasAdmin);
		Evento aniadirUsuario = new Evento("añadir usuario", txtNombreAñadir, txtContraseaAñadir, txtCorreoAñadir, lblDatosAñadirIncorrectos, lblNombreAñadir, lblContraseaaadir, lblCorreoaadir, lblDatosAñadirCorrectos);
		btnAadir.addActionListener(aniadirUsuario);
		Evento eliminarUsuario = new Evento("eliminar usuario", txtNombreEliminar, lblNombreEliminarIncorrecto, lblNombreEliminar, lblNombreEliminarCorrecto);
		btnEliminar.addActionListener(eliminarUsuario);
		Evento esconderAñadirEliminarUsuario = new Evento("esconder info", txtNombreAñadir, txtContraseaAñadir, txtCorreoAñadir, lblDatosAñadirIncorrectos, lblNombreAñadir, lblContraseaaadir, lblCorreoaadir, txtNombreEliminar, lblNombreEliminarIncorrecto, lblNombreEliminar, lblDatosAñadirCorrectos, lblNombreEliminarCorrecto);
		btnCancelar.addActionListener(esconderAñadirEliminarUsuario);
		Evento aceptarAñadirEliminarUsuario = new Evento("aceptar info", txtNombreAñadir, txtContraseaAñadir, txtCorreoAñadir, lblDatosAñadirIncorrectos, lblNombreAñadir, lblContraseaaadir, lblCorreoaadir, txtNombreEliminar, lblNombreEliminarIncorrecto, lblNombreEliminar, lblDatosAñadirCorrectos, lblNombreEliminarCorrecto);
		btnAceptar.addActionListener(aceptarAñadirEliminarUsuario);
		
		// He creado un botón de cerrar sesión para cada panel pero que llama al mismo evento
		JButton btnCerrarSesionMostrarCategorias = new JButton("Cerrar sesión");
		btnCerrarSesionMostrarCategorias.setBounds(530, 630, 150, 25);
		btnCerrarSesionMostrarCategorias.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCerrarSesionMostrarCategorias.setEnabled(false);
		btnCerrarSesionMostrarCategorias.setVisible(true);
		panelMostrarCategorias.add(btnCerrarSesionMostrarCategorias);
		JButton btnCerrarSesionGeneralAdmin = new JButton("Cerrar sesión");
		btnCerrarSesionGeneralAdmin.setBounds(499, 589, 154, 25);
		btnCerrarSesionGeneralAdmin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCerrarSesionGeneralAdmin.setEnabled(false);
		btnCerrarSesionGeneralAdmin.setVisible(true);
		panelGeneralAdmin.add(btnCerrarSesionGeneralAdmin);
		JButton btnCerrarSesionCategoriasFavoritas = new JButton("Cerrar sesión");
		btnCerrarSesionCategoriasFavoritas.setBounds(500, 615, 150, 25);
		btnCerrarSesionCategoriasFavoritas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCerrarSesionCategoriasFavoritas.setEnabled(false);
		btnCerrarSesionCategoriasFavoritas.setVisible(true);
		panelCategoriasFavoritas.add(btnCerrarSesionCategoriasFavoritas);
		JButton btnCerrarSesionGestionUsuarios = new JButton("Cerrar sesión");
		btnCerrarSesionGestionUsuarios.setBounds(499, 589, 154, 25);
		btnCerrarSesionGestionUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCerrarSesionGestionUsuarios.setEnabled(false);
		panelGestionUsuariosAdmin.add(btnCerrarSesionGestionUsuarios);

		// El evento de cerrar sesión que organiza los paneles para que se vea el de iniciar sesión
		Evento cerrarSesion = new Evento("cerrar sesion");
		btnCerrarSesionMostrarCategorias.addActionListener(cerrarSesion);
		btnCerrarSesionCategoriasFavoritas.addActionListener(cerrarSesion);
		btnCerrarSesionGeneralAdmin.addActionListener(cerrarSesion);
		btnCerrarSesionGestionUsuarios.addActionListener(cerrarSesion);

		JButton btnAtrasAdmin = new JButton("Atrás");
		btnAtrasAdmin.setBounds(70, 630, 110, 25);
		btnAtrasAdmin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelMostrarCategorias.add(btnAtrasAdmin);
		btnAtrasAdmin.setEnabled(false);
		btnAtrasAdmin.setVisible(false);
		JButton btnEnviarTitulares = new JButton("Enviar");
		btnEnviarTitulares.setBounds(300, 630, 110, 25);
		btnEnviarTitulares.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelMostrarCategorias.add(btnEnviarTitulares);
		btnEnviarTitulares.setEnabled(false);
		btnEnviarTitulares.setVisible(false);
		Evento enviarTitulares = new Evento("enviar categorias", titulares);
		btnEnviarTitulares.addActionListener(enviarTitulares);
		
		// El evento de testear si todos los titulares funcionan y se envian al correo
		Evento mostrarCategoriasAdmin = new Evento("mostrar categorias admin", panelMostrarCategorias.getComponents());
		btnMostrarCategoriasAdmin.addActionListener(mostrarCategoriasAdmin);
		Evento atrasAdminTitulares = new Evento("atras admin titulares", panelMostrarCategorias.getComponents());
		btnAtrasAdmin.addActionListener(atrasAdminTitulares);
		// Este evento muestra u oculta los titulares dependiendo del botón que lo pulsa
		Evento mostrarCategorias = new Evento("mostrar categorias", panelMostrarCategorias.getComponents());
		btnMostrarCategorias.addActionListener(mostrarCategorias);
		btnEsconderCategorias.addActionListener(mostrarCategorias);
		
		// El botón del inicio de sesión es el único habilitado al principio
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.setBounds(294, 468, 117, 25);
		btnEntrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelInicioSesion.add(btnEntrar);
		panelInicioSesion.setEnabled(true);

		// Hago un array de botones para poder acceder a todos desde una variable
		ArrayList<JButton> todosBotones = new ArrayList<JButton>();
		todosBotones.add(btnEntrar); //0
		todosBotones.add(btnGuardarCategorias); //1
		todosBotones.add(btnMostrarCategorias); //2
		todosBotones.add(btnEsconderCategorias); //3
		todosBotones.add(btnGuardarTitulares); //4
		todosBotones.add(btnCerrarSesionMostrarCategorias); //5
		todosBotones.add(btnCerrarSesionCategoriasFavoritas); //6
		todosBotones.add(btnCerrarSesionGeneralAdmin); //7
		todosBotones.add(btnGestionarusuarios); //8
		todosBotones.add(btnCerrarSesionGestionUsuarios); //9
		todosBotones.add(btnAtras); //10
		todosBotones.add(btnAadir); //11
		todosBotones.add(btnEliminar); //12
		todosBotones.add(btnAceptar); //13
		todosBotones.add(btnCancelar); //14
		todosBotones.add(btnMostrarCategoriasAdmin); //15
		todosBotones.add(btnAtrasAdmin); //16
		todosBotones.add(btnMostrarHora); //17
		todosBotones.add(btnEnviarTitulares); //18
		
		

		// El evento al iniciar sesión comprueba que el usuario exista y tenga categorías favoritas
		Evento comprobarSesion = new Evento("comprobar sesion", txtNombre, pwdContrasenia, lblSesionIncorrecta, usuarios, todosPaneles, todosBotones, todosCheckboxes);
		btnEntrar.addActionListener(comprobarSesion);
		
		// Añado los paneles al JLayederPane
		todosPaneles.add(panelInicioSesion);
		todosPaneles.add(panelCategoriasFavoritas);
		todosPaneles.add(panelMostrarCategorias);
		todosPaneles.add(panelGeneralAdmin);
		todosPaneles.add(panelGestionUsuariosAdmin);
		
		// Les pongo nombre para poder identificarlos posteriormente
		todosPaneles.getComponent(0).setName("Inicio");
		todosPaneles.getComponent(1).setName("Favoritos");
		todosPaneles.getComponent(2).setName("Titulares");
		todosPaneles.getComponent(3).setName("Admin");
		todosPaneles.getComponent(4).setName("Gestion");
	}
	
	private void gestion_usuarios_admin(Panel panelGestionUsuariosAdmin) {
		// El título del panel
		JLabel lblCategoriasFavoritas = new JLabel("Gestionar usuarios");
		lblCategoriasFavoritas.setForeground(new Color(36, 31, 49));
		lblCategoriasFavoritas.setFont(new Font("Noto Mono", Font.BOLD, 28));
		lblCategoriasFavoritas.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(28, 113, 216), new Color(0, 0, 0)));
		lblCategoriasFavoritas.setBackground(new Color(98, 160, 234));
		lblCategoriasFavoritas.setOpaque(true);
		lblCategoriasFavoritas.setHorizontalAlignment(SwingConstants.CENTER);
		lblCategoriasFavoritas.setBounds(180, 90, 352, 64);
		panelGestionUsuariosAdmin.add(lblCategoriasFavoritas);

		txtNombreAñadir = new JTextField();
		txtNombreAñadir.setText("");
		txtNombreAñadir.setBounds(156, 360, 187, 19);
		txtNombreAñadir.setColumns(10);
		txtNombreAñadir.setVisible(false);
		panelGestionUsuariosAdmin.add(txtNombreAñadir);
		
		txtContraseaAñadir = new JTextField();
		txtContraseaAñadir.setText("");
		txtContraseaAñadir.setBounds(156, 438, 187, 19);
		txtContraseaAñadir.setColumns(10);
		txtContraseaAñadir.setVisible(false);
		panelGestionUsuariosAdmin.add(txtContraseaAñadir);
		
		txtCorreoAñadir = new JTextField();
		txtCorreoAñadir.setText("");
		txtCorreoAñadir.setBounds(156, 514, 187, 19);
		txtCorreoAñadir.setColumns(10);
		txtCorreoAñadir.setVisible(false);
		panelGestionUsuariosAdmin.add(txtCorreoAñadir);
		
		txtNombreEliminar = new JTextField();
		txtNombreEliminar.setText("");
		txtNombreEliminar.setBounds(374, 360, 187, 19);
		txtNombreEliminar.setColumns(10);
		txtNombreEliminar.setVisible(false);
		panelGestionUsuariosAdmin.add(txtNombreEliminar);
		
		lblDatosAñadirIncorrectos = new JLabel("Datos incorrectos");
		lblDatosAñadirIncorrectos.setForeground(new Color(237, 51, 59));
		lblDatosAñadirIncorrectos.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatosAñadirIncorrectos.setBounds(58, 558, 374, 15);
		lblDatosAñadirIncorrectos.setVisible(false);
		panelGestionUsuariosAdmin.add(lblDatosAñadirIncorrectos);
		lblDatosAñadirCorrectos = new JLabel("Usuario añadido correctamente");
		lblDatosAñadirCorrectos.setForeground(new Color(38, 162, 105));
		lblDatosAñadirCorrectos.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatosAñadirCorrectos.setBounds(58, 558, 374, 15);
		lblDatosAñadirCorrectos.setVisible(false);
		panelGestionUsuariosAdmin.add(lblDatosAñadirCorrectos);
		
		lblNombreEliminarIncorrecto = new JLabel("Nombre incorrecto");
		lblNombreEliminarIncorrecto.setForeground(new Color(237, 51, 59));
		lblNombreEliminarIncorrecto.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreEliminarIncorrecto.setBounds(279, 558, 374, 15);
		lblNombreEliminarIncorrecto.setVisible(false);
		panelGestionUsuariosAdmin.add(lblNombreEliminarIncorrecto);
		lblNombreEliminarCorrecto = new JLabel("Usuario eliminado correctamente");
		lblNombreEliminarCorrecto.setForeground(new Color(38, 162, 105));
		lblNombreEliminarCorrecto.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreEliminarCorrecto.setBounds(279, 558, 374, 15);
		lblNombreEliminarCorrecto.setVisible(false);
		panelGestionUsuariosAdmin.add(lblNombreEliminarCorrecto);
		
		lblNombreAñadir = new JLabel("Nombre");
		lblNombreAñadir.setBounds(156, 333, 187, 15);
		lblNombreAñadir.setVisible(false);
		panelGestionUsuariosAdmin.add(lblNombreAñadir);
		
		lblContraseaaadir = new JLabel("Contraseña");
		lblContraseaaadir.setBounds(156, 411, 187, 15);
		lblContraseaaadir.setVisible(false);
		panelGestionUsuariosAdmin.add(lblContraseaaadir);
		
		lblCorreoaadir = new JLabel("Correo electrónico");
		lblCorreoaadir.setBounds(156, 487, 187, 15);
		lblCorreoaadir.setVisible(false);
		panelGestionUsuariosAdmin.add(lblCorreoaadir);
		
		lblNombreEliminar = new JLabel("Nombre");
		lblNombreEliminar.setBounds(374, 333, 187, 15);
		lblNombreEliminar.setVisible(false);
		panelGestionUsuariosAdmin.add(lblNombreEliminar);
	}

	private void mostrar_categorias_usuario(Panel panelMostrarCategorias) {
		// El título del panel
		JLabel lblCategoriasFavoritas = new JLabel("Tus noticias");
		lblCategoriasFavoritas.setForeground(new Color(36, 31, 49));
		lblCategoriasFavoritas.setFont(new Font("Noto Mono", Font.BOLD, 28));
		lblCategoriasFavoritas.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(28, 113, 216), new Color(0, 0, 0)));
		lblCategoriasFavoritas.setBackground(new Color(98, 160, 234));
		lblCategoriasFavoritas.setOpaque(true);
		lblCategoriasFavoritas.setHorizontalAlignment(SwingConstants.CENTER);
		lblCategoriasFavoritas.setBounds(193, 200, 312, 64);
		panelMostrarCategorias.add(lblCategoriasFavoritas);	
		
		// Pongo las categorías y sus titulares ocultos para que, dependiendo del usuario, se muestren los que ha seleccionado como favoritos
		JLabel lblEconomia = new JLabel("Economía");
		lblEconomia.setFont(new Font("Fira Sans", Font.BOLD, 15));
		lblEconomia.setBounds(50, 100, 150, 25);
		lblEconomia.setVisible(false);
		panelMostrarCategorias.add(lblEconomia);

			JTextArea textEconomia1 = new JTextArea("- "+titulares.get(0));
			textEconomia1.setOpaque(false);
			textEconomia1.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
			textEconomia1.setEditable(false);
			textEconomia1.setEnabled(false);
			textEconomia1.setDisabledTextColor(new Color(0, 0, 0));
			textEconomia1.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			// Dependiendo de su longitud, le voy a dar más o menos espacio para que no haya líneas en blanco y aprovechar el espacio
			if(titulares.get(0).length()<102)
				textEconomia1.setBounds(50, 100, 620, 16);
			else
				textEconomia1.setBounds(50, 100, 620, 30);
			textEconomia1.setLineWrap(true);
			textEconomia1.setVisible(false);
			panelMostrarCategorias.add(textEconomia1);
			JTextArea textEconomia2 = new JTextArea("- "+titulares.get(1));
			textEconomia2.setOpaque(false);
			textEconomia2.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
			textEconomia2.setEditable(false);
			textEconomia2.setEnabled(false);
			textEconomia2.setDisabledTextColor(new Color(0, 0, 0));
			textEconomia2.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			if(titulares.get(1).length()<102)
				textEconomia2.setBounds(50, 100, 620, 16);
			else
				textEconomia2.setBounds(50, 100, 620, 30);
			textEconomia2.setLineWrap(true);
			textEconomia2.setVisible(false);
			panelMostrarCategorias.add(textEconomia2);
			JTextArea textEconomia3 = new JTextArea("- "+titulares.get(2));
			textEconomia3.setOpaque(false);
			textEconomia3.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
			textEconomia3.setEditable(false);
			textEconomia3.setEnabled(false);
			textEconomia3.setDisabledTextColor(new Color(0, 0, 0));
			textEconomia3.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			if(titulares.get(2).length()<102)
				textEconomia3.setBounds(50, 100, 620, 16);
			else
				textEconomia3.setBounds(50, 100, 620, 30);
			textEconomia3.setLineWrap(true);
			textEconomia3.setVisible(false);
			panelMostrarCategorias.add(textEconomia3);
		
		JLabel lblDeportes = new JLabel("Deportes");
		lblDeportes.setFont(new Font("Fira Sans", Font.BOLD, 15));
		lblDeportes.setBounds(50, 100, 150, 25);
		lblDeportes.setVisible(false);
		panelMostrarCategorias.add(lblDeportes);

			JTextArea textDeportes1 = new JTextArea("- "+titulares.get(3));
			textDeportes1.setOpaque(false);
			textDeportes1.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
			textDeportes1.setEditable(false);
			textDeportes1.setEnabled(false);
			textDeportes1.setDisabledTextColor(new Color(0, 0, 0));
			textDeportes1.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			if(titulares.get(3).length()<102)
				textDeportes1.setBounds(50, 100, 620, 16);
			else
				textDeportes1.setBounds(50, 100, 620, 30);
			textDeportes1.setLineWrap(true);
			textDeportes1.setVisible(false);
			panelMostrarCategorias.add(textDeportes1);
			JTextArea textDeportes2 = new JTextArea("- "+titulares.get(4));
			textDeportes2.setOpaque(false);
			textDeportes2.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
			textDeportes2.setEditable(false);
			textDeportes2.setEnabled(false);
			textDeportes2.setDisabledTextColor(new Color(0, 0, 0));
			textDeportes2.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			if(titulares.get(4).length()<102)
				textDeportes2.setBounds(50, 100, 620, 16);
			else
				textDeportes2.setBounds(50, 100, 620, 30);
			textDeportes2.setLineWrap(true);
			textDeportes2.setVisible(false);
			panelMostrarCategorias.add(textDeportes2);
			JTextArea textDeportes3 = new JTextArea("- "+titulares.get(5));
			textDeportes3.setOpaque(false);
			textDeportes3.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
			textDeportes3.setEditable(false);
			textDeportes3.setEnabled(false);
			textDeportes3.setDisabledTextColor(new Color(0, 0, 0));
			textDeportes3.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			if(titulares.get(5).length()<102)
				textDeportes3.setBounds(50, 100, 620, 16);
			else
				textDeportes3.setBounds(50, 100, 620, 30);
			textDeportes3.setLineWrap(true);
			textDeportes3.setVisible(false);
			panelMostrarCategorias.add(textDeportes3);

		
		JLabel lblNacional = new JLabel("Nacional");
		lblNacional.setFont(new Font("Fira Sans", Font.BOLD, 15));
		lblNacional.setBounds(50, 100, 150, 25);
		lblNacional.setVisible(false);
		panelMostrarCategorias.add(lblNacional);


			JTextArea textNacional1 = new JTextArea("- "+titulares.get(6));
			textNacional1.setOpaque(false);
			textNacional1.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
			textNacional1.setEditable(false);
			textNacional1.setEnabled(false);
			textNacional1.setDisabledTextColor(new Color(0, 0, 0));
			textNacional1.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			if(titulares.get(6).length()<102)
				textNacional1.setBounds(50, 100, 620, 16);
			else
				textNacional1.setBounds(50, 100, 620, 30);
			textNacional1.setLineWrap(true);
			textNacional1.setVisible(false);
			panelMostrarCategorias.add(textNacional1);
			JTextArea textNacional2 = new JTextArea("- "+titulares.get(7));
			textNacional2.setOpaque(false);
			textNacional2.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
			textNacional2.setEditable(false);
			textNacional2.setEnabled(false);
			textNacional2.setDisabledTextColor(new Color(0, 0, 0));
			textNacional2.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			if(titulares.get(7).length()<102)
				textNacional2.setBounds(50, 100, 620, 16);
			else
				textNacional2.setBounds(50, 100, 620, 30);
			textNacional2.setLineWrap(true);
			textNacional2.setVisible(false);
			panelMostrarCategorias.add(textNacional2);
			JTextArea textNacional3 = new JTextArea("- "+titulares.get(8));
			textNacional3.setOpaque(false);
			textNacional3.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
			textNacional3.setEditable(false);
			textNacional3.setEnabled(false);
			textNacional3.setDisabledTextColor(new Color(0, 0, 0));
			textNacional3.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			if(titulares.get(8).length()<102)
				textNacional3.setBounds(50, 100, 620, 16);
			else
				textNacional3.setBounds(50, 100, 620, 30);
			textNacional3.setLineWrap(true);
			textNacional3.setVisible(false);
			panelMostrarCategorias.add(textNacional3);

		
		JLabel lblInternacional = new JLabel("Internacional");
		lblInternacional.setFont(new Font("Fira Sans", Font.BOLD, 15));
		lblInternacional.setBounds(50, 100, 150, 25);
		lblInternacional.setVisible(false);
		panelMostrarCategorias.add(lblInternacional);

			JTextArea textInternacional1 = new JTextArea("- "+titulares.get(9));
			textInternacional1.setOpaque(false);
			textInternacional1.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
			textInternacional1.setEditable(false);
			textInternacional1.setEnabled(false);
			textInternacional1.setDisabledTextColor(new Color(0, 0, 0));
			textInternacional1.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			if(titulares.get(9).length()<102)
				textInternacional1.setBounds(50, 100, 620, 16);
			else
				textInternacional1.setBounds(50, 100, 620, 30);
			textInternacional1.setLineWrap(true);
			textInternacional1.setVisible(false);
			panelMostrarCategorias.add(textInternacional1);
			JTextArea textInternacional2 = new JTextArea("- "+titulares.get(10));
			textInternacional2.setOpaque(false);
			textInternacional2.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
			textInternacional2.setEditable(false);
			textInternacional2.setEnabled(false);
			textInternacional2.setDisabledTextColor(new Color(0, 0, 0));
			textInternacional2.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			if(titulares.get(10).length()<102)
				textInternacional2.setBounds(50, 100, 620, 16);
			else
				textInternacional2.setBounds(50, 100, 620, 30);
			textInternacional2.setLineWrap(true);
			textInternacional2.setVisible(false);
			panelMostrarCategorias.add(textInternacional2);
			JTextArea textInternacional3 = new JTextArea("- "+titulares.get(11));
			textInternacional3.setOpaque(false);
			textInternacional3.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
			textInternacional3.setEditable(false);
			textInternacional3.setEnabled(false);
			textInternacional3.setDisabledTextColor(new Color(0, 0, 0));
			textInternacional3.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			if(titulares.get(11).length()<102)
				textInternacional3.setBounds(50, 100, 620, 16);
			else
				textInternacional3.setBounds(50, 100, 620, 30);
			textInternacional3.setLineWrap(true);
			textInternacional3.setVisible(false);
			panelMostrarCategorias.add(textInternacional3);
		
		JLabel lblMusica = new JLabel("Música");
		lblMusica.setFont(new Font("Fira Sans", Font.BOLD, 15));
		lblMusica.setBounds(50, 100, 150, 25);
		lblMusica.setVisible(false);
		panelMostrarCategorias.add(lblMusica);


			JTextArea textMusica1 = new JTextArea("- "+titulares.get(12));
			textMusica1.setOpaque(false);
			textMusica1.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
			textMusica1.setEditable(false);
			textMusica1.setEnabled(false);
			textMusica1.setDisabledTextColor(new Color(0, 0, 0));
			textMusica1.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			if(titulares.get(12).length()<102)
				textMusica1.setBounds(50, 100, 620, 16);
			else
				textMusica1.setBounds(50, 100, 620, 30);
			textMusica1.setLineWrap(true);
			textMusica1.setVisible(false);
			panelMostrarCategorias.add(textMusica1);
			JTextArea textMusica2 = new JTextArea("- "+titulares.get(13));
			textMusica2.setOpaque(false);
			textMusica2.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
			textMusica2.setEditable(false);
			textMusica2.setEnabled(false);
			textMusica2.setDisabledTextColor(new Color(0, 0, 0));
			textMusica2.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			if(titulares.get(13).length()<102)
				textMusica2.setBounds(50, 100, 620, 16);
			else
				textMusica2.setBounds(50, 100, 620, 30);
			textMusica2.setLineWrap(true);
			textMusica2.setVisible(false);
			panelMostrarCategorias.add(textMusica2);
			JTextArea textMusica3 = new JTextArea("- "+titulares.get(14));
			textMusica3.setOpaque(false);
			textMusica3.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
			textMusica3.setEditable(false);
			textMusica3.setEnabled(false);
			textMusica3.setDisabledTextColor(new Color(0, 0, 0));
			textMusica3.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			if(titulares.get(14).length()<102)
				textMusica3.setBounds(50, 100, 620, 16);
			else
				textMusica3.setBounds(50, 100, 620, 30);
			textMusica3.setLineWrap(true);
			textMusica3.setVisible(false);
			panelMostrarCategorias.add(textMusica3);
		
		JLabel lblMedioAmbiente = new JLabel("Medio Ambiente");
		lblMedioAmbiente.setFont(new Font("Fira Sans", Font.BOLD, 15));
		lblMedioAmbiente.setBounds(50, 100, 150, 25);
		lblMedioAmbiente.setVisible(false);
		panelMostrarCategorias.add(lblMedioAmbiente);


			JTextArea textMedioambiente1 = new JTextArea("- "+titulares.get(15));
			textMedioambiente1.setOpaque(false);
			textMedioambiente1.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
			textMedioambiente1.setEditable(false);
			textMedioambiente1.setEnabled(false);
			textMedioambiente1.setDisabledTextColor(new Color(0, 0, 0));
			textMedioambiente1.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			if(titulares.get(15).length()<102)
				textMedioambiente1.setBounds(50, 100, 620, 16);
			else
				textMedioambiente1.setBounds(50, 100, 620, 30);
			textMedioambiente1.setLineWrap(true);
			textMedioambiente1.setVisible(false);
			panelMostrarCategorias.add(textMedioambiente1);
			JTextArea textMedioambiente2 = new JTextArea("- "+titulares.get(16));
			textMedioambiente2.setOpaque(false);
			textMedioambiente2.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
			textMedioambiente2.setEditable(false);
			textMedioambiente2.setEnabled(false);
			textMedioambiente2.setDisabledTextColor(new Color(0, 0, 0));
			textMedioambiente2.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			if(titulares.get(16).length()<102)
				textMedioambiente2.setBounds(50, 100, 620, 16);
			else
				textMedioambiente2.setBounds(50, 100, 620, 30);
			textMedioambiente2.setLineWrap(true);
			textMedioambiente2.setVisible(false);
			panelMostrarCategorias.add(textMedioambiente2);
			JTextArea textMedioambiente3 = new JTextArea("- "+titulares.get(17));
			textMedioambiente3.setOpaque(false);
			textMedioambiente3.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 10));
			textMedioambiente3.setEditable(false);
			textMedioambiente3.setEnabled(false);
			textMedioambiente3.setDisabledTextColor(new Color(0, 0, 0));
			textMedioambiente3.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			if(titulares.get(17).length()<102)
				textMedioambiente3.setBounds(50, 100, 620, 16);
			else
				textMedioambiente3.setBounds(50, 100, 620, 30);
			textMedioambiente3.setLineWrap(true);
			textMedioambiente3.setVisible(false);
			panelMostrarCategorias.add(textMedioambiente3);
	}

	private void panel_administracion(Panel panelGeneralAdmin) {
		// Título del panel
		JLabel lblCategoriasFavoritas = new JLabel("Administración");
		lblCategoriasFavoritas.setForeground(new Color(36, 31, 49));
		lblCategoriasFavoritas.setFont(new Font("Noto Mono", Font.BOLD, 28));
		lblCategoriasFavoritas.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(28, 113, 216), new Color(0, 0, 0)));
		lblCategoriasFavoritas.setBackground(new Color(98, 160, 234));
		lblCategoriasFavoritas.setOpaque(true);
		lblCategoriasFavoritas.setHorizontalAlignment(SwingConstants.CENTER);
		lblCategoriasFavoritas.setBounds(193, 89, 312, 64);
		panelGeneralAdmin.add(lblCategoriasFavoritas);		
	}

	private JCheckBox [] categorias_favoritas(Panel panelCategoriasFavoritas) {
		// Título del panel
		JLabel lblCategoriasFavoritas = new JLabel("Categorías");
		lblCategoriasFavoritas.setForeground(new Color(36, 31, 49));
		lblCategoriasFavoritas.setFont(new Font("Noto Mono", Font.BOLD, 28));
		lblCategoriasFavoritas.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(28, 113, 216), new Color(0, 0, 0)));
		lblCategoriasFavoritas.setBackground(new Color(98, 160, 234));
		lblCategoriasFavoritas.setOpaque(true);
		lblCategoriasFavoritas.setHorizontalAlignment(SwingConstants.CENTER);
		lblCategoriasFavoritas.setBounds(193, 30, 312, 64);
		panelCategoriasFavoritas.add(lblCategoriasFavoritas);

		JLabel lblInfoseleccion = new JLabel("Selecciona los periódicos de las categorías que más te gusten.");
		lblInfoseleccion.setFont(new Font("Cantarell", Font.PLAIN, 13));
		lblInfoseleccion.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoseleccion.setBounds(12, 107, 676, 15);
		panelCategoriasFavoritas.add(lblInfoseleccion);
		
		// Las categorías
		JLabel lblEconomia = new JLabel("Economía");
		lblEconomia.setFont(new Font("Fira Sans", Font.BOLD, 20));
		lblEconomia.setBounds(152, 134, 149, 41);
		panelCategoriasFavoritas.add(lblEconomia);
		
		JLabel lblDeportes = new JLabel("Deportes");
		lblDeportes.setFont(new Font("Fira Sans", Font.BOLD, 20));
		lblDeportes.setBounds(468, 134, 149, 41);
		panelCategoriasFavoritas.add(lblDeportes);
		
		JLabel lblNacional = new JLabel("Nacional");
		lblNacional.setFont(new Font("Fira Sans", Font.BOLD, 20));
		lblNacional.setBounds(152, 286, 149, 41);
		panelCategoriasFavoritas.add(lblNacional);
		
		JLabel lblInternacional = new JLabel("Internacional");
		lblInternacional.setFont(new Font("Fira Sans", Font.BOLD, 20));
		lblInternacional.setBounds(468, 286, 182, 41);
		panelCategoriasFavoritas.add(lblInternacional);
		
		JLabel lblMusica = new JLabel("Música");
		lblMusica.setFont(new Font("Fira Sans", Font.BOLD, 20));
		lblMusica.setBounds(152, 453, 149, 41);
		panelCategoriasFavoritas.add(lblMusica);
		
		JLabel lblMedioAmbiente = new JLabel("Medio Ambiente");
		lblMedioAmbiente.setFont(new Font("Fira Sans", Font.BOLD, 20));
		lblMedioAmbiente.setBounds(468, 453, 207, 41);
		panelCategoriasFavoritas.add(lblMedioAmbiente);

		JCheckBox [] todosCheckboxes = new JCheckBox[CANTIDAD_CATEGORIAS];
		
		// El usuario seleccionará los periódicos que quiera
		JCheckBox chckbxEconomia1 = new JCheckBox("El país");
		chckbxEconomia1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxEconomia1.setForeground(new Color(0, 0, 0));
		chckbxEconomia1.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxEconomia1.setBackground(new Color(255, 190, 111));
		chckbxEconomia1.setBounds(152, 183, 129, 23);
		chckbxEconomia1.setEnabled(false);
		panelCategoriasFavoritas.add(chckbxEconomia1);
		todosCheckboxes[0] = chckbxEconomia1;

		JCheckBox chckbxEconomia2 = new JCheckBox("Es radio");
		chckbxEconomia2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxEconomia2.setForeground(new Color(0, 0, 0));
		chckbxEconomia2.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxEconomia2.setBackground(new Color(255, 190, 111));
		chckbxEconomia2.setBounds(152, 210, 129, 23);
		chckbxEconomia2.setEnabled(false);
		panelCategoriasFavoritas.add(chckbxEconomia2);
		todosCheckboxes[1] = chckbxEconomia2;
		
		JCheckBox chckbxEconomia3 = new JCheckBox("El economista");
		chckbxEconomia3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxEconomia3.setForeground(new Color(0, 0, 0));
		chckbxEconomia3.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxEconomia3.setBackground(new Color(255, 190, 111));
		chckbxEconomia3.setBounds(152, 237, 129, 23);
		chckbxEconomia3.setEnabled(false);
		panelCategoriasFavoritas.add(chckbxEconomia3);
		todosCheckboxes[2] = chckbxEconomia3;
		
		JCheckBox chckbxDeportes1 = new JCheckBox("Marca");
		chckbxDeportes1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxDeportes1.setForeground(new Color(0, 0, 0));
		chckbxDeportes1.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxDeportes1.setBackground(new Color(255, 190, 111));
		chckbxDeportes1.setBounds(468, 183, 129, 23);
		chckbxDeportes1.setEnabled(false);
		panelCategoriasFavoritas.add(chckbxDeportes1);
		todosCheckboxes[3] = chckbxDeportes1;
		
		JCheckBox chckbxDeportes2 = new JCheckBox("El país");
		chckbxDeportes2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxDeportes2.setForeground(new Color(0, 0, 0));
		chckbxDeportes2.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxDeportes2.setBackground(new Color(255, 190, 111));
		chckbxDeportes2.setBounds(468, 210, 129, 23);
		chckbxDeportes2.setEnabled(false);
		panelCategoriasFavoritas.add(chckbxDeportes2);
		todosCheckboxes[4] = chckbxDeportes2;
		
		JCheckBox chckbxDeportes3 = new JCheckBox("Mundo deportivo");
		chckbxDeportes3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxDeportes3.setForeground(new Color(0, 0, 0));
		chckbxDeportes3.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxDeportes3.setBackground(new Color(255, 190, 111));
		chckbxDeportes3.setBounds(468, 237, 129, 23);
		chckbxDeportes3.setEnabled(false);
		panelCategoriasFavoritas.add(chckbxDeportes3);
		todosCheckboxes[5] = chckbxDeportes3;
		
		JCheckBox chckbxNacional1 = new JCheckBox("Miteco");
		chckbxNacional1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxNacional1.setForeground(new Color(0, 0, 0));
		chckbxNacional1.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxNacional1.setBackground(new Color(255, 190, 111));
		chckbxNacional1.setBounds(152, 335, 129, 23);
		chckbxNacional1.setEnabled(false);
		panelCategoriasFavoritas.add(chckbxNacional1);
		todosCheckboxes[6] = chckbxNacional1;
		
		JCheckBox chckbxNacional2 = new JCheckBox("El mundo");
		chckbxNacional2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxNacional2.setForeground(new Color(0, 0, 0));
		chckbxNacional2.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxNacional2.setBackground(new Color(255, 190, 111));
		chckbxNacional2.setBounds(152, 362, 129, 23);
		chckbxNacional2.setEnabled(false);
		panelCategoriasFavoritas.add(chckbxNacional2);
		todosCheckboxes[7] = chckbxNacional2;
		
		JCheckBox chckbxNacional3 = new JCheckBox("El diario");
		chckbxNacional3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxNacional3.setForeground(new Color(0, 0, 0));
		chckbxNacional3.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxNacional3.setBackground(new Color(255, 190, 111));
		chckbxNacional3.setBounds(152, 389, 129, 23);
		chckbxNacional3.setEnabled(false);
		panelCategoriasFavoritas.add(chckbxNacional3);
		todosCheckboxes[8] = chckbxNacional3;
		
		JCheckBox chckbxInternacional1 = new JCheckBox("Telemundo");
		chckbxInternacional1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxInternacional1.setForeground(new Color(0, 0, 0));
		chckbxInternacional1.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxInternacional1.setBackground(new Color(255, 190, 111));
		chckbxInternacional1.setBounds(468, 335, 129, 23);
		chckbxInternacional1.setEnabled(false);
		panelCategoriasFavoritas.add(chckbxInternacional1);
		todosCheckboxes[9] = chckbxInternacional1;
		
		JCheckBox chckbxInternacional2 = new JCheckBox("BBC");
		chckbxInternacional2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxInternacional2.setForeground(new Color(0, 0, 0));
		chckbxInternacional2.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxInternacional2.setBackground(new Color(255, 190, 111));
		chckbxInternacional2.setBounds(468, 362, 129, 23);
		chckbxInternacional2.setEnabled(false);
		panelCategoriasFavoritas.add(chckbxInternacional2);
		todosCheckboxes[10] = chckbxInternacional2;
		
		JCheckBox chckbxInternacional3 = new JCheckBox("DW");
		chckbxInternacional3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxInternacional3.setForeground(new Color(0, 0, 0));
		chckbxInternacional3.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxInternacional3.setBackground(new Color(255, 190, 111));
		chckbxInternacional3.setBounds(468, 389, 129, 23);
		chckbxInternacional3.setEnabled(false);
		panelCategoriasFavoritas.add(chckbxInternacional3);
		todosCheckboxes[11] = chckbxInternacional3;
		
		JCheckBox chckbxMusica1 = new JCheckBox("El mundo");
		chckbxMusica1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxMusica1.setForeground(new Color(0, 0, 0));
		chckbxMusica1.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxMusica1.setBackground(new Color(255, 190, 111));
		chckbxMusica1.setBounds(152, 502, 129, 23);
		chckbxMusica1.setEnabled(false);
		panelCategoriasFavoritas.add(chckbxMusica1);
		todosCheckboxes[12] = chckbxMusica1;
		
		JCheckBox chckbxMusica2 = new JCheckBox("El país");
		chckbxMusica2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxMusica2.setForeground(new Color(0, 0, 0));
		chckbxMusica2.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxMusica2.setBackground(new Color(255, 190, 111));
		chckbxMusica2.setBounds(152, 529, 129, 23);
		chckbxMusica2.setEnabled(false);
		panelCategoriasFavoritas.add(chckbxMusica2);
		todosCheckboxes[13] = chckbxMusica2;
		
		JCheckBox chckbxMusica3 = new JCheckBox("Xataka");
		chckbxMusica3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxMusica3.setForeground(new Color(0, 0, 0));
		chckbxMusica3.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxMusica3.setBackground(new Color(255, 190, 111));
		chckbxMusica3.setBounds(152, 556, 129, 23);
		chckbxMusica3.setEnabled(false);
		panelCategoriasFavoritas.add(chckbxMusica3);
		todosCheckboxes[14] = chckbxMusica3;
		
		JCheckBox chckbxMedioambiente1 = new JCheckBox("Faro de vigo");
		chckbxMedioambiente1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxMedioambiente1.setForeground(new Color(0, 0, 0));
		chckbxMedioambiente1.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxMedioambiente1.setBackground(new Color(255, 190, 111));
		chckbxMedioambiente1.setBounds(468, 502, 129, 23);
		chckbxMedioambiente1.setEnabled(false);
		panelCategoriasFavoritas.add(chckbxMedioambiente1);
		todosCheckboxes[15] = chckbxMedioambiente1;
		
		JCheckBox chckbxMedioambiente2 = new JCheckBox("Info libre");
		chckbxMedioambiente2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxMedioambiente2.setForeground(new Color(0, 0, 0));
		chckbxMedioambiente2.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxMedioambiente2.setBackground(new Color(255, 190, 111));
		chckbxMedioambiente2.setBounds(468, 529, 129, 23);
		chckbxMedioambiente2.setEnabled(false);
		panelCategoriasFavoritas.add(chckbxMedioambiente2);
		todosCheckboxes[16] = chckbxMedioambiente2;
		
		JCheckBox chckbxMedioambiente3 = new JCheckBox("El español");
		chckbxMedioambiente3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxMedioambiente3.setForeground(new Color(0, 0, 0));
		chckbxMedioambiente3.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxMedioambiente3.setBackground(new Color(255, 190, 111));
		chckbxMedioambiente3.setBounds(468, 556, 129, 23);
		chckbxMedioambiente3.setEnabled(false);
		panelCategoriasFavoritas.add(chckbxMedioambiente3);
		todosCheckboxes[17] = chckbxMedioambiente3;

		lblCategoriasIncorrectas = new JLabel("Por favor, seleccione al menos un periódico");
		lblCategoriasIncorrectas.setForeground(new Color(237, 51, 59));
		lblCategoriasIncorrectas.setHorizontalAlignment(SwingConstants.CENTER);
		lblCategoriasIncorrectas.setBounds(152, 593, 445, 15);
		lblCategoriasIncorrectas.setVisible(false);
		panelCategoriasFavoritas.add(lblCategoriasIncorrectas);
		
		return todosCheckboxes;
	}

	private void iniciar_sesion(Panel panelInicioSesion) {
		// Título del panel
		JLabel lblTituloIniciaSesion = new JLabel("Inicia sesión");
		lblTituloIniciaSesion.setForeground(new Color(36, 31, 49));
		lblTituloIniciaSesion.setFont(new Font("Noto Mono", Font.BOLD, 28));
		lblTituloIniciaSesion.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(28, 113, 216), new Color(0, 0, 0)));
		lblTituloIniciaSesion.setBackground(new Color(98, 160, 234));
		lblTituloIniciaSesion.setOpaque(true);
		lblTituloIniciaSesion.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloIniciaSesion.setBounds(193, 89, 312, 64);
		panelInicioSesion.add(lblTituloIniciaSesion);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombre.setBounds(193, 263, 137, 15);
		panelInicioSesion.add(lblNombre);
		
		JLabel lblContrasena = new JLabel("Contraseña");
		lblContrasena.setHorizontalAlignment(SwingConstants.RIGHT);
		lblContrasena.setBounds(193, 355, 137, 15);
		panelInicioSesion.add(lblContrasena);
		
		// Necesito que el usuario me diga su nombre y su contraseña para poder iniciar sesión
		txtNombre = new JTextField();
		txtNombre.setBounds(368, 261, 137, 19);
		txtNombre.setColumns(10);
		panelInicioSesion.add(txtNombre);

		pwdContrasenia = new JPasswordField();
		pwdContrasenia.setBounds(368, 353, 137, 19);
		panelInicioSesion.add(pwdContrasenia);

		lblSesionIncorrecta = new JLabel("El nombre o la contraseña no es correcto");
		lblSesionIncorrecta.setVisible(false);
		lblSesionIncorrecta.setForeground(new Color(237, 51, 59));
		lblSesionIncorrecta.setHorizontalAlignment(SwingConstants.CENTER);
		lblSesionIncorrecta.setBounds(193, 427, 312, 15);
		panelInicioSesion.add(lblSesionIncorrecta);
		
		// La contraseña se muestra con • así que tengo que poner un JRadioButton para poder leerla en claro
		JRadioButton rdbtnMostrar = new JRadioButton("Mostrar");
		rdbtnMostrar.setOpaque(false);
		rdbtnMostrar.setBounds(513, 351, 153, 23);
		rdbtnMostrar.setSelected(false);
		panelInicioSesion.add(rdbtnMostrar);
		Evento mostrarOcultarContraseña = new Evento("mostrar ocultar contraseña", rdbtnMostrar, pwdContrasenia);
		rdbtnMostrar.addActionListener(mostrarOcultarContraseña);
	}
}
