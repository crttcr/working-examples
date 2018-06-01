package xivvic.adt.pgraph.antlr;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import lombok.val;
import xivvic.adt.pgraph.antlr.GraphDefinitionError.Severity;
import xivvic.adt.pgraph.simple.Formatter;


public class SimpleGraphDriver
{
	private static String    INPUT_DIR = "src/test/resources/input";

	public static void main(String[] args) throws Exception
	{
		val  input = getInput();
		val     cs = CharStreams.fromString(input);
		val    lex = new PropertyGraphLexer(cs);
		val tokens = new CommonTokenStream(lex);
		val  parse = new PropertyGraphParser(tokens);
		val   tree = parse.graph();

		val   walker = new ParseTreeWalker();
		val listener = new SimpleGraphBuilder();

		walker.walk(listener, tree);

		val result = listener.result();

		val ok = handleErrors(result);

		if (ok)
		{
			val graph = listener.graph();
			val   msg = Formatter.format(graph);
			System.out.println(msg);
		}

	}

	public static boolean handleErrors(GraphParseResult result)
	{
		val fail = result.isFailure();

		for (Severity sev : Severity.values())
		{
			val es = result.errorsForSeverity(sev);
			if (es.size() == 0)
			{
				continue;
			}

			System.out.println(sev.name() + ":");

			es.stream().forEach(System.out::println);

		}



		return ! fail;
	}


	public static String getInput()
	{
//		val  input = "(x:DOG {height = 2.3F, weight=12.5D, age=23, name='HBH \"The Ham\"', fur=\"wiry\"})\n(_:CAT {a=4,id='Sylvester'})\n(x)--[:CHASES {comic=true}]->(_)";
//		return input;

		val expected = new File(INPUT_DIR, "country.pgraph");
//		val expected = new File(INPUT_DIR, "financial.markets.pgraph");
//		val expected = new File(INPUT_DIR, "world.trade.pgraph");
//		val expected = new File(INPUT_DIR, "rdc.pgraph");

		@val
		byte[] bytes = null;
		try {
			bytes = Files.readAllBytes(expected.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}

		val  content = new String(bytes);

		return content;
	}
}


