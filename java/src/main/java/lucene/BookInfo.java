package lucene;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lucene.BookInfo.BookInfoBuilder;

@Data
@lombok.Builder
@JsonDeserialize(builder=BookInfoBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookInfo
{
	public static final List<BookInfo> EMPTY_LIST =  Collections.<BookInfo>emptyList();

	private String title;
	private String authors;
	private String isbn;

}