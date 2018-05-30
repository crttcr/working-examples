package xivvic.adt.pgraph.antlr;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import lombok.val;
import xivvic.adt.pgraph.simple.Formatter;


public class SimpleGraphDriver
{
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

		val graph = listener.graph();
		val   msg = Formatter.format(graph);

		System.out.println(msg);
	}


	public static String getInput()
	{
		val  input = "(x:DOG {height = 2.3F, weight=12.5D, age=23, name='HBH \"The Ham\"', fur=\"wiry\"})\n(_:CAT {a=4,id='Sylvester'})\n(x)--[:CHASES {comic=true}]->(_)";
		return input;

	}
}


