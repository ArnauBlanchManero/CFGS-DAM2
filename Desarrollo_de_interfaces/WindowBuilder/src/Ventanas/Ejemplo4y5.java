package Ventanas;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.event.ActionEvent;

public class Ejemplo4y5 {

	private JFrame frame;
	private JPanel panel_azul;
	private JPanel panel_naranja;
	private JPanel panel_rosa;
	private JPanel panelConFondo;
	private Dimension monitor = Toolkit.getDefaultToolkit().getScreenSize();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ejemplo4y5 window = new Ejemplo4y5();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Ejemplo4y5() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Ejemplo4y5.class.getResource("/O-Berry.jpg")));
		frame.getContentPane().setBackground(new Color(0, 64, 128));
		frame.setBounds(0, 0, 589, 563);
		int ancho = (int) monitor.getWidth() / 2 - frame.getWidth() / 2;
		int alto = (int) monitor.getHeight() / 2 - frame.getHeight() / 2;
		frame.setLocation(ancho, alto);
		// frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(buscarImagen());
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 573, 524);
		frame.getContentPane().add(layeredPane);

		panel_azul = new JPanel();
		layeredPane.setLayer(panel_azul, 4);
		panel_azul.setBackground(new Color(0, 128, 255));
		panel_azul.setBounds(0, 0, 573, 524);
		layeredPane.add(panel_azul);
		panel_azul.setLayout(null);

		panel_rosa = new JPanel();
		layeredPane.setLayer(panel_rosa, 2);
		panel_rosa.setBackground(new Color(255, 0, 128));
		panel_rosa.setBounds(0, 0, 573, 524);
		layeredPane.add(panel_rosa);
		panel_rosa.setLayout(null);

		panel_naranja = new JPanel();
		layeredPane.setLayer(panel_naranja, 1);
		panel_naranja.setBackground(new Color(255, 128, 64));
		panel_naranja.setBounds(0, 0, 573, 524);
		layeredPane.add(panel_naranja);
		panel_naranja.setLayout(null);

		JButton btnNewButton_1 = new JButton("Naranja");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.setLayer(panel_naranja, 6);
				layeredPane.setLayer(panel_azul, 1);
				layeredPane.setLayer(panel_naranja, 3);
			}
		});
		btnNewButton_1.setActionCommand("Naranja");
		btnNewButton_1.setBounds(400, 60, 89, 23);
		panel_azul.add(btnNewButton_1);

		JButton btnNewButton_3 = new JButton("Rosa");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.setLayer(panel_rosa, 5);
				layeredPane.setLayer(panel_naranja, 3);
				layeredPane.setLayer(panel_azul, 1);
			}
		});
		btnNewButton_3.setActionCommand("Rosa");
		btnNewButton_3.setBounds(100, 60, 89, 23);
		panel_azul.add(btnNewButton_3);

		JButton btnNewButton = new JButton("Azul");
		btnNewButton.setActionCommand("Naranja");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.setLayer(panel_azul, 4);
				layeredPane.setLayer(panel_rosa, 2);
				layeredPane.setLayer(panel_naranja, 3);
			}
		});
		btnNewButton.setBounds(400, 80, 89, 23);
		panel_rosa.add(btnNewButton);

		JButton btnNewButton_7 = new JButton("Naranja");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.setLayer(panel_naranja, 6);
				layeredPane.setLayer(panel_azul, 1);
				layeredPane.setLayer(panel_rosa, 2);
			}
		});
		btnNewButton_7.setBounds(100, 80, 89, 23);
		panel_rosa.add(btnNewButton_7);

		JButton btnNewButton_4 = new JButton("Azul");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.setLayer(panel_azul, 4);
				layeredPane.setLayer(panel_rosa, 2);
				layeredPane.setLayer(panel_naranja, 3);
			}
		});
		btnNewButton_4.setBounds(100, 100, 89, 23);
		panel_naranja.add(btnNewButton_4);

		JButton btnNewButton_6 = new JButton("Rosa");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.setLayer(panel_rosa, 5);
				layeredPane.setLayer(panel_naranja, 3);
				layeredPane.setLayer(panel_azul, 1);
			}
		});
		btnNewButton_6.setBounds(400, 100, 89, 23);
		panel_naranja.add(btnNewButton_6);
	}

	private Component buscarImagen() {
		BufferedImage fondo = null;
		//JPanel panelConFondo = null;
		// Cargar la imagen
		try {
			File ruta = new File("src/clubPenguin.jpg");
			ruta.setReadable(true);
			System.out.println(ruta.getAbsolutePath());
			fondo = ImageIO.read(ruta);
			// asignar la imagen a un jpanel
			Image foto = fondo;
			panelConFondo=new JPanel() {
				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.drawImage(foto, 0, 0, 1200, 675, null);
				}
			};
			int ancho = (int) monitor.getWidth() / 2 - 1200 / 2;
			int alto = (int) monitor.getHeight() / 2 - 675 / 2;
			panelConFondo.setBounds(ancho, alto, 1200, 675);
			System.out.println("Bien?");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Excepcion");
		}
		System.out.println(panelConFondo.toString());
		return panelConFondo;
	}
}
