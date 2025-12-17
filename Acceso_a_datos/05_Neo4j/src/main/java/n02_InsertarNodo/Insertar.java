package n02_InsertarNodo;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.Values;

public class Insertar {

	public static void main(String[] args) {
		System.out.println("Proyecto con Neo4j");
		Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "cfgscfgs"));

		try (Session session = driver.session(SessionConfig.forDatabase("nba"))) {
			// Se usan variables con el caracter $ para evitar inyecciones
			String insertar = "MERGE (ad:PLAYER {name: $name}) " + "ON CREATE SET " + "ad.age = $age, "
					+ "ad.number = $number, " + "ad.height = $height, " + "ad.weight = $weight";

			session.run(insertar, Values.parameters("name", "Prueba", "age", 1, "number", 1, "height", 1.01, "weight", 100));

			System.out.println("Jugador insertado correctamente.");
		} finally {
			driver.close();
		}
	}

}
