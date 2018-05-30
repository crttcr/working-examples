package xivvic.adt.pgraph.antlr;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import lombok.val;


public class SimpleGraphDriver
{
	private static String    INPUT_DIR = "src/test/resources/input";
	private static String    EXPECT_DIR = "src/test/resources/expected";
	private static String    INPUT_FILE = "v1p1_e0p0";
	private static String    INPUT_EXT = ".pgraph";
	private static String    EXPECT_EXT = ".expected";

	public static void main(String[] args) throws Exception
	{
		final String input = "(x:DOG {height = 2.3F})";
//		final String input = "(x:DOG {height = 2.3F})\n(_:CAT {a=4})";
//		final String input = "(x:DOG {height = 2.3F, weight=12.5D, age=23, name='ROVER \"funky\"', fur=\"wiry\"})\n(_:CAT {a=4})";

		final CharStream cs = CharStreams.fromString(input);
		final PropertyGraphLexer lex = new PropertyGraphLexer(cs);
		final CommonTokenStream tokens = new CommonTokenStream(lex);
		final PropertyGraphParser parse = new PropertyGraphParser(tokens);

		final ParseTree tree = parse.graph();

		final ParseTreeWalker walker = new ParseTreeWalker();
		val listener = new SimpleGraphBuilder();

		walker.walk(listener, tree);

		val out = listener.graph();

		System.out.println(out);


	}
}


