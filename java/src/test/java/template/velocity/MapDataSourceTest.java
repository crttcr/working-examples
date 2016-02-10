package template.velocity;

import static org.junit.Assert.assertTrue;

import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;

import template.MapDataSource;

public class MapDataSourceTest
{
	private VelocityContext context;
	private VelocityEngine       ve;
	
	@Before
	public void setUp() throws Exception
	{
      ve = new VelocityEngine();
      ve.init();
      
      context = new VelocityContext();
	}
	
	@Test
	public void testExpandTemplate()
	{
		Map<String, Object> data = MapDataSource.getData();
		
		context.put("map", data);
	   
	   Template template = null;

	   try
	   {
	   	// See this reference:
	   	// http://velocity.apache.org/engine/releases/velocity-1.7/developer-guide.html#resourceloaders
	   	// to learn about accessing resources in Velocity.
	   	//
	   	// My attempt to normalize and absolutize the path caused Velocity not to be able
	   	// to find the .vtl file.
	   	//
			Path path = Paths.get("./src/main/resources/templates/map_template.vtl");
	   	
//	      template = ve.getTemplate(path.normalize().toAbsolutePath().toString());
	      template = ve.getTemplate(path.toString());
	   }
		catch( ResourceNotFoundException rnfe )
		{
		   // couldn't find the template
			//
			rnfe.printStackTrace();
			System.exit(-1);
		}
		catch( ParseErrorException pee )
		{
			// syntax error: problem parsing the template
			//
			pee.printStackTrace();
			System.exit(-1);
		}
		catch( MethodInvocationException mie )
		{
			// something invoked in the template
			// threw an exception
			//
			mie.printStackTrace();
			System.exit(-1);
		}
		catch( Exception e )
		{
			//
			e.printStackTrace();
			System.exit(-1);
			
		}
	   
	   StringWriter sw = new StringWriter();
		template.merge(context, sw);
		
		String expanded = sw.toString();

		System.out.println(expanded);

		assertTrue(expanded.contains("GT4"));
		assertTrue(expanded.contains("Damien"));
		assertTrue(expanded.contains("Boo hoo"));
	}

}
