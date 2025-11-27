package Email;

import java.awt.EventQueue;
import java.awt.Toolkit;


public class MainEmail {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaMail ventanaPrincipal = new VentanaMail("Enviar email");
					ventanaPrincipal.setSize(800, 800);
					ventanaPrincipal.setResizable(false);
					ventanaPrincipal.setLocationRelativeTo(null);
					ventanaPrincipal.setIconImage(Toolkit.getDefaultToolkit().getImage(MainEmail.class.getResource("/O-Berry.jpg")));
					ventanaPrincipal.setVisible(true);
				} catch (Exception e){
					e.getStackTrace();
				}
			}
		});

	}

}
