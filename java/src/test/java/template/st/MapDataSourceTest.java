package template.st;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;
import org.stringtemplate.v4.ST;

import template.MapDataSource;

public class MapDataSourceTest
{
	@Test
	public void testExpandMapData()
	{
		Map<String, Object> data = MapDataSource.getData();
		String template = Template4MapData.getTemplateText();

		ST st = new ST(template, '{', '}');

		st.add("data", data);
		String expanded = st.render();

//		System.out.println(expanded);

		assertTrue(expanded.contains("GT4"));
		assertTrue(expanded.contains("Damien"));
		assertTrue(expanded.contains("Boo hoo"));
	}

}
