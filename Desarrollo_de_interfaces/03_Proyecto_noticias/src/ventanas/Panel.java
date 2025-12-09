package ventanas;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/*
 * Trabajo realizado por Arnau Blanch Manero
 */

public class Panel extends JPanel{
	
	public Panel(String mensaje) {
		// Este es el panel de los errores
		super();
		setBackground(new Color(255, 118, 114));
		setLayout(null);
		JLabel info = new JLabel();
		info.setBounds(10, 10, 428, 278);
		info.setText(mensaje);
		info.setHorizontalAlignment(SwingConstants.CENTER);
		info.setVerticalAlignment(SwingConstants.TOP);
		add(info);
	}

	public Panel(boolean carga) {
		// Todos mis paneles parten de esta configuración
		super();
		setBackground(new Color(133, 239, 237));
		setLayout(null);
		setBounds(0, 0, 700, 700);
		//TODO poner una barra de Menú con un “Acerca de” con los datos de la versión y	el desarrollador.
		if (!carga) {
			JLabel lblAcercaDe = new JLabel("Acerca de");
			lblAcercaDe.setBackground(new Color(28, 113, 216));
			lblAcercaDe.setForeground(new Color(36, 31, 49));
			lblAcercaDe.setHorizontalAlignment(SwingConstants.CENTER);
			lblAcercaDe.setFont(new Font("Caladea", Font.BOLD | Font.ITALIC, 14));
			lblAcercaDe.setOpaque(true);
			lblAcercaDe.setBounds(0, 0, 700, 15);
			Raton mostrarAcercaDe = new Raton(this);
			lblAcercaDe.addMouseListener(mostrarAcercaDe);
			add(lblAcercaDe);
		}
		/*
		 * La barra de arriba del eclipse que pone Help y luego About y te salta una ventana con info
		 * 
		 * */
	}

}
