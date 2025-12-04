package ventanas;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;

import javax.swing.border.SoftBevelBorder;

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


public class Ventana extends JFrame{
	public static final int CANTIDAD_CATEGORIAS = 18;
	private JTextField txtNombre;
	private JTextField txtContrasea;
	private JLabel lblSesionIncorrecta;
	private JLabel lblCategoriasIncorrectas;
	private Timer tiempo;
	private JLayeredPane todosPaneles;
	public static ArrayList<Usuario> usuarios;
	public static ArrayList<String> titulares;
	public static int rolUsuario = -1;
	
	public Ventana(int x, int y) {
		super();
		setUndecorated(true);
		setSize(x, y);
		setResizable(false);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("newsLogo.png")));
		Panel panelCarga = buscarImagen();
		getContentPane().add(panelCarga);
		panelCarga.setVisible(true);
		JProgressBar barraProgreso = new JProgressBar();
		barraProgreso.setStringPainted(true);
		barraProgreso.setBounds(10, 300, 620, 20);
		panelCarga.add(barraProgreso);
		tiempo = new Timer(200, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				barraProgreso.setValue(barraProgreso.getValue()+4);
				if(barraProgreso.getValue()==100) {
					dispose();
					Ventana ventanaPrincipal = new Ventana("Noticias diarias");
					ventanaPrincipal.setVisible(true);
					tiempo.stop();
				}
				if(barraProgreso.getValue()==80) {
					try {
						setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						usuarios = LeerTxt.leerTodosUsuarios();
						titulares = LeerTxt.leerTodasNoticias();
					} catch (IOException e1) {
						usuarios = new ArrayList<Usuario>();
						e1.printStackTrace();
					}
					if (usuarios.size() < 4) {
						//TODO comprobar que haya 1 admin y 3 usuarios
						tiempo.stop();
						barraProgreso.setValue(100);
						Ventana ventanaError = new Ventana("No se han cargado los usuarios correctamente", 450, 200);
						ventanaError.setVisible(true);
						dispose();
					}
					if (titulares == null) {
						// Comprobar que el array este vacio 
						tiempo.stop();
						barraProgreso.setValue(100);
						Ventana ventanaError = new Ventana("No se he encontrado alguno de los titulares", 450, 200);
						ventanaError.setVisible(true);
						dispose();
					}
				}
			}
		});
		tiempo.start();
	}

	private Panel buscarImagen() {
		BufferedImage fondo = null;
		Panel panelConFondo = null;
		// Cargar la imagen
		File ruta = new File("src/ventanas/fondoPlaya.jpg");
		ruta.setReadable(true);
		try {
			fondo = ImageIO.read(ruta);
			// asignar la imagen a un jpanel
			Image foto = fondo;
			panelConFondo=new Panel() {
				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.drawImage(foto.getScaledInstance(640, 323, Image.SCALE_SMOOTH), 0, 0, 640, 323, null);
				}
			};
		} catch (IOException e) {
			panelConFondo=new Panel("Error al cargar la imagen");
//			panelConFondo.setBackground(new Color(255, 118, 114));
			e.printStackTrace();
		}
		return panelConFondo;
	}
	
	public Ventana(String mensaje, int x, int y) {
		super();
		setTitle("ERROR");
		setSize(x, y);
		setResizable(false);
		setLocationRelativeTo(null);
		Panel informacionError = new Panel(mensaje);
		getContentPane().add(informacionError);
		informacionError.setVisible(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("errorLogo.png")));
	}

	/**
	 * @wbp.parser.constructor
	 */
	public Ventana(String title) throws HeadlessException {
		super();
		Dimension monitor = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = (int) monitor.getWidth() / 2 - 700 / 2;
		int alto = (int) monitor.getHeight() / 2 - 700 / 2;
		setBounds(ancho, alto, 700, 700);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("newsLogo.png")));
		
		setTitle(title);
		todosPaneles = new JLayeredPane();
		todosPaneles.setSize(700, 700);
		todosPaneles.setLayout(null);
		getContentPane().add(todosPaneles);
		Panel panelInicioSesion = new Panel();
		todosPaneles.setLayer(panelInicioSesion, 6);
		Panel panelCategoriasFavoritas = new Panel();
		todosPaneles.setLayer(panelCategoriasFavoritas, 5);
		Panel panelMostrarCategorias = new Panel();
		todosPaneles.setLayer(panelCategoriasFavoritas, 4);
		Panel panelGeneralAdmin = new Panel();
		todosPaneles.setLayer(panelGeneralAdmin, 3);

		iniciar_sesion(panelInicioSesion);
		
		JCheckBox[] todosCheckboxes = categorias_favoritas(panelCategoriasFavoritas);
		
		mostrar_categorias_usuario(panelMostrarCategorias);
		
		panel_administracion(panelGeneralAdmin);


		JButton btnGuardarCategorias = new JButton("Guardar");
		btnGuardarCategorias.setBounds(314, 615, 117, 25);
		panelCategoriasFavoritas.add(btnGuardarCategorias);
		btnGuardarCategorias.setEnabled(false);
		Evento guardarCategorias = new Evento("guardar categorias", todosCheckboxes, lblCategoriasIncorrectas);
		btnGuardarCategorias.addActionListener(guardarCategorias);

		JButton btnMostrarCategorias = new JButton("Mostrar");
		btnMostrarCategorias.setBounds(300, 300, 117, 75);
		panelMostrarCategorias.add(btnMostrarCategorias);
		btnMostrarCategorias.setEnabled(false);
		Evento mostrarCategorias = new Evento("mostrar categorias", panelMostrarCategorias.getComponents());
		btnMostrarCategorias.addActionListener(mostrarCategorias);
		
		JButton btnEnviarCategorias = new JButton("Enviar");
		btnEnviarCategorias.setBounds(314, 615, 117, 25);
		panelGeneralAdmin.add(btnEnviarCategorias);
		btnEnviarCategorias.setEnabled(false);
		Evento enviarCategorias = new Evento("enviar categorias");
		btnEnviarCategorias.addActionListener(enviarCategorias);
		
		
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.setBounds(294, 468, 117, 25);
		panelInicioSesion.add(btnEntrar);
		panelInicioSesion.setEnabled(true);
		
		ArrayList<JButton> todosBotones = new ArrayList<JButton>();
		todosBotones.add(btnEntrar);
		todosBotones.add(btnGuardarCategorias);
		todosBotones.add(btnMostrarCategorias);
		todosBotones.add(btnEnviarCategorias);
		
		Evento comprobarSesion = new Evento("comprobar sesion", txtNombre, txtContrasea, lblSesionIncorrecta, usuarios, todosPaneles, todosBotones);
		btnEntrar.addActionListener(comprobarSesion);
		
		todosPaneles.add(panelInicioSesion);
		todosPaneles.add(panelCategoriasFavoritas);
		todosPaneles.add(panelMostrarCategorias);
		todosPaneles.add(panelGeneralAdmin);
	}
	
	private void mostrar_categorias_usuario(Panel panelMostrarCategorias) {
		// TODO Auto-generated method stub
		JLabel lblCategoriasFavoritas = new JLabel("Tus noticias");
		lblCategoriasFavoritas.setForeground(new Color(36, 31, 49));
		lblCategoriasFavoritas.setFont(new Font("Noto Mono", Font.BOLD, 28));
		lblCategoriasFavoritas.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(28, 113, 216), new Color(0, 0, 0)));
		lblCategoriasFavoritas.setBackground(new Color(98, 160, 234));
		lblCategoriasFavoritas.setOpaque(true);
		lblCategoriasFavoritas.setHorizontalAlignment(SwingConstants.CENTER);
		lblCategoriasFavoritas.setBounds(193, 12, 312, 64);
		panelMostrarCategorias.add(lblCategoriasFavoritas);	

		JLabel lblEconomia = new JLabel("Economía");
		lblEconomia.setFont(new Font("Fira Sans", Font.BOLD, 20));
		lblEconomia.setBounds(50, 100, 149, 41);
		lblEconomia.setVisible(false);
		panelMostrarCategorias.add(lblEconomia);
		
		JLabel lblDeportes = new JLabel("Deportes");
		lblDeportes.setFont(new Font("Fira Sans", Font.BOLD, 20));
		lblDeportes.setBounds(50, 100, 149, 41);
		lblDeportes.setVisible(false);
		panelMostrarCategorias.add(lblDeportes);
		
		JLabel lblNacional = new JLabel("Nacional");
		lblNacional.setFont(new Font("Fira Sans", Font.BOLD, 20));
		lblNacional.setBounds(50, 100, 149, 41);
		lblNacional.setVisible(false);
		panelMostrarCategorias.add(lblNacional);
		
		JLabel lblInternacional = new JLabel("Internacional");
		lblInternacional.setFont(new Font("Fira Sans", Font.BOLD, 20));
		lblInternacional.setBounds(50, 100, 182, 41);
		lblInternacional.setVisible(false);
		panelMostrarCategorias.add(lblInternacional);
		
		JLabel lblMusica = new JLabel("Música");
		lblMusica.setFont(new Font("Fira Sans", Font.BOLD, 20));
		lblMusica.setBounds(50, 100, 149, 41);
		lblMusica.setVisible(false);
		panelMostrarCategorias.add(lblMusica);
		
		JLabel lblMedioAmbiente = new JLabel("Medio Ambiente");
		lblMedioAmbiente.setFont(new Font("Fira Sans", Font.BOLD, 20));
		lblMedioAmbiente.setBounds(50, 100, 207, 41);
		lblMedioAmbiente.setVisible(false);
		panelMostrarCategorias.add(lblMedioAmbiente);

