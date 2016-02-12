package lucene;

import java.io.IOException;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;

public class LuceneUtil
{
	public static IndexSearcher getSearcher(Directory index)
	{
		IndexReader reader = null;
		try
		{
			reader = DirectoryReader.open(index);
		}
		catch (IOException e)
		{
			String msg = String.format("Failed to open reader: %s", e.getMessage());
			System.err.println(msg);
			return null;
		}
		
		
		IndexSearcher searcher = new IndexSearcher(reader);
		
		return searcher;
	}

}
