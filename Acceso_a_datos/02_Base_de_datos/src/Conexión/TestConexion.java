package Conexión;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
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
			
			//Mostrar resultados
			while (resultado_consulta.next()) {
				int id = resultado_consulta.getInt(1);
				String nombre = resultado_consulta.getString(2);
				Date fecha = resultado_consulta.getDate(3);
				String genero = resultado_consulta.getString(4);
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
