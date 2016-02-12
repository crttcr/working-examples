package lucene;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.FilterMapping;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class BookIndexServer
{
	public static final String CORS_FILTER_NAME = "cross-origin";
	public static final int PORT = 8080;
	
	public static void main(String[] args)
	{
		ResourceConfig rc = new ResourceConfig();
		
		// Search for WS/REST Annotations in this package
		//
		rc.packages(LuceneBookApi.class.getPackage().getName());
		
		// Establish Jackson as processor for JSON
		//
		rc.register(JacksonFeature.class);
		
		ServletContainer    container = new ServletContainer(rc);
		ServletHolder        s_holder = new ServletHolder(container);
		Server                 server = new Server(PORT);
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		
		// Configure CORS access
		//
		FilterHolder         f_holder = new FilterHolder(CrossOriginFilter.class);
		f_holder.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
		f_holder.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
		f_holder.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,POST,HEAD");
		f_holder.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin");
		f_holder.setName(CORS_FILTER_NAME);
		
		FilterMapping f_mapping = new FilterMapping();
		f_mapping.setFilterName(CORS_FILTER_NAME);
		f_mapping.setPathSpec("*");
		
		context.addFilter(f_holder, "/*", EnumSet.of(DispatcherType.REQUEST));
		context.setContextPath("/");
		context.addServlet(s_holder, "/*");
		server.setHandler(context);
		
		try
		{
         server.start();
         server.join();
		}
		catch (Exception e)
		{
			System.err.println("Failed to start or interrupted waiting to join server:\n\t" + e.getMessage());
		}
		
	}

}
