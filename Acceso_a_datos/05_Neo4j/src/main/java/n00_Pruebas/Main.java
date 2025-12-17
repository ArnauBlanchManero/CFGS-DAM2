package n00_Pruebas;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;

public class Main {

	public static void main(String[] args) {
		System.out.println("Proyecto con Neo4j");
		Driver driver = GraphDatabase.driver(
                "bolt://localhost:7687",
                AuthTokens.basic("neo4j", "cfgscfgs")
        );

        try (Session session = driver.session()) {
            int result = session.run("RETURN 1 AS test")
                                .single()
                                .get("test")
                                .asInt();
            System.out.println("Neo4j OK → " + result);
        } finally {
            driver.close();
        }
	}

}
