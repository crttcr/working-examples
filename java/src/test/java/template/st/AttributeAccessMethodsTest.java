package template.st;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.stringtemplate.v4.ST;

/**
 * This test serves to demonstrate the various methods of accessing attributes from within 
 * a template.
 * 
 * @author reid.dev
 *
 */
public class AttributeAccessMethodsTest
{
	public static class PropertyHolder
	{
		public String a;
		protected int  b;
		private   char c;
		
		public PropertyHolder(String s, int i, char c)
		{
			this.a = s;
			this.b = i;
			this.c = c;
		}
		
		public String toString()
		{
			return a + ":" + Integer.toString(b) + ":" + c;
		}
	}

	public static class PropertyBean
	{
		private final String a;
		private final int  b;
		private final char c;
		
		public PropertyBean(String s, int i, char c)
		{
			this.a = s;
			this.b = i;
			this.c = c;
		}
		
		public String getA() { return a; }
		public int    getB() { return b; }
		public char   getC() { return c; }
	
		protected String getD() { return a; }
		protected int    getE() { return b; }
		protected char   getF() { return c; }
		
		public String toString()
		{
			return a + ":" + Integer.toString(b) + ":" + c;
		}
	}

	public static class MethodExposer
	{
		private final String a;
		private final int  b;
		private final char c;
		
		public MethodExposer(String s, int i, char c)
		{
			this.a = s;
			this.b = i;
			this.c = c;
		}
		
		public String a() { return a; }
		public int    b() { return b; }
		public char   c() { return c; }
	
		public String toString()
		{
			return a + ":" + Integer.toString(b) + ":" + c;
		}
	}

	@Test
	public void testMapAttribute()
	{
		List<String> list = new ArrayList<>();
		list.add("A");
		list.add("B");
		list.add("C");
		
		Map<String, Object> map = new HashMap<>();
		map.put("key_1", "value.one");                // N.B. You cannot have a key such as "key.one" because the tempate reference {x.y.key.one} expects to find "key"
		map.put("key_2", "value.two");
		map.put("key_3", map);                        // Let's see how ST deals with recursive structures.

		Map<String, Object> data = new HashMap<>();
		
		data.put("float"  , new Float(98.6F));
		data.put("integer", new Integer(42));
		data.put("string" , "Horse hockey");
		data.put("date"   , LocalDate.now());
		data.put("time"   , LocalTime.now());
		data.put("list"   , list);
		data.put("map"    , map);

		String template = ""                       +
				"Float     : {data.float}"           + "\n" +
				"Integer   : {data.integer}"         + "\n" +
				"String    : {data.string}"          + "\n" +
				"Date      : {data.date}"            + "\n" +
				"Time      : {data.time}"            + "\n" +
				"List      : {data.list}"            + "\n" +
				"Map       : {data.map}"             + "\n" +   // This will treat the keys as a collection.
				"Map k1    : {data.map.key_1}"       + "\n" +
				"Map k2    : {data.map.key_2}"       + "\n" +
				"Map k3.k1 : {data.map.key_3.key_1}" + "\n" +
				"";

		ST st = new ST(template, '{', '}');

		st.add("data", data);
		String expanded = st.render();

		// System.out.println(expanded);

		assertTrue(expanded.contains("98.6"));
		assertTrue(expanded.contains("42"));
		assertTrue(expanded.contains("Horse hockey"));
		assertTrue(expanded.contains("value.one"));
		assertTrue(expanded.contains("value.two"));
		assertTrue(expanded.contains("key_1"));
		assertTrue(expanded.contains("key_2"));
		assertTrue(expanded.contains("key_3"));
	}

	@Test
	public void testPropertyAttribute()
	{
		PropertyHolder ph_1 = new PropertyHolder("cow", 14, ':');
		PropertyHolder ph_2 = new PropertyHolder("dog", 39, 'x');
		
		
		String template = ""                +
				"one     : [{one}]"           + "\n" +
				"two     : [{two}]"           + "\n" +
				"a       : [{one.a}]"         + "\n" +
				"b       : [{one.b}]"         + "\n" +
				"c       : [{one.c}]"         + "\n" +
				"";

		ST st = new ST(template, '{', '}');

		st.add("one", ph_1);
		st.add("two", ph_2);
		st.add("two", ph_2);
		String expanded = st.render();

//		System.out.println(expanded);

		assertTrue(expanded.contains("[cow:14::]"));  
		assertTrue(expanded.contains("[cow]"));  
		
		// Apparently, only public fields can be seen by ST
		//
		assertFalse(expanded.contains("[14]"));
		assertFalse(expanded.contains("[:]"));
	}
	
	@Test
	public void testBeanAccess()
	{
		PropertyBean bean_one = new PropertyBean("cow", 14, ':');
		PropertyBean bean_two = new PropertyBean("dog", 39, 'x');
		
		
		String template = ""                +
				"one     : [{one}]"           + "\n" +
				"two     : [{two}]"           + "\n" +
				"a       : [{one.a}]"         + "\n" +
				"b       : [{one.b}]"         + "\n" +
				"c       : [{one.c}]"         + "\n" +
				"d       : [{one.d}]"         + "\n" +
				"e       : [{one.e}]"         + "\n" +
				"f       : [{one.f}]"         + "\n" +
				"";

		ST st = new ST(template, '{', '}');

		st.add("one", bean_one);
		st.add("two", bean_two);
		st.add("two", bean_two);
		String expanded = st.render();

//		System.out.println(expanded);

		assertTrue(expanded.contains("[cow:14::]"));  
		assertTrue(expanded.contains("[cow]"));  
		assertTrue(expanded.contains("[14]"));
		assertTrue(expanded.contains("[:]"));
		
		// Apparently, only public accessorMethods can be seen by ST
		//
		assertTrue(expanded.contains("[]"));
	}

	@Test
	public void testMethodAccess()
	{
		MethodExposer me = new MethodExposer("cow", 14, ':');
		
		String template = ""                +
				"one     : [{one}]"           + "\n" +
				"a       : [{one.a}]"         + "\n" +
				"b       : [{one.b}]"         + "\n" +
				"c       : [{one.c}]"         + "\n" +
				"";

		ST st = new ST(template, '{', '}');

		st.add("one", me);
		String expanded = st.render();

//		System.out.println(expanded);

		assertTrue(expanded.contains("[cow:14::]"));  

		
		// ST can't call a method without bean accessor style method names.
		//
		// Decidedly annoying to be forced to write foo.getA() instead of foo.a() by a template tool.
		// Pretty much exposes the era when this was written, too much enthusiasm for JavaBeans, XML, and such.
		//
		assertFalse(expanded.contains("[cow]"));  
		assertFalse(expanded.contains("[14]"));
		assertFalse(expanded.contains("[:]"));
		assertTrue(expanded.contains("[]"));
	}


}
