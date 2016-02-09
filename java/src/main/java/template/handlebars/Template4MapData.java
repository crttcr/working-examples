package template.handlebars;

public class Template4MapData
{
	public static String getTemplateText()
	{
		String result = ""         +
			"Model: {{model}}"      + "\n" +
			"User : {{user}}"       + "\n" +
			"Child: {{child.says}}" +
			"";
		
		return result;
	}
	
}
