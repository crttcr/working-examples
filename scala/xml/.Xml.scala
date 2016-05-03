// First class citizen
//
// Note: This doesn't work because the Yahoo API requires OAUTH

import scala.io._
import scala.xml._

val yahoo_client = "dj0yJmk9UHdsYzNNRFdoQ21qJmQ9WVdrOVJsUmplWGRHTldVbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD1jOQ--"
val yahoo_secret = "49c165bfb2673d3530cf070d9e558a1f201bf63a"

var xml = <hello></hello>

println(xml)


def getWeatherInfo(woeid : String) =
{
//	val url = "http://weather.yahooapis.com/forecastrss?w=" + woeid + "&u=f"
	val url = "http://api.openweathermap.org/data/2.5/weather?zip=94040,us"

	val response = scala.io.Source.fromURL(url).mkString

	val x_resp = XML.loadString(response)

	println(x_resp)

	// Return the tuple
	//
	(x_resp \\ "location" \\ "@city", x_resp \\ "condition" \\ "@temp" )
}


for (id <- 2391271 to 2391279)
{
	println(getWeatherInfo(id.toString))
}


