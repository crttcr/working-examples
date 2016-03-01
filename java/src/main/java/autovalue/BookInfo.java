package autovalue;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;
import com.sun.istack.internal.Nullable;

/** 
 * Example class that uses both AutoValue and Jackson.
 * Primarily used here to demonstrate how syntax of AutoValue.
 * 
 * @author reid.dev
 *
 */
@AutoValue
@JsonDeserialize(builder=AutoValue_BookInfo.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BookInfo
{
	public static final List<BookInfo> EMPTY_LIST =  Collections.<BookInfo>emptyList();

	@JsonProperty("title")
	public abstract String title();
	
	@JsonProperty("authors")
	public abstract String authors();
	
	// Use this Annotation for optional properties
	//
	@Nullable
	@JsonProperty("isnb")
	public abstract String isbn();

	// Builder pattern is optional, but it's a good idea when there are more than 2-3
	// constructor arguments
	//
	@AutoValue.Builder
	public abstract static class Builder
	{
		@JsonProperty("title")
		public abstract Builder title(String title);

		@JsonProperty("isbn")
		public abstract Builder isbn(String isbn);

		@JsonProperty("authors")
		public abstract Builder authors(String authors);

		public abstract BookInfo   build();
	}
	

	// Can expose a static method that enforces minimum required data
	//
	public static Builder builder(String title)
	{
		Objects.requireNonNull(title, "Title required");
		
		Builder builder = new AutoValue_BookInfo.Builder();
		
		builder.title(title);
		
		return builder;
	}

	// Or a static method that provides default values
	//
	public static Builder builder()
	{
		Builder builder = new AutoValue_BookInfo.Builder();
		
		// Can introduce default values here
		//
		builder.title("Title not disclosed");
		
		return builder;
	}
}