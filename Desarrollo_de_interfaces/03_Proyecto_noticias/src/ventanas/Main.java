package ventanas;

import java.awt.EventQueue;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana ventanaPrincipal = new Ventana("Noticias diarias");
					ventanaPrincipal.setVisible(true);
				} catch (Exception e){
//					Ventana ventanaError = new Ventana(e.getMessage(), 450, 200);
//					ventanaError.setVisible(true);
					//e.getStackTrace();
				}
			}
		});
	}

}
