package spring.data.neo4j;

import static org.neo4j.driver.v1.Values.parameters;

import org.neo4j.driver.v1.AuthToken;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;

public class Neo4jServerExampleProgram
{
	public static void main(String[] args) {

		AuthToken token = AuthTokens.basic( "neo4j", "secret" );
		Driver driver = GraphDatabase.driver( "bolt://localhost:7687", token);

		try ( Session session = driver.session() )
		{

			try ( Transaction tx = session.beginTransaction() )
			{
				tx.run( "CREATE (a:Person {name: {name}, title: {title}})",
					parameters( "name", "James", "title", "King" ) );
				tx.success();
			}

			try ( Transaction tx = session.beginTransaction() )
			{
				StatementResult result = tx.run( "MATCH (a:Person) WHERE a.name = {name} " +
					"RETURN a.name AS name, a.title AS title",
					parameters( "name", "Arthur" ) );
				while ( result.hasNext() )
				{
					Record record = result.next();
					System.out.println( String.format( "%s %s", record.get( "title" ).asString(), record.get( "name" ).asString() ) );
				}
			}

		}

		driver.close();
	}

}
