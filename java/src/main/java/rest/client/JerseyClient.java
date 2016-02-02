package rest.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.Validate;

public class JerseyClient
{
	public static final int   PORT = 8080;
	public static final String URI = String.format("http://localhost:%d/api",PORT);

	private final String base;
	
	public JerseyClient(String URI)
	{
		Validate.notEmpty(URI);

		this.base = URI;
	}
	
	public String getWithPathParam(String path, String param)
	{
		Client      client = ClientBuilder.newClient();
		WebTarget     root = client.target(base);
		WebTarget resource = root.path(path).path(param);
		
		Invocation.Builder builder = resource.request();
		Response          response = builder.get();
		
		String result = response.readEntity(String.class);
		return result;
	}
	
	public String getWithQueryParam(String path, String paramName, String paramValue)
	{
		Client      client = ClientBuilder.newClient();
		WebTarget     root = client.target(base);
		WebTarget resource = root.path(path);
		resource           = resource.queryParam(paramName, paramValue);
		
		Invocation.Builder builder = resource.request(MediaType.TEXT_PLAIN_TYPE);
		builder.header("X-Requested-With", "blowtorch");
		
		Response          response = builder.get();
		
		String result = response.readEntity(String.class);
		return result;
	}
	
	public static void main(String[] args)
	{
		JerseyClient jc = new JerseyClient(URI);
		String test = jc.getWithPathParam("hello", "Spanky");
		System.out.println("Using PathParam: " + test);

		test = jc.getWithQueryParam("hello", "name", "BeerNut");
		System.out.println("Using QueryParam: " + test);
	}
}