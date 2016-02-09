package template.handlebars;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;

import template.InlineDataSource;

public class InlineTemplateTest
{
	@Test
	public void testExpandTemplate()
	{
		String text = InlineTemplate.getTemplateText();
		String data = InlineDataSource.getData();
		
		try
		{
			Handlebars     hb = new Handlebars();
			Template template = hb.compileInline(text);
			String   expanded = template.apply(data);

			assertTrue(expanded.contains("enjoy"));
			
			// System.out.println(expanded);
		}
		catch (IOException e)
		{
			fail ("Exception: " + e.getMessage());
		}
		
	}

}
