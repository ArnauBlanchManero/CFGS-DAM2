package ventanas;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Raton implements MouseListener{

	Panel panel;
	JPanel acercaDe = new JPanel();
	
	public Raton(Panel panel) {
		super();
		this.panel = panel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, "Desarrollador: Arnau Blanch\nDesde: 1/12/2025\nVersion: v1.0\nHoras dedicadas: más de las que me gustaría\nLenguaje utilizado: Java\nVersion de Java: JavaSE-21", "Noticias diarias", 1);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
