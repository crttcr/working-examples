import java.util.function.BiFunction

/**
  * Scala is more pleasant, even for demonstrating behavior or Java components.
  */
object MapComputeIfPresent
{
	type BIF = BiFunction[String, Int, Int]
	type MAP = java.util.concurrent.ConcurrentHashMap[String, Int]

	def main(args: Array[String]): Unit =
	{
		val x = MapComputeIfPresent()

		x.compute_if_present_value_exists__fn_returns_int
		x.compute_if_present_value_exists__fn_returns_null
		x.compute_if_present_value_exists__fn_throws_exception
		x.compute_if_present_value_missing_fn_returns_int
		x.compute_if_present_value_missing_fn_returns_null
		x.compute_if_present_value_missing_fn_throws_exception

	}

	def apply() = new MapComputeIfPresent()

	def show(prefix: String, map: MAP) = println(prefix + map)
	def before(map: MAP) = show("Before compute_if_present: ", map)
	def after (map: MAP) = show("After  compute_if_present: ", map)
}

class MapComputeIfPresent
{
	import MapComputeIfPresent._

	def fixture = new {
		val key = "Apple"
		val map = new MAP()
	}

	def compute_if_present_value_exists__fn_returns_int: Unit =
	{
		val f = fixture
		val mf: BIF = (a, b) => { (a + b.toString).length }

		f.map.put(f.key, 10)

		println("compute_if_present_value_exists__fn_returns_int")
		before(f.map)
		val v = f.map.computeIfPresent(f.key, mf)
		after(f.map)

		println
	}

	def compute_if_present_value_exists__fn_returns_null: Unit =
	{
		val f = fixture
		val mf: BIF = (a, b) => { val x: java.lang.Integer = null ; x}

		f.map.put(f.key, 10)

		println("compute_if_present_value_exists__fn_returns_null")
		before(f.map)
		f.map.computeIfPresent(f.key, mf)
		after(f.map)
		println
	}

	def compute_if_present_value_exists__fn_throws_exception: Unit =
	{
		val f = fixture
		val mf: BIF = (a, b) => throw new RuntimeException("Bang!")

		f.map.put(f.key, 10)

		println("compute_if_present_value_exists__fn_throws_exception")
		before(f.map)
		try
		{
			f.map.computeIfPresent(f.key, mf)
		}
		catch
		{
			case e: Exception => println("Caught exception: " + e.getLocalizedMessage)
		}
		after(f.map)
		println
	}

	def compute_if_present_value_missing_fn_returns_int: Unit =
	{
		val f = fixture
		val mf: BIF = (a, b) => { (a + b.toString).length }

		println("compute_if_present_value_missing_fn_returns_int")
		before(f.map)
		f.map.computeIfPresent(f.key, mf)
		after(f.map)
		println
	}


	def compute_if_present_value_missing_fn_returns_null: Unit =
	{
		val f = fixture
		val mf: BIF = (a, b) => { val x: java.lang.Integer = null ; x}

		println("compute_if_present_value_missing_fn_returns_null")
		before(f.map)
		f.map.computeIfPresent(f.key, mf)
		after(f.map)
		println
	}

	def compute_if_present_value_missing_fn_throws_exception: Unit =
	{
		val f = fixture
		val mf: BIF = (a, b) => throw new RuntimeException("Bang!")

		println("compute_if_present_value_missing_fn_throws_exception")
		before(f.map)
		try
		{
			f.map.computeIfPresent(f.key, mf)
		}
		catch
		{
			case e: Exception => println("Caught exception: " + e.getLocalizedMessage)
		}
		after(f.map)
		println
	}


}
