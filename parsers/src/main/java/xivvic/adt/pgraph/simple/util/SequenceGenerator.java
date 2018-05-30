package xivvic.adt.pgraph.simple.util;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.val;

public class SequenceGenerator
{
	private static AtomicInteger next = new AtomicInteger(0);

	public static int next_id_int()
	{
		return next.incrementAndGet();
	}

	public static String next_id_string()
	{
		val id = next.incrementAndGet();
		return Integer.toString(id);
	}

}
