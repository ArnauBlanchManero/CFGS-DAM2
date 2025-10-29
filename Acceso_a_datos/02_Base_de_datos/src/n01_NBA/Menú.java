package n01_NBA;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Menú {
	static Scanner read = new Scanner(System.in);

	public static void main(String[] args) {
		// 1 Conectarse a la base de datos
		String url = "jdbc:mysql://localhost:3306/nba";
		String usuario = "root";
		String password = "cfgs";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conexion = DriverManager.getConnection(url, usuario, password);
			System.out.println("Conexion realizada\n\n");
			System.out.println("BIENBENIDO AL MENÚ DE LOS JUGADORES DE NBA");
			System.out.println("==========================================");
			System.out.println("\n1. Mostrar los jugadores que comparten la misma inicial");
			// TODO función que permita obtener los datos de los jugadores que empiecen por una letra
			obtener_jugadores_letra(conexion);
			// TODO el peso medio de los jugadores
			// select avg(peso) as pesoMedia from jugadores
			// TODO listando los equipos, selecciona el equipo y te muestra todos los jugadores
			// TODO insertar un jugador
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void obtener_jugadores_letra(Connection conexion) {
		String respuesta;
		System.out.print("\nEscribe la letra inicial de los jugadores que quieres mostrar: ");
		respuesta = read.next();
		read.nextLine();
		Character letra = respuesta.toUpperCase().charAt(0);
		try {
			String consulta = "SELECT * FROM jugadores WHERE nombre LIKE ? ";
			PreparedStatement sentencia;
			sentencia = conexion.prepareStatement(consulta);
			String letra_consulta = letra.toString() + '%';
			int posicion_interrogante = 1;
			sentencia.setString(posicion_interrogante, letra_consulta);
			ResultSet resultado_consulta = sentencia.executeQuery();
			// Mostrar resultados
			int vueltas = 0;
			while (resultado_consulta.next()) {
				int codigo = resultado_consulta.getInt(1);
				String nombre = resultado_consulta.getString(2);
				String procedencia = resultado_consulta.getString(3);
				String altura = resultado_consulta.getString(4);
				int peso = resultado_consulta.getInt(5);
				String posicion = resultado_consulta.getString(6);
				String nombreEquipo = resultado_consulta.getString(7);
				System.out.println(codigo + "\n\tNombre: " + nombre + "\n\tProcedencia: " + procedencia + "\n\tAltura: "
						+ altura + "\n\tPeso: " + peso + "\n\tPosicion: " + posicion + "\n\tNombre del equipo: "
						+ nombreEquipo);

				vueltas++;
			}
			if (vueltas == 0)
				System.out.println("No hay jugadores con esa inicial.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
