package ConexionesURL;

import java.awt.HeadlessException;
import java.awt.Toolkit;

import javax.swing.JFrame;

import Ventanas.Ejemplo4y5;

public class VentanaTraductor extends JFrame{

	public VentanaTraductor(String title) throws HeadlessException {
		super();
		setTitle(title);
		setSize(500, 500);
		setVisible(true);
		setResizable(isVisible());
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Ejemplo4y5.class.getResource("/O-Berry.jpg")));
		PanelTraductor panelPrincipal = new PanelTraductor();
		add(panelPrincipal);
	}
}
