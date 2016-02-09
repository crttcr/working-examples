package template.handlebars;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;

import template.MapDataSource;

public class MapDataSourceTest
{
	@Test
	public void testExpandTemplate()
	{
		String              text = Template4MapData.getTemplateText();
		Map<String, Object> data = MapDataSource.getData();
		
		try
		{
			Handlebars     hb = new Handlebars();
			Template template = hb.compileInline(text);
			String   expanded = template.apply(data);

			assertTrue(expanded.contains("GT4"));
			assertTrue(expanded.contains("Damien"));
			assertTrue(expanded.contains("Boo hoo"));
			
			// System.out.println(expanded);
		}
		catch (IOException e)
		{
			fail ("Exception: " + e.getMessage());
		}
		
	}

}
