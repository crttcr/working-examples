package jackson.basics;

import com.fasterxml.jackson.annotation.JsonProperty;


public class SingleFieldAnnotation
{
	@JsonProperty
	private String name;
	
	private int height;
	
	private int weight;
	
	@Override
   public String toString()
   {
		String cname = this.getClass().getName();
   	String   msg = String.format("%s.toString() -> name = %s, height = %d weight = %d", cname, name, height,weight);
   	
   	return msg;
   }

}
