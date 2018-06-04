package xivvic.adt.pgraph.antlr;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import lombok.val;
import xivvic.adt.pgraph.antlr.error.AccumulatingErrorListener;
import xivvic.adt.pgraph.antlr.error.GraphDefinitionError.Severity;
import xivvic.adt.pgraph.antlr.error.GraphDefinitionResult;
import xivvic.adt.pgraph.antlr.error.GraphDefinitionResult.GraphDefinitionResultBuilder;
import xivvic.adt.pgraph.simple.Formatter;


public class SimpleGraphDriver
{
	private static String    INPUT_DIR = "src/test/resources/input";

	public static void main(String[] args) throws Exception
	{
		val  input = getInput();
		val tokens = string2tokens(input);

		val result_builder = GraphDefinitionResult.builder();
		val       listener = new SimpleGraphBuilder(result_builder);
		val         parser = constructAndConfigureParser(tokens, result_builder);

		walkTreeFromRoot(parser, listener);
		processResult(result_builder, listener);
	}

	private static void processResult(GraphDefinitionResultBuilder result_builder, SimpleGraphBuilder listener) {
		val result = result_builder.build();

		val ok = handleErrors(result);

		if (ok)
		{
			val graph = listener.graph();
			val   msg = Formatter.format(graph);
			System.out.println(msg);
		}
	}

	private static void walkTreeFromRoot(PropertyGraphParser parse, SimpleGraphBuilder listener) {
		val     tree = parse.graph();
		val   walker = new ParseTreeWalker();
		walker.walk(listener, tree);
	}

	private static CommonTokenStream string2tokens(String input)
	{
		val     cs = CharStreams.fromString(input);
		val    lex = new PropertyGraphLexer(cs);
		val tokens = new CommonTokenStream(lex);

		return tokens;
	}

	private static PropertyGraphParser constructAndConfigureParser(CommonTokenStream tokens, GraphDefinitionResultBuilder result_builder)
	{
		val  parser = new PropertyGraphParser(tokens);
		val error_listener = new AccumulatingErrorListener(result_builder);

		parser.removeErrorListeners();
		parser.addErrorListener(error_listener);

		return parser;
	}


	private static boolean handleErrors(GraphDefinitionResult result)
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


	private static String getInput()
	{
//		val  input = "(x:DOG {height = 2.3F, weight=12.5D, age=23, name='HBH \"The Ham\"', fur=\"wiry\"})\n(_:CAT {a=4,id='Sylvester'})\n(x)--[:CHASES {comic=true}]->(_)";
//		return input;

		val input = new File(INPUT_DIR, "deployment.pgraph");
//		val input = new File(INPUT_DIR, "country.pgraph");
//		val input = new File(INPUT_DIR, "financial.markets.pgraph");
//		val input = new File(INPUT_DIR, "world.trade.pgraph");
//		val input = new File(INPUT_DIR, "rdc.pgraph");

		@val
		byte[] bytes = null;
		try {
			bytes = Files.readAllBytes(input.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}

		val  content = new String(bytes);

		return content;
	}
}


