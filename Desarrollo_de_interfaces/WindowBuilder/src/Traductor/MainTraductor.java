package Traductor;

import java.awt.EventQueue;
import java.awt.Toolkit;


public class MainTraductor {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaTraductor ventanaPrincipal = new VentanaTraductor("TRADUCTOR ES-EN");
					ventanaPrincipal.setSize(800, 400);
					ventanaPrincipal.setResizable(false);
					ventanaPrincipal.setLocationRelativeTo(null);
					ventanaPrincipal.setIconImage(Toolkit.getDefaultToolkit().getImage(MainTraductor.class.getResource("/traductorLogo.png")));
				} catch (Exception e){
					e.getStackTrace();
				}
			}
		});
	}

}
