package template.handlebars;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Jackson2Helper;
import com.github.jknack.handlebars.JsonNodeValueResolver;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.context.FieldValueResolver;
import com.github.jknack.handlebars.context.JavaBeanValueResolver;
import com.github.jknack.handlebars.context.MapValueResolver;
import com.github.jknack.handlebars.context.MethodValueResolver;

import template.JsonDataSource;
public class JsonDataSourceTest
{
	@Test
	public void testExpandTemplate()
	{
		String    json = JsonDataSource.getData();
		String    text = HtmlTableTemplate.getTemplateText();
		
		try
		{
			Handlebars     hb = new Handlebars();
			Template template = hb.compileInline(text);
			JsonNode    jnode = new ObjectMapper().readValue(json, JsonNode.class);
			
			hb.registerHelper("json",  Jackson2Helper.INSTANCE);
			
			Context context = Context.newBuilder(jnode)
					.resolver(JsonNodeValueResolver.INSTANCE, 
								 JavaBeanValueResolver.INSTANCE, 
								 FieldValueResolver.INSTANCE, 
								 MapValueResolver.INSTANCE, 
								 MethodValueResolver.INSTANCE)
					.build();
			
			String   expanded = template.apply(context);

			assertTrue(expanded.contains("ID"));
			assertTrue(expanded.contains("Name"));
			assertTrue(expanded.contains("CUSIP"));
			assertTrue(expanded.contains("ISIN"));
			
			System.out.println(expanded);
		}
		catch (IOException e)
		{
			fail ("Exception: " + e.getMessage());
		}
		
	}

}
