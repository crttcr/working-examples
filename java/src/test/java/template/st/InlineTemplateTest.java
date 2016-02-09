package template.st;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.stringtemplate.v4.ST;

import template.InlineDataSource;

public class InlineTemplateTest
{
	@Before
	public void setUp() throws Exception
	{
	}
	
	@Test
	public void testHelloWorld()
	{
		ST st = new ST("Hello, {name}!", '{', '}');
		st.add("name", "Moto");
		String expanded = st.render();

		// System.out.println(expanded);
		//

		assertTrue(expanded.contains("Moto"));
	}

	@Test
	public void testInline()
	{
		String template = InlineTemplate.getTemplateText();
		ST st = new ST(template, '{', '}');

		String data = InlineDataSource.getData();
		st.add("verb", data);
		String expanded = st.render();

		// System.out.println(expanded);
		//

		assertTrue(expanded.contains("enjoy"));
	}


}
