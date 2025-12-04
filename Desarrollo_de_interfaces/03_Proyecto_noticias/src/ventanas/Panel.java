package ventanas;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Panel extends JPanel{
	
	public Panel(String mensaje) {
		super();
		setBackground(new Color(255, 118, 114));
		setLayout(null);
		JLabel info = new JLabel();
		add(info);
		info.setBounds(10, 10, 428, 278);
		info.setText(mensaje);
		info.setHorizontalAlignment(SwingConstants.CENTER);
		info.setVerticalAlignment(SwingConstants.TOP);
	}

	public Panel() {
		super();
		setBackground(new Color(133, 239, 237));
		setLayout(null);
		setBounds(0, 0, 684, 661);
		//TODO poner una barra de Menú con un “Acerca de” con los datos de la versión y	el desarrollador. (preguntar a Sergio que quiere)
		/*
		 * La barra de arriba del eclipse que pone Help y luego About y te salta una ventana con info
		 * 
		 * */
	}

}
