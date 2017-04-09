package args;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class TestUtil
{
	public static ListIterator<String> getListIterator(String...strings)
	{
		List<String> list = Arrays.asList(strings);
		ListIterator<String> it = list.listIterator();
		return it;
	}


}
