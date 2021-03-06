package template.velocity;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;

public class VelocityUtil
{
	// Since v1.2 Velocity supports a non-singleton approach
	// using a VelocityEngine instance to replace static methods
	// on the Velocity class.
	//
	public static VelocityEngine getVelocityEngine()
	{
		VelocityEngine ve = new VelocityEngine();
		
		ve.setProperty(Velocity.RESOURCE_LOADER, "string");
		ve.addProperty("string.resource.loader.class", StringResourceLoader.class.getName());
		ve.addProperty("string.resource.loader.modificationCheckInterval", "1");
		ve.init();
		
		return ve;
	}

	public static boolean initializeVelocityForStringTemplates()
	{

//    Velocity.reset();
//    Velocity.setProperty(Velocity.RUNTIME_LOG_INSTANCE, new TestLogger());

		Velocity.setProperty(Velocity.RESOURCE_LOADER, "string");
		Velocity.addProperty("string.resource.loader.class", StringResourceLoader.class.getName());
		Velocity.addProperty("string.resource.loader.modificationCheckInterval", "1");
		Velocity.init();
		
		return true;
	}
	
	
	public static StringResourceRepository getRepo(String name, VelocityEngine engine)
   {
       if (engine == null)
       {
           if (name == null)
           {
               return StringResourceLoader.getRepository();
           }
           else
           {
               return StringResourceLoader.getRepository(name);
           }
       }
       else
       {
           if (name == null)
           {
               return (StringResourceRepository)engine.getApplicationAttribute(StringResourceLoader.REPOSITORY_NAME_DEFAULT);
           }
           else
           {
               return (StringResourceRepository)engine.getApplicationAttribute(name);
           }
       }
   }

}
