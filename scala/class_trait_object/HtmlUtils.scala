object HtmlUtils
{
	def removeMarkup(input: String) = 
	{
		input
			.replaceAll("""</?\w[^>]*>""","")
			.replaceAll("<.*>", "")
	}
}

val html = "<html><body><h1>Introduction</h1></body></html>"
val text = HtmlUtils.removeMarkup(html)

println(text)
