package lucene;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/** 
 * This class provides an example of how to index a data file using Lucene.
 * See the companion class SearchDataFile to query the index, once the index has
 * been built.
 * 
 * @author reid.dev
 */
public class IndexDataFile
{
	private IndexDataFile() {}
	
	public static void main(String[] args)
	{
		Map<String, Object> argmap = ProgramUtil.args2Map(args);
		Path            index_path = ProgramUtil.getIndexPath(argmap);
		Path              doc_path = ProgramUtil.getDocumentPath(argmap);
		boolean     overwrite_flag = ProgramUtil.getOverwriteFlag(argmap);
		
		
		try
		{
			String msg = String.format("Indexing to location [%s] ...", index_path);
			System.out.println(msg);
			
			Date         start = new Date();
			Directory      dir = FSDirectory.open(index_path);
			Analyzer    analyzer = new StandardAnalyzer();
			IndexWriter   writer = getIndexWriter(dir, analyzer, overwrite_flag);
			long        modified = doc_path.toFile().lastModified();
			
			indexFileOfData(writer, doc_path, modified);

			writer.close();

			Date     end = new Date();
			long  millis = end.getTime() - start.getTime();
			msg          = String.format("Completed indexing in [%d] milliseconds", millis);

			System.out.println(msg);
		}
		catch (IOException e)
		{
			String msg = String.format("Caught exception [%s] with message [%s]", e, e.getMessage());
			System.out.println(msg);
		}
	}

	private static void indexFileOfData(IndexWriter writer, Path doc_path, long modified)
		throws IOException
	{
		try (InputStream stream = Files.newInputStream(doc_path))
		{
			InputStreamReader isr = new InputStreamReader(stream, StandardCharsets.UTF_8);
			BufferedReader     br = new BufferedReader(isr);
			String     headerLine = br.readLine();
			String[]        parts = splitIntoFields(headerLine);
			
			String msg = String.format("Fields: %s", Arrays.toString(parts));
			System.out.println(msg);
			
			String line = null;
			int       i = 0;
			while ((line = br.readLine()) != null)
			{
				parts = splitIntoFields(line);
				if (i++ % 50000 == 0)
				{
					String s = Arrays.toString(parts);
					msg = String.format("Line %8d: %s", i, s);
					System.out.println(msg);
				}
				
				// Make a new empty document for each valid line of the file
				//
				Document d = new Document();
				
				// Add the ID of the record as a field named "id." Use a field that is indexed
				// (i.e. searchable), but don't tokenize or otherwise analyze/modify the field
				// into terms and don't index term frequency or positional information.
				//
				Field id_f = new StringField("id", parts[0], Field.Store.YES);
				d.add(id_f);
				
				// Create a composite field of the interesting details in the record to be searched.
				//
				String     content = createCompositeText(parts);
				if (content.length() == 0)
				{
					continue;
				}
				
				// NOTE: This is a TextField, not a StringField
				//
				Field content_f = new TextField("content", content, Field.Store.NO);
				d.add(content_f);

				// Add the TICKER of the record as a field named "ticker." Use a field that is indexed
				// (i.e. searchable), but don't tokenize or otherwise analyze/modify the field
				// into terms and don't index term frequency or positional information.
				//
				Field ticker_f = new StringField("ticker", parts[3], Field.Store.YES);
				d.add(ticker_f);
				
				writer.addDocument(d);
			}
		}
	}

	private static String createCompositeText(String[] parts)
	{
		if (parts == null || parts.length < 5)
		{
			String   s = Arrays.toString(parts);
			String msg = String.format("Failure splitting line into sufficient fields [%s]", s);
			System.out.println(msg);
			return "";
		}
		
		return String.join(" ", parts[1],  parts[2], parts[3], parts[4]);
	}

	private static String[] splitIntoFields(String line)
	{
		Validate.notBlank(line);
		
		// NOTE, this -1 is important because it causes split not to lose
		// empty fields. Read String.split() Javadoc for more information.
		//
		return line.split("\\|", -1);
	}

	private static IndexWriter getIndexWriter(Directory dir, Analyzer analyzer, boolean overwrite_flag)
	{
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		
		if (overwrite_flag)
		{
			// Create a new index in the directory, removing any previously indexed documents
			//
			iwc.setOpenMode(OpenMode.CREATE);
		}
		else
		{
			// Creates if does not exist, appends to any pre-existing documents.
			//
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
		}

		
		// Optional: for better indexing performance, if indexing many documents, increase the RAM
		// buffer. But also increase the heap size for the JVM (e.g. add -Xmx512m or -Xmx1g)
		//
		// iwc.setRAMBufferSizeMB(256.0);
		//
		IndexWriter writer = null;
		try
		{
			writer = new IndexWriter(dir, iwc);
		}
		catch(IOException e)
		{
			String msg = String.format("Failed to create IndexWriter [%s]", e.getMessage());
			System.out.println(msg);
			System.exit(1);
		}

		return writer;
	}

}
