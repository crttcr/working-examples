package template.st;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

import com.fasterxml.jackson.databind.JsonNode;

import template.JsonDataSource;

public class JsonDataSourceTest
{
	@Test
	public void testExpandJsonDataIntoTable()
	{
		String json = JsonDataSource.getData();
		Property[] props = DataToPropertyArray(json);

		Path     path = Paths.get("src/main/resources/templates");
		String  spath = path.normalize().toAbsolutePath().toString();
		STGroup group = new STGroupDir(spath, '$', '$');
		ST         st = group.getInstanceOf("html");
		
		if (st == null)
			fail("Unable to process template");

		st.add("properties", props);
		String expanded = st.render();

System.out.println(expanded);

		assertTrue(expanded.contains("Name"));
		assertTrue(expanded.contains("Type"));
		assertTrue(expanded.contains("Key"));
	}

	private Property[] DataToPropertyArray(String json)
	{
		JsonNode[] data = JsonDataSource.convertJsonData(json);
		
		Property[] result = new Property[data.length];
		
		for (int i = 0; i < data.length; i++)
		{
			JsonNode node = data[i];
			
			String name = node.get("name").textValue();
			String type = node.get("type").textValue();
			boolean key = node.get("key") == null ? false : node.get("key").booleanValue();
			boolean req = node.get("required").booleanValue();
			boolean uni = node.get("unique").booleanValue();
			
			result[i] = new Property(name, type, key, req, uni);
		}
		
		
		return result;
	}

	public static class Property
	{
		public Property(String name, String type, boolean key, boolean required, boolean unique)
		{
			this.name = name;
			this.type = type;
			this. key =  key;
			this.required = required;
			this.unique   = unique;
		}
			
		public String name;
		public String type;
		public boolean key;
		public boolean required;
		public boolean unique;
	}
}
