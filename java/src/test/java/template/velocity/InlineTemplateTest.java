package template.velocity;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;
import org.junit.Before;
import org.junit.Test;

import template.InlineDataSource;

public class InlineTemplateTest
{
	private VelocityEngine             ve;
	private VelocityContext       context;
	private StringResourceRepository repo;
	
	@Before
	public void setUp() throws Exception
	{
		boolean ok = VelocityUtil.initializeVelocityForStringTemplates();
		
// Still haven't figured out how to handle the resource repository with a non-singleton
// version of Velocity. Perhaps I haven't found the right example yet, but it certainly
// seems like there's a lot of scaffolding to deal with just to make some simple template
// expansions. It's beginning to remind me of XML -- engineered for so many arcane edge
// cases that the cognitive burden eats into the value proposition.
//
//		ve = VelocityUtil.getVelocityEngine();
		
		if (! ok)
		{
			fail("Could not set up Velocity for string templates");
		}

      repo    = VelocityUtil.getRepo(null, null);
      if (repo == null)
      {
      	fail("Could not acquire Velocity resource repository");
      }
      
      context = new VelocityContext();
	}
	
	
	@Test
	public void testExpandTemplate()
	{
		String           text = InlineTemplate.getTemplateText();
		String           data = InlineDataSource.getData();

		context.put("verb", data);
		
//		StringResourceRepository repo = StringResourceLoader.getRepository();

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

		assertTrue(expanded.contains("enjoy"));

		// System.out.println(expanded);
	}
	
}
