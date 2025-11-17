package n01_NBA;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AmpliaciónMenú {
	static Scanner read = new Scanner(System.in);
	public static void main(String[] args) {
		String url = "jdbc:mysql://127.0.0.1:3306/nba";
		String usuario = "root";
		String passwd = "cfgs";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conexion = DriverManager.getConnection(url, usuario, passwd);
			System.out.println("BIENBENIDO AL MENÚ AMPLIADO DE LOS JUGADORES DE NBA");
			System.out.println("===================================================");
			System.out.println("\n1. Borrar un jugador");
			borrar_jugador(conexion);
			// Fichar un jugador en un equipo, los equipos aparecen en una lista donde el usuario inserta el número de equipo controlado y de ahí se inserta en la base de datos
			System.out.println("\n\n2. Fichar un jugador en un equipo");
			// Insertar un partido utilizando parte de la funcionalidad anterior para no tener que insertar los nombres de los jugadores
			System.out.println("\n\n3. Insertar un partido");
			// Dado un equipo por número (como el procedimiento anterior) conocer las estadísticas de todos sus jugadores
			System.out.println("\n\n4. Conocer las estadísticas de todos los jugadores por equipo");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private static void borrar_jugador(Connection conexion) {
		try {
			
			String consultaMostrarJugadores = "SELECT * FROM jugadores";
			PreparedStatement sentenciaMostrarJugadores;
			sentenciaMostrarJugadores = conexion.prepareStatement(consultaMostrarJugadores);
			ResultSet resultadoMostrarJugadores = sentenciaMostrarJugadores.executeQuery();
			int vueltas = 0;
			while (resultadoMostrarJugadores.next()) {
				int codigo = resultadoMostrarJugadores.getInt(1);
				String nombre = resultadoMostrarJugadores.getString(2);
				String procedencia = resultadoMostrarJugadores.getString(3);
				String altura = resultadoMostrarJugadores.getString(4);
				int peso = resultadoMostrarJugadores.getInt(5);
				String posicion = resultadoMostrarJugadores.getString(6);
				String nombreEquipo = resultadoMostrarJugadores.getString(7);
				System.out.println("\nID: "+codigo + "\n    Nombre: " + nombre + "\n    Procedencia: " + procedencia + "\n    Altura: "
						+ altura + "\n    Peso: " + peso + "\n    Posicion: " + posicion + "\n    Nombre del equipo: "
						+ nombreEquipo);

				vueltas++;
			}
			if (vueltas == 0)
				System.out.println("No hay jugadores.");
			System.out.print("\nEscribe el ID del jugador que quieres eliminar: ");
			int codigoEliminar = read.nextInt();
			try {
				String consulta = "DELETE FROM jugadores WHERE codigo = ?";
				PreparedStatement sentencia;
				sentencia = conexion.prepareStatement(consulta);
				sentencia.setInt(1, codigoEliminar);
				
				if(sentencia.executeUpdate()>0) {
					System.out.println("\n\nJugador eliminado");
				} else {
					System.out.println("Error al eliminar el jugador.");
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
