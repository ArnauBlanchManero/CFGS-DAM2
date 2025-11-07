package Ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class Ejemplo6 {

	private JFrame frame;
	private Timer tiempo;
	private Timer tiempo2;
	private int temporizador=4;
	private int porcentaje=10;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ejemplo6 window = new Ejemplo6();
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
	public Ejemplo6() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 128, 192));
		frame.setBounds(100, 100, 877, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel textoMostrar = new JLabel("FELIZ PUENTE");
		textoMostrar.setVisible(false);
		textoMostrar.setHorizontalAlignment(SwingConstants.CENTER);
		textoMostrar.setVerticalTextPosition(SwingConstants.TOP);
		textoMostrar.setOpaque(true);
		textoMostrar.setFont(new Font("Times New Roman", Font.BOLD, 25));
		textoMostrar.setBackground(new Color(128, 255, 0));
		textoMostrar.setBounds(96, 28, 260, 60);
		frame.getContentPane().add(textoMostrar);
		JLabel contador = new JLabel("");
		
		JButton startButton = new JButton("START");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textoMostrar.setVisible(false);
				contador.setText(String.valueOf(temporizador));
				tiempo.start();
			}
		});
		startButton.setBackground(new Color(0, 255, 255));
		startButton.setBounds(29, 126, 114, 83);
		frame.getContentPane().add(startButton);
		contador.setText(String.valueOf(temporizador+1));
		contador.setFont(new Font("Tahoma", Font.BOLD, 15));
		contador.setHorizontalAlignment(SwingConstants.CENTER);
		contador.setBounds(232, 126, 46, 83);
		frame.getContentPane().add(contador);
		
		JButton exitButton = new JButton("Salir");
		exitButton.setBackground(new Color(255, 128, 128));
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				System.exit(0);
			}
		});
		exitButton.setRolloverEnabled(false);
		exitButton.setVisible(false);
		exitButton.setBounds(335, 227, 89, 23);
		frame.getContentPane().add(exitButton);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 128, 255));
		panel.setBounds(435, 0, 434, 261);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("DESCARGAR");
		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				progressBar.setValue(0);
				tiempo2.start();
				
			}
		});
		btnNewButton.setBounds(38, 36, 162, 67);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Tiempo restante");
		lblNewLabel.setBackground(new Color(0, 0, 160));
		lblNewLabel.setOpaque(true);
		lblNewLabel.setForeground(new Color(128, 128, 255));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(227, 36, 112, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("10");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(260, 73, 46, 30);
		panel.add(lblNewLabel_1);
		
		progressBar.setBounds(51, 167, 302, 29);
		panel.add(progressBar);
		
		JLabel lblNewLabel_2 = new JLabel("Preparando café...");
		lblNewLabel_2.setBounds(51, 207, 121, 14);
		panel.add(lblNewLabel_2);

		tiempo = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				temporizador--;
				contador.setText(String.valueOf(temporizador));
				if (temporizador==0) {
					tiempo.stop();
					textoMostrar.setVisible(true);
					startButton.setText("RESET");
					temporizador=5;
					exitButton.setRolloverEnabled(true);
					exitButton.setVisible(true);
				}
			}
		});
		tiempo2 = new Timer(500, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				porcentaje--;
				progressBar.setValue((10-porcentaje)*10);
				lblNewLabel_1.setText(String.valueOf(porcentaje));
				lblNewLabel_2.setLocation(lblNewLabel_2.getX()+10, lblNewLabel_2.getY());
				if (progressBar.getValue()==100) {
					lblNewLabel_2.setText("Café preparado :)");
					btnNewButton.setRolloverEnabled(false);
					btnNewButton.setText("DESCARGADO");
					String msg = "¡¡Henhorabuena!! \n\n Vamos que no queda nada. \n ¡ÁNIMO!";
					JOptionPane.showMessageDialog(null, msg, "AL RECREO?", 1);
					btnNewButton.setVisible(false);
					exitButton.setRolloverEnabled(true);
					exitButton.setVisible(true);
				}
				if (porcentaje==0) {
					tiempo2.stop();
				}
			}
		});
	}
}
