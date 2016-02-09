package template.velocity;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import template.JsonDataSource;
public class JsonDataSourceTest
{
	private VelocityContext context;
	
	@Before
	public void setUp() throws Exception
	{
		boolean ok = VelocityUtil.InitializeVelocityForStringTemplates();
		
		if (! ok)
		{
			fail("Could not set up Velocity for string templates");
		}

      VelocityUtil.getRepo(null, null);
 
      context = new VelocityContext();
	}

	@Test
	public void testExpandTemplate()
	{
		String    json = JsonDataSource.getData();
		Object[]  data = convertJsonData(json);
		String    text = HtmlTableTemplate.getTemplateText();

		context.put("properties", data);
		
		StringResourceRepository repo = StringResourceLoader.getRepository();

	   String myTemplateName = "/some/imaginary/path/hello.vm";
	   String myTemplate     = text; // "Hi, ${username}... this is some template!";
	   repo.putStringResource(myTemplateName, myTemplate);
	 
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

		
	}

	private Object[] convertJsonData(String json)
	{
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode root = null;
		try
		{
			root = mapper.readTree(json);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			fail("Could not process json");
		}
		
		JsonNode props = root.get("properties");
		
		if (! props.isArray())
			fail("Expected an array from JSON data, received: " + props.toString());
		
		
		List<Object> list = new ArrayList<>();
		
		for (final JsonNode item : props)
		{
			list.add(item);
//			System.out.println(item.toString());
		}
		
		return list.toArray();
	}

}
