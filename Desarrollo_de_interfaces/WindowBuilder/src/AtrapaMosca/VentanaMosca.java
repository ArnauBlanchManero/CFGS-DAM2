package AtrapaMosca;

import java.awt.HeadlessException;

import javax.swing.JFrame;


public class VentanaMosca extends JFrame{

	public VentanaMosca(String title) throws HeadlessException{
		super();
		setTitle(title);
		setSize(800, 800);
		setVisible(true);
		setResizable(isVisible());
		setLocationRelativeTo(null);
		PanelMosca panelPrincipal = new PanelMosca();
		getContentPane().add(panelPrincipal);
	}

}
