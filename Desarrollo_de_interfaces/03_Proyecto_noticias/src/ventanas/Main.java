package ventanas;

import java.awt.EventQueue;

import javax.swing.JOptionPane;


/*
 * Trabajo realizado por Arnau Blanch Manero
 */

public class Main {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Al iniciar la aplicación se muestra una pantalla de carga.
					Ventana ventanaCarga = new Ventana(640, 323);
					ventanaCarga.setVisible(true);
				} catch (Exception e){
					// Si ocurre cualquier problema se mostrará esta ventana con el mensaje de la excepción (no debería mostrarse nunca)
					JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", 2);
					System.exit(3);
				}
			}
		});
	}

}