//		boolean[] categoriasMostrar = Evento.usuarioLogueado.getCategorias();
	}

	private void panel_administracion(Panel panelGeneralAdmin) {
		
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
		
		JLabel lblCategoriasFavoritas = new JLabel("Categorías");
		lblCategoriasFavoritas.setForeground(new Color(36, 31, 49));
		lblCategoriasFavoritas.setFont(new Font("Noto Mono", Font.BOLD, 28));
		lblCategoriasFavoritas.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(28, 113, 216), new Color(0, 0, 0)));
		lblCategoriasFavoritas.setBackground(new Color(98, 160, 234));
		lblCategoriasFavoritas.setOpaque(true);
		lblCategoriasFavoritas.setHorizontalAlignment(SwingConstants.CENTER);
		lblCategoriasFavoritas.setBounds(193, 12, 312, 64);
		panelCategoriasFavoritas.add(lblCategoriasFavoritas);

		JLabel lblInfoseleccion = new JLabel("Selecciona los periódicos de las categorías que más te gusten.");
		lblInfoseleccion.setFont(new Font("Cantarell", Font.PLAIN, 13));
		lblInfoseleccion.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoseleccion.setBounds(12, 107, 676, 15);
		panelCategoriasFavoritas.add(lblInfoseleccion);

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
		
		JCheckBox chckbxEconomia1 = new JCheckBox("El país");
		chckbxEconomia1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxEconomia1.setForeground(new Color(0, 0, 0));
		chckbxEconomia1.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxEconomia1.setBackground(new Color(255, 190, 111));
		chckbxEconomia1.setBounds(152, 183, 129, 23);
		panelCategoriasFavoritas.add(chckbxEconomia1); // [8] 
		todosCheckboxes[0] = chckbxEconomia1;
