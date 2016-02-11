package template.velocity;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import template.JsonDataSource;

/**
 * Note, in this approach, there's nothing that Velocity does that natively supports JSON, 
 * so Jackson is used to create a JsonNode from the root of the document.
 * 
 * Velocity is a bit picky about what can be used to handle array-type values, so the
 * properties array is turned into Object[] which is placed into the context. Velocity
 * will wrap an array with an Iteratable class to allow #foreach to loop through values.
 * 
 * In the VTL, the JsonNode.get("property_name") method is called to get property values.
 * 
 * @author reid.dev
 *
 */
public class JsonDataSourceTest
{
	private VelocityContext context;
	private StringResourceRepository repo;
	
	@Before
	public void setUp() throws Exception
	{
		boolean ok = VelocityUtil.initializeVelocityForStringTemplates();
		
		if (! ok)
		{
			fail("Could not set up Velocity for string templates");
		}

      repo = VelocityUtil.getRepo(null, null);
      if (repo == null)
      {
      	fail("Could not acquire repository");
      }
 
      context = new VelocityContext();
	}

	@Test
	public void testExpandTemplate()
	{
		String     json = JsonDataSource.getData();
		JsonNode[] data = JsonDataSource.convertJsonData(json);
		String     text = HtmlTableTemplate.getTemplateText();

		context.put("properties", data);
		
	   String myTemplateName = "/some/imaginary/path/hello.vm";
	   repo.putStringResource(myTemplateName, text);
	 
		Template template = null;

		try
		{
		   template = Velocity.getTemplate(myTemplateName);
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

		template.merge( context, sw );		
		
		String expanded = sw.toString();

//		System.out.println(expanded);

		assertTrue(expanded.contains("ID"));
		assertTrue(expanded.contains("Name"));
		assertTrue(expanded.contains("CUSIP"));
		assertTrue(expanded.contains("ISIN"));
	}


}
