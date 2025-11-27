package n02_AtrapaMosca;

import java.awt.EventQueue;
import java.awt.Toolkit;

public class MainAtrapaMosca {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaMosca ventanaPrincipal = new VentanaMosca("Atrapa la mosca");
					ventanaPrincipal.setSize(800, 800);
					ventanaPrincipal.setResizable(false);
					ventanaPrincipal.setLocationRelativeTo(null);
					ventanaPrincipal.setIconImage(Toolkit.getDefaultToolkit().getImage(MainAtrapaMosca.class.getResource("/moscaLogo.png")));
				} catch (Exception e){
					e.getStackTrace();
				}
			}
		});
	}

}
