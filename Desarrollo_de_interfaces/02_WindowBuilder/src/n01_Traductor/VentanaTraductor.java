package n01_Traductor;

import java.awt.HeadlessException;

import javax.swing.JFrame;


public class VentanaTraductor extends JFrame{

	public VentanaTraductor(String title) throws HeadlessException {
		super();
		setTitle(title);
		setSize(500, 500);
		setVisible(true);
		setResizable(isVisible());
		setLocationRelativeTo(null);
		PanelTraductor panelPrincipal = new PanelTraductor();
		add(panelPrincipal);
	}
}
