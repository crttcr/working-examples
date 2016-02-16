package patterns;

/** 
 * This example covers a simple enhancement to the Effective Java (Joshua Bloch)
 * Builder pattern.  By using a captive instance within the builder, at least
 * you don't have to repeat the field decls, even if you do have to provide builder
 * methods for them.
 * 
 * In the normal case, use AutoValue to not have to write classes like this at all.
 * (Ran into an exception where AV threw an exception due to specializing an implemented
 * sub-interface)
 * 
 * @author reid.dev
 */
public class BlochBuilderExample
{
	private String           name;
	private boolean		required;
	private final int         max;
	
	private BlochBuilderExample(int max)
	{
		this.max = max;
	}

	public String name()
	{
		return name;
	};

	public boolean required()
	{
		return required;
	};

	public int max()
	{
		return max;
	}

	public static class Builder
	{
		// If you were to make this field final, you can't reuse the builder with a new object.
		// That would permit you to potentially leak changes to the object if the caller holds on
		// to your builder.
		//
		private BlochBuilderExample instance;

		public Builder(int max)
		{
			instance = new BlochBuilderExample(max);
		}

		public Builder name(String name)
		{
			this.instance.name = name;
			return this;
		}

		public Builder required(boolean b)
		{
			this.instance.required = b;
			return this;
		}

		public BlochBuilderExample build()
		{
			BlochBuilderExample result = instance;

			instance = null;
			return result;
		}
	}

	// Note, the user must provide an integer max value in order to acquire the builder.
	//
	// This is useful if the built class needs to pass an argument to a superclass constructor
	// e.g. super(max);
	//
	// However, it does mean that the builder cannot be reused, as that would expose the field
	// values of the built object. (Hence setting instance = null in the build() method in this
	// variation. This example exposes the unwary user to an NPE, so probably would want to protect
	// against that or eliminate the parameter to the builder() method and replace null assignment with
	// instance = new BlochBuilderExample(). The downside there is no reasonable defaults for the second
	// use and possible confusion. Factor out the defaults to handle that issue.
	//
	// Force the use of this factory method (instead of allowing new BlochBuilderExample.Builder() 
	// by making a private constructor for your builder class.
	//
	public static Builder builder(int max)
	{
		Builder builder = new BlochBuilderExample.Builder(max);

		// Set reasonable default values
		//
		builder.required(false);

		return builder;
	}

}
