package JUGUETERIA;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GestorJugueteria {

	public static void main(String[] args) {
		System.out.println("  ☺  ʕ•ᴥ•ʔ  [°_°]    ☺  ʕ•ᴥ•ʔ  [°_°]  ");
		System.out.println(" /|\\ ( • ) /|___|\\  /|\\ ( • ) /|___|\\ ");
		System.out.println(" / \\ () ()   | |    / \\ () ()   | |   ");
		System.out.println("BIENVENIDO AL GESTOR DE LA JUGUETERIA\n");
		// TODO conectarse a la bbdd
		String[] datosConectar = {"jdbc:mysql://localhost:3306/Jugueteria", "root", "cfgs"};
		BaseDatos jugueteriaBBDD = new BaseDatos("Jugueteria", datosConectar);
		ResultSet resultado_consulta = jugueteriaBBDD.consulta("SELECT * FROM EMPLEADO");
		if(resultado_consulta!=null) {
			try {
				while (resultado_consulta.next()) {
					int id = resultado_consulta.getInt(1);
					System.out.println(id);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//Connection conexion = jugueteriaBBDD.conectar();
		// TODO crear variables esenciales
		// TODO delegar el trabajo a los menus
		System.out.println("Aplicación cerrada.");
	}

}
