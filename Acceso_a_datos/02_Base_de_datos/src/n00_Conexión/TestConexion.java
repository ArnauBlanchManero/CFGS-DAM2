package n00_Conexión;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestConexion {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/mydb";
		String usuario = "root";
		String password = "cfgs";
		try {
			// Carga el drive de la BBDD
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			
			// Conectar
			Connection conexion = DriverManager.getConnection(url, usuario, password);
			System.out.println("Conexion realizada");
			
			// Crear statement
			Statement sentencia = conexion.createStatement();
			String consulta = "select * from usuario";
			ResultSet resultado_consulta = sentencia.executeQuery(consulta);
			/*
			 * Esto tiene vuneralbilidades
			 * Se pueden hacer inyecciones SQL
			 * Para arreglarlo, usaremos otra clase
			 * Que es más robusta a la hora de pedir datos al usuario
			 * Ya que preparamos la consulta de forma segura
			*/
			String consulta2 = "Select * from usuario where id= ? ";
			PreparedStatement sentencia2 = conexion.prepareStatement(consulta2);
			int id_consulta2=1;
			int posicion_interrogante = 1;
			sentencia2.setInt(posicion_interrogante, id_consulta2);
			ResultSet resultado_consulta2 = sentencia2.executeQuery();
			//Mostrar resultados
			while (resultado_consulta2.next()) {
				int id = resultado_consulta2.getInt(1);
				String nombre = resultado_consulta2.getString(2);
				Date fecha = resultado_consulta2.getDate(3);
				String genero = resultado_consulta2.getString(4);
				System.out.println(id+"\n\tNombre: " +nombre+"\n\tFecha: "+fecha+"\n\tGénero: "+ genero);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
