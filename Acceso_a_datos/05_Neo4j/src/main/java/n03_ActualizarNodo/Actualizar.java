package n03_ActualizarNodo;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.Values;

public class Actualizar {

	public static void main(String[] args) {
		System.out.println("Proyecto con Neo4j");
		Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "cfgscfgs"));

		try (Session session = driver.session(SessionConfig.forDatabase("nba"))) {
			// Se usan variables con el caracter $ para evitar inyecciones
			String insertar = "MATCH (ad:PLAYER {name: $name}) " + "SET " + "ad.age = $age ";

			session.run(insertar, Values.parameters("name", "Carmelo Rios", "age", 33));

			System.out.println("Jugador actualizado correctamente.");
		} finally {
			driver.close();
		}
	}

}
