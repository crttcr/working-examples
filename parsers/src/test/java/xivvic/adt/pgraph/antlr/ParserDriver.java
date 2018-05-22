package xivvic.adt.pgraph.antlr;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;


public class ParserDriver
{
	private static String    INPUT_DIR = "src/test/resources/input";
	private static String    EXPECT_DIR = "src/test/resources/expected";
	private static String    INPUT_EXT = ".pgraph";
	private static String    EXPECT_EXT = ".expected";

	public static void main(String[] args) throws Exception
	{
		//final String input = "(x:DOG {height = 2.3F, weight=12.5D, age=23, name='ROVER \"funky\"', fur=\"wiry\"})\n(_:CAT {a=4})";

		/*

		final CharStream cs = CharStreams.fromString(input);
		final PropertyGraphLexer lex = new PropertyGraphLexer(cs);
		final CommonTokenStream tokens = new CommonTokenStream(lex);
		final PropertyGraphParser parse = new PropertyGraphParser(tokens);

		final ParseTree tree = parse.graph();

		final ParseTreeWalker walker = new ParseTreeWalker();

		walker.walk(new EchoGraphListener(), tree);


		System.out.println(tree.toStringTree());
		*/

		final File[] inputs = getInputFiles();
		int win = 0;
		int lose = 0;

		for (final File in: inputs)
		{
			final boolean ok = testInputFile(in);

			if (ok)
			{
				win++;
			}
			else
			{
				lose++;
			}
		}

		final String msg = String.format("Wins [%d], Losses [%d]", win, lose);
		System.out.println(msg);

	}


	public static boolean testInputFile(File input) throws Exception
	{
		final FileInputStream fis = new FileInputStream(input);
		final BufferedInputStream bis = new BufferedInputStream(fis);
		final CharStream cs = CharStreams.fromStream(bis);
		final PropertyGraphLexer lex = new PropertyGraphLexer(cs);
		final CommonTokenStream tokens = new CommonTokenStream(lex);
		final PropertyGraphParser parse = new PropertyGraphParser(tokens);

		final ParseTree tree = parse.graph();

		final ParseTreeWalker walker = new ParseTreeWalker();
		final EchoGraphListener listener = new EchoGraphListener();

		walker.walk(listener, tree);

		final String result = listener.result();
		final File   expect = getExpectedFileMatchingInputFile(input);
		final boolean    rv = compareResultToExpected(result, expect);
		return rv;
	}

	public static File getExpectedFileMatchingInputFile(File input)
	{
		final   String name = input.getName();
		final       int pos = name.indexOf(INPUT_EXT);
		final String   stem = name.substring(0, pos);
		final String target = stem + EXPECT_EXT;
		final File   expect = new File(EXPECT_DIR, target);
		return expect;
	}

	public static boolean compareResultToExpected(String result, File expected)
	{
		try
		{
			final byte[]   bytes = Files.readAllBytes(expected.toPath());
			final String content = new String(bytes);
			final String   rtrim = result.trim();
			final String   etrim = content.trim();

			System.out.println(rtrim);
			System.out.println(etrim);

			return rtrim.equals(etrim);
		} catch (final IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}


	public static File[] getInputFiles()
	{
		final File              dir = new File(INPUT_DIR);
		final FilenameFilter filter = new InputFileNameFilter();
		final File[]             rv = dir.listFiles(filter);

		return rv;
	}


	public static class InputFileNameFilter implements FilenameFilter
	{
		@Override
		public boolean accept(File dir, String name)
		{
			return name.endsWith(INPUT_EXT);
		}
	}

}
