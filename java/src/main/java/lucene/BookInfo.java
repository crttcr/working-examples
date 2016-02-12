package lucene;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;

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
	
	@JsonProperty("isnb")
	public abstract String isbn();

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
	
	public static Builder builder()
	{
		return new AutoValue_BookInfo.Builder();
	}
}