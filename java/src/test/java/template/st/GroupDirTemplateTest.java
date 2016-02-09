package template.st;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

public class GroupDirTemplateTest
{
	@Before
	public void setUp() throws Exception
	{
	}
	
	@Test
	public void testGroupDirectory()
	{
		Path path = Paths.get("src/main/resources/templates");
		STGroup group = new STGroupDir(path.normalize().toAbsolutePath().toString(), '{', '}');
		ST st = group.getInstanceOf("decl");
		
		st.add("type", "int");
		st.add("name", "x");
		st.add("value", 0);

		String expanded = st.render();

		System.out.println(expanded);

		assertTrue(expanded.contains("int"));
		assertTrue(expanded.contains("x"));
		assertTrue(expanded.contains("="));
		assertTrue(expanded.contains("0"));
	}

}
