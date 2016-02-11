package template.st;

public class Template4MapData
{
	public static String getTemplateText()
	{
		String result = ""             +
			"Model: {data.model}"       + "\n" +
			"User : {data.user}"        + "\n" +
			"Child: {data.child.says}"  +
			"";
		
		return result;
	}
	
}
