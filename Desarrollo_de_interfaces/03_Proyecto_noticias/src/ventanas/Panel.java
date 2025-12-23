package ventanas;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Cursor;

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
		info.setHorizontalAlignment(SwingConstants.LEFT);
		info.setVerticalAlignment(SwingConstants.TOP);
		add(info);
	}

	/**
	 * @wbp.parser.constructor
	 */
	public Panel(boolean carga) {
		// Todos mis paneles parten de esta configuración
		super();
		setBackground(new Color(133, 239, 237));
		setLayout(null);
		setBounds(0, 0, 700, 700);
		
		//Esta es una barra de Menú con un “Acerca de” con los datos de la versión y el desarrollador.
		
		if (!carga) {
			// Solo aparece si no es la pantalla de carga
			JMenuBar menuBar = new JMenuBar();
			menuBar.setBackground(new Color(0, 128, 255));
			menuBar.setBounds(600, 0, 100, 22);
			add(menuBar);
			
			JMenu mnAyuda = new JMenu("Ayuda");
			mnAyuda.setHorizontalTextPosition(SwingConstants.RIGHT);
			mnAyuda.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			mnAyuda.setHorizontalAlignment(SwingConstants.RIGHT);
			menuBar.add(mnAyuda);
			
			JMenuItem mntmAcercaDe = new JMenuItem("Acerca de");
			mnAyuda.add(mntmAcercaDe);
			mntmAcercaDe.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			Panel.addPopup(mntmAcercaDe, getComponentPopupMenu());
		}
		
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
					
				}
			}
			public void mouseReleased(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Desarrollador: Arnau Blanch\nDesde: 1/12/2025\nVersion: v2.0\nHoras dedicadas: más de las que me gustaría\nLenguaje utilizado: Java\nVersion de Java: JavaSE-21", "Noticias diarias", 1);
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
