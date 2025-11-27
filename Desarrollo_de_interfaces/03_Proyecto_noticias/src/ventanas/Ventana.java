package ventanas;

import java.awt.HeadlessException;

import javax.swing.JFrame;


public class Ventana extends JFrame{

	public Ventana(String title) throws HeadlessException {
		super();
		
		setTitle(title);
		Panel panelInicioSesion = new Panel();
		getContentPane().add(panelInicioSesion);
		panelInicioSesion.setVisible(true);
	}

}
