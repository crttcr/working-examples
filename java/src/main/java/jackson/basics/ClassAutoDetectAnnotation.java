package jackson.basics;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Trivial class to explore the behavior of the @AutoDetect annotation
 * on a class.
 *
 * It turns out that you have to follow JavaBeans conventions if you want the detection
 * to work. For serializing, there needs to be a getter method and for deserializing,
 * there needs to be a setter.
 * 
 * For deserialize, essentially what Jackson is doing is calling the default, no-arg
 * constructor, and then for any detected setters which have a correspondingly named
 * input in the source JSON, it will call that setter with the JSON value.
 * 
 * @author reid.dev
 *
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown=true)
public class ClassAutoDetectAnnotation
{
	private String name;
	private int height = 30;
	private int weight;

	public int getHeight()
	{
		return height;
	}

//	public void setHeight(int height)
//	{
//		this.height = height;
//	}

	public void setName(String name)
	{
		this.name = name;
	}

	
	@Override
   public String toString()
   {
		String cname = this.getClass().getName();
   	String   msg = String.format("%s.toString() -> name = %s, height = %d weight = %d", cname, name, height,weight);
   	
   	return msg;
   }

}