//		chckbxEconomia1.isSelected();

		JCheckBox chckbxEconomia2 = new JCheckBox("Es radio");
		chckbxEconomia2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxEconomia2.setForeground(new Color(0, 0, 0));
		chckbxEconomia2.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxEconomia2.setBackground(new Color(255, 190, 111));
		chckbxEconomia2.setBounds(152, 210, 129, 23);
		panelCategoriasFavoritas.add(chckbxEconomia2);
		todosCheckboxes[1] = chckbxEconomia2;
		
		JCheckBox chckbxEconomia3 = new JCheckBox("El economista");
		chckbxEconomia3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxEconomia3.setForeground(new Color(0, 0, 0));
		chckbxEconomia3.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxEconomia3.setBackground(new Color(255, 190, 111));
		chckbxEconomia3.setBounds(152, 237, 129, 23);
		panelCategoriasFavoritas.add(chckbxEconomia3);
		todosCheckboxes[2] = chckbxEconomia3;
		
		JCheckBox chckbxDeportes1 = new JCheckBox("Marca");
		chckbxDeportes1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxDeportes1.setForeground(new Color(0, 0, 0));
		chckbxDeportes1.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxDeportes1.setBackground(new Color(255, 190, 111));
		chckbxDeportes1.setBounds(468, 183, 129, 23);
		panelCategoriasFavoritas.add(chckbxDeportes1);
		todosCheckboxes[3] = chckbxDeportes1;
		
		JCheckBox chckbxDeportes2 = new JCheckBox("El país");
		chckbxDeportes2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxDeportes2.setForeground(new Color(0, 0, 0));
		chckbxDeportes2.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxDeportes2.setBackground(new Color(255, 190, 111));
		chckbxDeportes2.setBounds(468, 210, 129, 23);
		panelCategoriasFavoritas.add(chckbxDeportes2);
		todosCheckboxes[4] = chckbxDeportes2;
		
		JCheckBox chckbxDeportes3 = new JCheckBox("Mundo deportivo");
		chckbxDeportes3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxDeportes3.setForeground(new Color(0, 0, 0));
		chckbxDeportes3.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxDeportes3.setBackground(new Color(255, 190, 111));
		chckbxDeportes3.setBounds(468, 237, 129, 23);
		panelCategoriasFavoritas.add(chckbxDeportes3);
		todosCheckboxes[5] = chckbxDeportes3;
		
		JCheckBox chckbxNacional1 = new JCheckBox("Miteco");
		chckbxNacional1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxNacional1.setForeground(new Color(0, 0, 0));
		chckbxNacional1.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxNacional1.setBackground(new Color(255, 190, 111));
		chckbxNacional1.setBounds(152, 335, 129, 23);
		panelCategoriasFavoritas.add(chckbxNacional1);
		todosCheckboxes[6] = chckbxNacional1;
		
		JCheckBox chckbxNacional2 = new JCheckBox("El mundo");
		chckbxNacional2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxNacional2.setForeground(new Color(0, 0, 0));
		chckbxNacional2.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxNacional2.setBackground(new Color(255, 190, 111));
		chckbxNacional2.setBounds(152, 362, 129, 23);
		panelCategoriasFavoritas.add(chckbxNacional2);
		todosCheckboxes[7] = chckbxNacional2;
		
		JCheckBox chckbxNacional3 = new JCheckBox("El diario");
		chckbxNacional3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxNacional3.setForeground(new Color(0, 0, 0));
		chckbxNacional3.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxNacional3.setBackground(new Color(255, 190, 111));
		chckbxNacional3.setBounds(152, 389, 129, 23);
		panelCategoriasFavoritas.add(chckbxNacional3);
		todosCheckboxes[8] = chckbxNacional3;
		
		JCheckBox chckbxInternacional1 = new JCheckBox("Telemundo");
		chckbxInternacional1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxInternacional1.setForeground(new Color(0, 0, 0));
		chckbxInternacional1.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxInternacional1.setBackground(new Color(255, 190, 111));
		chckbxInternacional1.setBounds(468, 335, 129, 23);
		panelCategoriasFavoritas.add(chckbxInternacional1);
		todosCheckboxes[9] = chckbxInternacional1;
		
		JCheckBox chckbxInternacional2 = new JCheckBox("BBC");
		chckbxInternacional2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxInternacional2.setForeground(new Color(0, 0, 0));
		chckbxInternacional2.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxInternacional2.setBackground(new Color(255, 190, 111));
		chckbxInternacional2.setBounds(468, 362, 129, 23);
		panelCategoriasFavoritas.add(chckbxInternacional2);
		todosCheckboxes[10] = chckbxInternacional2;
		
		JCheckBox chckbxInternacional3 = new JCheckBox("DW");
		chckbxInternacional3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxInternacional3.setForeground(new Color(0, 0, 0));
		chckbxInternacional3.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxInternacional3.setBackground(new Color(255, 190, 111));
		chckbxInternacional3.setBounds(468, 389, 129, 23);
		panelCategoriasFavoritas.add(chckbxInternacional3);
		todosCheckboxes[11] = chckbxInternacional3;
		
		JCheckBox chckbxMusica1 = new JCheckBox("El mundo");
		chckbxMusica1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxMusica1.setForeground(new Color(0, 0, 0));
		chckbxMusica1.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxMusica1.setBackground(new Color(255, 190, 111));
		chckbxMusica1.setBounds(152, 502, 129, 23);
		panelCategoriasFavoritas.add(chckbxMusica1);
		todosCheckboxes[12] = chckbxMusica1;
		
		JCheckBox chckbxMusica2 = new JCheckBox("El país");
		chckbxMusica2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxMusica2.setForeground(new Color(0, 0, 0));
		chckbxMusica2.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxMusica2.setBackground(new Color(255, 190, 111));
		chckbxMusica2.setBounds(152, 529, 129, 23);
		panelCategoriasFavoritas.add(chckbxMusica2);
		todosCheckboxes[13] = chckbxMusica2;
		
		JCheckBox chckbxMusica3 = new JCheckBox("Xataka");
		chckbxMusica3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxMusica3.setForeground(new Color(0, 0, 0));
		chckbxMusica3.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxMusica3.setBackground(new Color(255, 190, 111));
		chckbxMusica3.setBounds(152, 556, 129, 23);
		panelCategoriasFavoritas.add(chckbxMusica3);
		todosCheckboxes[14] = chckbxMusica3;
		
		JCheckBox chckbxMedioambiente1 = new JCheckBox("Faro de vigo");
		chckbxMedioambiente1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxMedioambiente1.setForeground(new Color(0, 0, 0));
		chckbxMedioambiente1.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxMedioambiente1.setBackground(new Color(255, 190, 111));
		chckbxMedioambiente1.setBounds(468, 502, 129, 23);
		panelCategoriasFavoritas.add(chckbxMedioambiente1);
		todosCheckboxes[15] = chckbxMedioambiente1;
		
		JCheckBox chckbxMedioambiente2 = new JCheckBox("Info libre");
		chckbxMedioambiente2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxMedioambiente2.setForeground(new Color(0, 0, 0));
		chckbxMedioambiente2.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxMedioambiente2.setBackground(new Color(255, 190, 111));
		chckbxMedioambiente2.setBounds(468, 529, 129, 23);
		panelCategoriasFavoritas.add(chckbxMedioambiente2);
		todosCheckboxes[16] = chckbxMedioambiente2;
		
		JCheckBox chckbxMedioambiente3 = new JCheckBox("El español");
		chckbxMedioambiente3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxMedioambiente3.setForeground(new Color(0, 0, 0));
		chckbxMedioambiente3.setFont(new Font("Monospaced", Font.PLAIN, 12));
		chckbxMedioambiente3.setBackground(new Color(255, 190, 111));
		chckbxMedioambiente3.setBounds(468, 556, 129, 23);
		panelCategoriasFavoritas.add(chckbxMedioambiente3); // [25] 
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
//		Dimension monitor = Toolkit.getDefaultToolkit().getScreenSize();
//		int ancho = (int) monitor.getWidth() / 2 - 700 / 2;
//		int alto = (int) monitor.getHeight() / 2 - 700 / 2;
//		setBounds(ancho, alto, 700, 700);
//		setResizable(false);
//		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("newsLogo.png")));
		
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
		
		txtNombre = new JTextField();
		txtNombre.setBounds(368, 261, 137, 19);
		panelInicioSesion.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtContrasea = new JTextField();
		txtContrasea.setBounds(368, 353, 137, 19);
		panelInicioSesion.add(txtContrasea);
		txtContrasea.setColumns(10);
	
		lblSesionIncorrecta = new JLabel("El nombre o la contraseña no es correcto");
		lblSesionIncorrecta.setVisible(false);
		lblSesionIncorrecta.setForeground(new Color(237, 51, 59));
		lblSesionIncorrecta.setHorizontalAlignment(SwingConstants.CENTER);
		lblSesionIncorrecta.setBounds(193, 427, 312, 15);
		panelInicioSesion.add(lblSesionIncorrecta);
		
		
	}
}
