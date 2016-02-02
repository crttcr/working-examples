package rest.jetty.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/api/hello")
public class ExampleApi
{
	public ExampleApi() {}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String helloQueryParam(@QueryParam("name") String name)
	{
		if (name == null)
			name = "Sparky!";
		else if (name.isEmpty())
			name = "BlankyBlankerson";
		
		return "Hello " + name;
	}

	@GET
	@Path("{name}")
	@Produces(MediaType.TEXT_PLAIN)
	public String helloPathParam(@PathParam("name") String name)
	{
		if (name == null)
			name = "Sparky!";
		
		return "Guten Tag " + name;
	}

}
