package n01_CrearDatos;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

public class Crear {

	public static void main(String[] args) {
		System.out.println("Proyecto con Neo4j");
		Driver driver = GraphDatabase.driver(
                "bolt://localhost:7687",
                AuthTokens.basic("neo4j", "cfgscfgs")
        );

        try (Session session = driver.session()) {
            Result result = session.run("MATCH (n) RETURN n");
            if(result.hasNext()) {
            	System.out.println("Neo4j OK");
            } else {
            	System.out.println("No hay datos en la base de datos");
            	session.run("CREATE (russell:PLAYER{name:\"Russell Westbrook\", age: 33, number: 0, height: 1.91, weight: 91})");
            	System.out.println("Se ha creado un nodo");
            }
        } finally {
            driver.close();
        }
	}

}
