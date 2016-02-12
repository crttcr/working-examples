package lucene;

import java.io.IOException;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanQuery.Builder;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

@Path("/book")
public class LuceneBookApi
{
	private Directory index;

	public LuceneBookApi()
	{
		Map<String, Object> argmap = ProgramUtil.args2Map(null);
		java.nio.file.Path index_path = ProgramUtil.getIndexPath(argmap);
		try
		{
			index = FSDirectory.open(index_path);
		}
		catch (IOException e)
		{
			String msg = String.format("FAIL: Cannot open index [%s]",  e.getMessage());
			System.err.println(msg);
		}
	}

	@GET
	@Path("/hello/{name}")
	public String greeting(@PathParam("name") String name)
	{
		if (name == null)
			name = "Spanky!";
		
		return "Hello " + name;
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public BookInfo[] search(
		@QueryParam("title") String title,
		@QueryParam("author") String author,
		@QueryParam("keyword") String keyword
		)
	{
		Query query = constructQueryFromParameters(title, author, keyword);
		
		IndexSearcher searcher = LuceneUtil.getSearcher(index);
		BookInfo[]      result = null;
		TopDocs           docs = null;
		
		try
		{
			docs = searcher.search(query, 100);
			
			if(docs.totalHits == 0)
			{
				String msg = String.format("No results from query [%s]",  query.toString());
				System.out.println(msg);
				return new BookInfo[0];
			}
			
			ScoreDoc[] scores = docs.scoreDocs;
			result            = new BookInfo[scores.length];
			
			for (int i = 0; i < scores.length; i++)
			{
				Document doc = searcher.doc(scores[i].doc);
				String  d_title = doc.get("title");
				String  d_author = doc.get("author");
				String  d_isbn = doc.get("isbn");
				
				result[i] = BookInfo.builder().authors(d_author).title(d_title).isbn(d_isbn).build();
			}
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return result;
	}

	private Query constructQueryFromParameters(String title, String author, String keyword)
	{
		Builder builder = new BooleanQuery.Builder();
		
		if (title != null)
		{
			Term term = new Term("title", title.toLowerCase());
			Query   q = new TermQuery(term);
			builder.add(q, Occur.SHOULD);
		}
		
		if (author != null)
		{
			Term term = new Term("author", author.toLowerCase());
			Query   q = new TermQuery(term);
			builder.add(q, Occur.SHOULD);
		}
		
		if (keyword != null)
		{
			Term term = new Term("content", keyword.toLowerCase());
			Query   q = new TermQuery(term);
			builder.add(q, Occur.SHOULD);
		}
		
		// TODO: Handle the case where you have no parameter values
		//
		return builder.build();
	}

	
	public String summaryString(int number, Document doc, ScoreDoc score)
	{
		StringBuilder sb = new StringBuilder();
		
		String fmt = String.format("Document [#=%6d, Score=%7.4f]: ", number, score.score);
		sb.append(fmt);
		sb.append("isbn").append(doc.get("isbn"));
		sb.append(doc.get("title"));
		
		return sb.toString();
	}

}
