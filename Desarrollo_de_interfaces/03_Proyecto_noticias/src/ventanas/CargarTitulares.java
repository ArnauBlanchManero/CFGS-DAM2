package ventanas;

import txt.LeerTxt;

/*
 * Trabajo realizado por Arnau Blanch Manero
 */

public class CargarTitulares implements Runnable{


	@Override
	public void run() {
		// Leo las noticias a la vez que carga la pantalla de carga
		Ventana.titulares =  LeerTxt.leerTodasNoticias();
		// Cuando ya tengo las noticias, libero el semáforo para seguir con la creación de los paneles
		Ventana.semaforo.release();
	}
	
}
