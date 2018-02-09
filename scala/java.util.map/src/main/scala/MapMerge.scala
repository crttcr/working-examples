import java.util.function.BiFunction

/**
  * Scala is more pleasant, even for demonstrating behavior or Java components.
  */
object MapMerge
{
	type BIF = BiFunction[Int, Int, Int]
	type MAP = java.util.concurrent.ConcurrentHashMap[String, Int]

	def main(args: Array[String]): Unit =
	{
		val x = MapMerge()

		x.merge_value_exists__fn_returns_int
		x.merge_value_exists__fn_returns_null
		x.merge_value_exists__fn_throws_exception
		x.merge_value_missing_fn_returns_int
		x.merge_value_missing_fn_returns_null
		x.merge_value_missing_fn_throws_exception

	}

	def apply() = new MapMerge()

	def show(prefix: String, map: MAP) = println(prefix + map)
	def before(map: MAP) = show("Before merge: ", map)
	def after (map: MAP) = show("After  merge: ", map)
}

class MapMerge
{
	import MapMerge._

	def fixture = new {
		val key = "Apple"
		val map = new MAP()
	}

	def merge_value_exists__fn_returns_int: Unit =
	{
		val f = fixture
		val mf: BIF = (a, b) => { a + b }

		f.map.put(f.key, 10)

		println("merge_value_exists__fn_returns_int")
		before(f.map)
		f.map.merge(f.key, 2, mf)
		after(f.map)
		println
	}

	def merge_value_exists__fn_returns_null: Unit =
	{
		val f = fixture
		val mf: BIF = (a, b) => { val x: java.lang.Integer = null ; x}

		f.map.put(f.key, 10)

		println("merge_value_exists__fn_returns_null")
		before(f.map)
		f.map.merge(f.key, 2, mf)
		after(f.map)
		println
	}

	def merge_value_exists__fn_throws_exception: Unit =
	{
		val f = fixture
		val mf: BIF = (a, b) => throw new RuntimeException("Bang!")

		f.map.put(f.key, 10)

		println("merge_value_exists__fn_throws_exception")
		before(f.map)
		try
		{
			f.map.merge(f.key, 2, mf)
		}
		catch
		{
			case e: Exception => println("Caught exception: " + e.getLocalizedMessage)
		}
		after(f.map)
		println
	}

	def merge_value_missing_fn_returns_int: Unit =
	{
		val f = fixture
		val mf: BIF = (a, b) => { a + b }

		println("merge_value_missing_fn_returns_int")
		before(f.map)
		f.map.merge(f.key, 2, mf)
		after(f.map)
		println
	}


	def merge_value_missing_fn_returns_null: Unit =
	{
		val f = fixture
		val mf: BIF = (a, b) => { val x: java.lang.Integer = null ; x}

		println("merge_value_missing_fn_returns_null")
		before(f.map)
		f.map.merge(f.key, 2, mf)
		after(f.map)
		println
	}

	def merge_value_missing_fn_throws_exception: Unit =
	{
		val f = fixture
		val mf: BIF = (a, b) => throw new RuntimeException("Bang!")

		println("merge_value_missing_fn_throws_exception")
		before(f.map)
		try
		{
			f.map.merge(f.key, 2, mf)
		}
		catch
		{
			case e: Exception => println("Caught exception: " + e.getLocalizedMessage)
		}
		after(f.map)
		println
	}


}
