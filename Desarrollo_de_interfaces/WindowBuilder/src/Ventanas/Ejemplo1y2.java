package Ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Cursor;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JPasswordField;
import javax.swing.JScrollBar;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import java.awt.Color;
import javax.swing.JPanel;

public class Ejemplo1y2 {

	private JFrame frame;
	private JTextField textNombre;
	private final JTextField titulo = new JTextField();
	private JPasswordField contraseña;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ejemplo1y2 window = new Ejemplo1y2();
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
	public Ejemplo1y2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(192, 192, 192));
		frame.setBounds(100, 100, 411, 286);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel saludo = new JLabel("Bienbenido!");
		saludo.setBounds(10, 11, 86, 14);
		frame.getContentPane().add(saludo);
		
		JLabel nombre = new JLabel("Nombre");
		nombre.setToolTipText("Escribe el nombre de tu mascota");
		nombre.setBounds(20, 36, 46, 14);
		frame.getContentPane().add(nombre);
		
		textNombre = new JTextField();
		textNombre.setBounds(76, 33, 86, 20);
		frame.getContentPane().add(textNombre);
		textNombre.setColumns(10);
		titulo.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		titulo.setText("titulo");
		titulo.setBounds(361, 0, 34, 26);
		frame.getContentPane().add(titulo);
		titulo.setEnabled(false);
		titulo.setColumns(10);
		
		JButton botonNo = new JButton("No pulses!");
		botonNo.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		botonNo.setBounds(139, 95, 127, 23);
		frame.getContentPane().add(botonNo);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(380, 225, 154, 201);
		frame.getContentPane().add(textArea);
		
		JCheckBox opcion2 = new JCheckBox("Negro");
		opcion2.setActionCommand("Negro");
		opcion2.setBounds(20, 139, 97, 23);
		frame.getContentPane().add(opcion2);
		
		JRadioButton selecciona1 = new JRadioButton("Arriba");
		selecciona1.setBounds(20, 170, 109, 23);
		frame.getContentPane().add(selecciona1);
		
		JRadioButton selecciona2 = new JRadioButton("Abajo");
		selecciona2.setBounds(20, 196, 109, 23);
		frame.getContentPane().add(selecciona2);
		
		JCheckBox opcion1 = new JCheckBox("Blanco");
		opcion1.setActionCommand("Blanco");
		opcion1.setBounds(20, 113, 97, 23);
		frame.getContentPane().add(opcion1);
		
		contraseña = new JPasswordField();
		contraseña.setEchoChar('-');
		contraseña.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contraseña.setBounds(76, 64, 97, 20);
		frame.getContentPane().add(contraseña);
		
		JProgressBar progreso = new JProgressBar();
		progreso.setBounds(16, 233, 232, 14);
		frame.getContentPane().add(progreso);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(378, 33, 17, 181);
		frame.getContentPane().add(scrollBar);
		
		JSpinner numeros = new JSpinner();
		numeros.setBounds(261, 140, 55, 20);
		frame.getContentPane().add(numeros);
		
		JPanel panel = new JPanel();
		panel.setBounds(20, 109, 109, 110);
		frame.getContentPane().add(panel);
	}
}
