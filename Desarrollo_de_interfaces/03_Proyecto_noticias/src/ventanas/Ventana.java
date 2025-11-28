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
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Component;

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


public class Ventana extends JFrame{
	private JTextField txtNombre;
	private JTextField txtContrasea;
	private Timer tiempo;
	public static ArrayList<Usuario> usuarios;
	public static int rolUsuario = -1;
	
	public Ventana(int x, int y) {
		super();
		setUndecorated(true);
		setSize(x, y);
		setResizable(false);
		setLocationRelativeTo(null);
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
					tiempo.stop();
					Ventana ventanaPrincipal = new Ventana("Noticias diarias");
					ventanaPrincipal.setVisible(true);
					dispose();
				}
				if(barraProgreso.getValue()==80) {
					try {
						usuarios = LeerTxt.leerTodosUsuarios();
					} catch (IOException e1) {
						usuarios = new ArrayList<Usuario>();
						e1.printStackTrace();
					}
					if (usuarios.size() <=0) {
						tiempo.stop();
						barraProgreso.setValue(100);
						Ventana ventanaError = new Ventana("No se han cargado los usuarios correctamente", 450, 200);
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

	public Ventana(String title) throws HeadlessException {
		super();
		setTitle(title);
		iniciar_sesion();
		System.out.println(rolUsuario);
	}
	
	private void iniciar_sesion() {
		Dimension monitor = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = (int) monitor.getWidth() / 2 - 700 / 2;
		int alto = (int) monitor.getHeight() / 2 - 700 / 2;
		setBounds(ancho, alto, 700, 700);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("newsLogo.png")));
		
		Panel panelInicioSesion = new Panel();
		getContentPane().add(panelInicioSesion);
		
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
	
		JLabel lblSesionIncorrecta = new JLabel("El nombre o la contraseña no es correcto");
		lblSesionIncorrecta.setVisible(false);
		lblSesionIncorrecta.setForeground(new Color(237, 51, 59));
		lblSesionIncorrecta.setHorizontalAlignment(SwingConstants.CENTER);
		lblSesionIncorrecta.setBounds(193, 427, 312, 15);
		panelInicioSesion.add(lblSesionIncorrecta);
		
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.setBounds(294, 468, 117, 25);
		panelInicioSesion.add(btnEntrar);
		Evento comprobarSesion = new Evento("comprobar sesion", txtNombre, txtContrasea, lblSesionIncorrecta, usuarios);
		btnEntrar.addActionListener(comprobarSesion);
		
	}
}
