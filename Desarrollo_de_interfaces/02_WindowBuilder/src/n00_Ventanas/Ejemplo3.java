package n00_Ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;

public class Ejemplo3 {

	private JFrame frame;
	private JTextField textField;
	private int contador=0;
	private JPanel panel_1;
	private JPanel panel_2;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ejemplo3 window = new Ejemplo3();
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
	public Ejemplo3() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 255, 0));
		frame.getContentPane().add(panel, "name_12536659225200");
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("CLICK!");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contador++;
				textField.setText(Integer.toString(contador));
			}
		});
		btnNewButton.setBounds(10, 54, 89, 23);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Número de clicks");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel.setBounds(108, 58, 110, 14);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(218, 55, 28, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("Siguiente");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(false);
				panel_1.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(345, 238, 89, 23);
		panel.add(btnNewButton_1);
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 128, 0));
		frame.getContentPane().add(panel_1, "name_12540697933700");
		panel_1.setLayout(null);
		
		btnNewButton_2 = new JButton("Atras");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(true);
				panel_1.setVisible(false);
			}
		});
		btnNewButton_2.setBounds(0, 238, 89, 23);
		panel_1.add(btnNewButton_2);
		
		btnNewButton_3 = new JButton("Siguiente");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_1.setVisible(false);
				panel_2.setVisible(true);
			}
		});
		btnNewButton_3.setBounds(345, 238, 89, 23);
		panel_1.add(btnNewButton_3);
		
		panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 128, 255));
		frame.getContentPane().add(panel_2, "name_12548032333700");
		panel_2.setLayout(null);
		
		btnNewButton_4 = new JButton("Atrás");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_1.setVisible(true);
				panel_2.setVisible(false);
			}
		});
		btnNewButton_4.setBounds(0, 238, 89, 23);
		panel_2.add(btnNewButton_4);
		
		lblNewLabel_1 = new JLabel("¡GRACIAS!");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Monospaced", Font.BOLD, 50));
		lblNewLabel_1.setBounds(0, 85, 434, 65);
		panel_2.add(lblNewLabel_1);
	}
}
