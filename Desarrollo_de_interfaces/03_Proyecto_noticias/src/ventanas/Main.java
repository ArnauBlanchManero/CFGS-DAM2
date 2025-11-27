package ventanas;

import java.awt.EventQueue;
import java.awt.Toolkit;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana ventanaPrincipal = new Ventana("Noticias diarias");
					// TODO set bounds en vez de set size y location relative to
					ventanaPrincipal.setSize(800, 800);
					ventanaPrincipal.setResizable(false);
					ventanaPrincipal.setLocationRelativeTo(null);
					ventanaPrincipal.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("newsLogo.png")));
					ventanaPrincipal.setVisible(true);
				} catch (Exception e){
					e.getStackTrace();
				}
			}
		});
	}

}
