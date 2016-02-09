package template.velocity;

import static org.junit.Assert.assertTrue;

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
	private VelocityContext context;
	
	@Before
	public void setUp() throws Exception
	{
//      Velocity.reset();
      Velocity.setProperty(Velocity.RESOURCE_LOADER, "string");
      Velocity.addProperty("string.resource.loader.class", StringResourceLoader.class.getName());
      Velocity.addProperty("string.resource.loader.modificationCheckInterval", "1");
//      Velocity.setProperty(Velocity.RUNTIME_LOG_INSTANCE, new TestLogger());
      Velocity.init();

//		StringResourceRepository repo = StringResourceLoader.getRepository();
      StringResourceRepository repo = getRepo(null, null);
      repo.putStringResource("foo", "This is $foo");
      repo.putStringResource("bar", "This is $bar");

      context = new VelocityContext();
      context.put("foo", "wonderful!");
      context.put("bar", "horrible!");
      context.put("woogie", "a woogie");	
	}
	
	
	@Test
	public void testExpandTemplate()
	{
		String           text = InlineTemplate.getTemplateText();
		String           data = InlineDataSource.getData();

		Velocity.init();
		VelocityContext context = new VelocityContext();

		context.put("verb", data);
		
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

		assertTrue(expanded.contains("enjoy"));

		// System.out.println(expanded);
	}
	

	protected StringResourceRepository getRepo(String name, VelocityEngine engine)
   {
       if (engine == null)
       {
           if (name == null)
           {
               return StringResourceLoader.getRepository();
           }
           else
           {
               return StringResourceLoader.getRepository(name);
           }
       }
       else
       {
           if (name == null)
           {
               return (StringResourceRepository)engine.getApplicationAttribute(StringResourceLoader.REPOSITORY_NAME_DEFAULT);
           }
           else
           {
               return (StringResourceRepository)engine.getApplicationAttribute(name);
           }
       }
   }

}
