package n01_NBA;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
			//System.out.println("Conexion realizada\n\n");
			System.out.println("BIENBENIDO AL MENÚ DE LOS JUGADORES DE NBA");
			System.out.println("==========================================");
			// función que permita obtener los datos de los jugadores que empiecen por una letra
			System.out.println("\n1. Mostrar los jugadores que comparten la misma inicial");
			obtener_jugadores_letra(conexion);
			// el peso medio de los jugadores
			System.out.println("\n\n2. Mostrar la media del peso de todos los jugadores");
			obtener_peso_medio(conexion);
			// listando los equipos, selecciona el equipo y te muestra todos los jugadores
			System.out.println("\n\n3. Mostrar los jugadores que pertenecen a un equipo");
			obtener_jugadores_equipo(conexion);
			// insertar un jugador
			System.out.println("\n\n4. Insertar un jugador");
			insertar_jugador_nuevo(conexion);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void insertar_jugador_nuevo(Connection conexion) {
		
		ArrayList<String> datosJugador = new ArrayList<String>();
		System.out.println("Por favor, rellena los siguientes campos:");
		datosJugador = pedir_datos(conexion);
		int intentos = 0;
		while (datosJugador==null) {
			if(intentos<3)
				System.out.println("\nPor favor, rellena CORRECTAMENTE los siguientes campos con lo pedido:");
			else
				System.out.println("\nTen en cuenta estas indicaciones al rellenar los campos:\nNombre máximo 30 caracteres\nProcedencia máximo 20 caracteres\nAltura máximo 4 caracteres\nPeso tiene que ser un número\nPosicion máximo 5 caracteres\nNombre del equipo máximo 20 caracteres");
			datosJugador = pedir_datos(conexion);
		}
		try {
			String consulta = "INSERT INTO jugadores VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement sentencia;
			sentencia = conexion.prepareStatement(consulta);
			
			String mini_consulta = "SELECT MAX(codigo) FROM jugadores";
			PreparedStatement mini_sentencia;
			mini_sentencia = conexion.prepareStatement(mini_consulta);
			ResultSet resultado_mini_consulta = mini_sentencia.executeQuery();
			int codigo_jugador_nuevo = 0;
			if(resultado_mini_consulta.next())
				codigo_jugador_nuevo = resultado_mini_consulta.getInt(1);
			codigo_jugador_nuevo++;
			sentencia.setInt(1, codigo_jugador_nuevo);
			for (int i = 0; i < 6; i++) {
				if (i!=3) 
					sentencia.setString(i+2, datosJugador.get(i));
				else
					sentencia.setInt(i+2, Integer.parseInt(datosJugador.get(i)));
			}
			if(sentencia.executeUpdate()>0) {
				System.out.println("\n\nJugador añadido:");
				String comprobar_consulta = "SELECT * FROM jugadores WHERE codigo = ?";
				PreparedStatement comprobar_sentencia;
				comprobar_sentencia = conexion.prepareStatement(comprobar_consulta);
				comprobar_sentencia.setInt(1, codigo_jugador_nuevo);
				ResultSet resultado_comprobar_consulta = comprobar_sentencia.executeQuery();
				// Mostrar resultados
				int vueltas = 0;
				while (resultado_comprobar_consulta.next()) {
					int codigo = resultado_comprobar_consulta.getInt(1);
					String nombre = resultado_comprobar_consulta.getString(2);
					String procedencia = resultado_comprobar_consulta.getString(3);
					String altura = resultado_comprobar_consulta.getString(4);
					int peso = resultado_comprobar_consulta.getInt(5);
					String posicion = resultado_comprobar_consulta.getString(6);
					String nombreEquipo = resultado_comprobar_consulta.getString(7);
					System.out.println("\nID: "+codigo + "\n    Nombre: " + nombre + "\n    Procedencia: " + procedencia + "\n    Altura: "
							+ altura + "\n    Peso: " + peso + "\n    Posicion: " + posicion + "\n    Nombre del equipo: "
							+ nombreEquipo);

					vueltas++;
				}
				if (vueltas == 0)
					System.out.println("No se ha encontrado al nuevo jugador.");
			} else {
				System.out.println("Error al insertar jugador.");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	private static ArrayList<String> pedir_datos(Connection conexion) {
		ArrayList<String> nuevoJugador = new ArrayList<String>();
		String nombre;
		String procedencia;
		String altura;
		String peso;
		String posicion;
		String nombreEquipo;
		System.out.print("Nombre: ");
		nombre = read.nextLine();
		if (nombre.length()>30)
			return null;
		System.out.print("Procedencia: ");
		procedencia = read.nextLine();
		if (procedencia.length()>20)
			return null;
		System.out.print("Altura: ");
		altura = read.nextLine();
		if (altura.length()>4)
			return null;
		System.out.print("Peso: ");
		peso = read.nextLine();
		try {
		    int pesoNum = Integer.parseInt(peso);
		} catch (NumberFormatException e) {
		    return null;
		}
		System.out.print("Posicion: ");
		posicion = read.nextLine();
		if (posicion.length()>5)
			return null;
		nombreEquipo = lista_equipos(conexion);
		System.out.println("Nombre del equipo: "+nombreEquipo);
		if (nombreEquipo.length()>20)
			return null;
		
		nuevoJugador.add(nombre);
		nuevoJugador.add(procedencia);
		nuevoJugador.add(altura);
		nuevoJugador.add(peso);
		nuevoJugador.add(posicion);
		nuevoJugador.add(nombreEquipo);
		return nuevoJugador;
	}

	private static void obtener_jugadores_equipo(Connection conexion) {
		String equipo = lista_equipos(conexion);
		try {
			String consulta = "SELECT * FROM jugadores WHERE Nombre_equipo = ? ";
			PreparedStatement sentencia;
			sentencia = conexion.prepareStatement(consulta);
			String equipo_consulta = equipo;
			int posicion_interrogante = 1;
			sentencia.setString(posicion_interrogante, equipo_consulta);
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
				System.out.println("\nID: "+codigo + "\n    Nombre: " + nombre + "\n    Procedencia: " + procedencia + "\n    Altura: "
						+ altura + "\n    Peso: " + peso + "\n    Posicion: " + posicion + "\n    Nombre del equipo: "
						+ nombreEquipo);

				vueltas++;
			}
			if (vueltas == 0)
				System.out.println("No hay jugadores con ese equipo.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	private static String lista_equipos(Connection conexion) {
		try {
			Statement sentencia = conexion.createStatement();
			String consulta = "SELECT nombre FROM equipos;";
			ResultSet resultado_consulta = sentencia.executeQuery(consulta);
			// Mostrar resultados
			int vueltas = 0;
			ArrayList<String> nombresEquipos = new ArrayList<String>();
			System.out.println("\nListado de los equipos:");
			while (resultado_consulta.next()) {
				String nombreEquipo = resultado_consulta.getString(1);
				nombresEquipos.add(nombreEquipo);
				vueltas++;
				System.out.println(vueltas+": "+nombreEquipo);
			}
			if (vueltas == 0) {
				System.out.println("No hay equipos disponibles.");
				return null;
			}
			int numRespuesta;
			do{
				System.out.print("\nEscribe el número correspondiente al equipo que quieras: ");
				numRespuesta = read.nextInt();
				read.nextLine();
				numRespuesta--;
			}while(numRespuesta>vueltas);
			return nombresEquipos.get(numRespuesta);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static void obtener_peso_medio(Connection conexion) {
		try {
			Statement sentencia = conexion.createStatement();
			String consulta = "SELECT avg(peso) AS pesoMedia FROM jugadores";
			ResultSet resultado_consulta = sentencia.executeQuery(consulta);
			// Mostrar resultados
			resultado_consulta.next();
			int pesoAvg = resultado_consulta.getInt(1);
			System.out.println("El peso medio de los jugadores es "+pesoAvg);
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
				System.out.println("\nID: "+codigo + "\n    Nombre: " + nombre + "\n    Procedencia: " + procedencia + "\n    Altura: "
						+ altura + "\n    Peso: " + peso + "\n    Posicion: " + posicion + "\n    Nombre del equipo: "
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
