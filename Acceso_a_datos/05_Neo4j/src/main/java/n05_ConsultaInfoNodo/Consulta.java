package n05_ConsultaInfoNodo;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.Values;

public class Consulta {

	public static void main(String[] args) {
		System.out.println("Proyecto con Neo4j");
		Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "cfgscfgs"));

		try (Session session = driver.session(SessionConfig.forDatabase("nba"))) {
			// Se usan variables con el caracter $ para evitar inyecciones
			String insertar = "MATCH (ad:PLAYER {name: $name}) " + "RETURN " + "ad.height " + "AS h";

			Result result = session.run(insertar, Values.parameters("name", "Anthony Davis"));

			System.out.println("Jugador consultado correctamente.");
			System.out.println("Altura del jugador: "+result.next().get("h").asDouble());
		} finally {
			driver.close();
		}
	}

}
