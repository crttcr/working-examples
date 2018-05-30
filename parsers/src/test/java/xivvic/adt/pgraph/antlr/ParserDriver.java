package xivvic.adt.pgraph.antlr;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.lang3.StringUtils;

import lombok.val;


public class ParserDriver
{
	private static String    INPUT_DIR = "src/test/resources/input";
	private static String    EXPECT_DIR = "src/test/resources/expected";
	private static String    INPUT_EXT = ".pgraph";
	private static String    EXPECT_EXT = ".expected";

	public static void main(String[] args) throws Exception
	{
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

		val msg = String.format("Wins [%d], Losses [%d]", win, lose);
		System.out.println(msg);

	}

	public static boolean testInputFile(File input) throws Exception
	{
		val fis = new FileInputStream(input);
		val bis = new BufferedInputStream(fis);
		val cs = CharStreams.fromStream(bis);
		val lex = new PropertyGraphLexer(cs);
		val tokens = new CommonTokenStream(lex);
		val parse = new PropertyGraphParser(tokens);
		val tree = parse.graph();

		val walker = new ParseTreeWalker();
		val listener = new EchoGraphListener();

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
			val   bytes = Files.readAllBytes(expected.toPath());
			final String content = new String(bytes);
			final String   rtrim = result.trim();
			final String   etrim = content.trim();
			final String    diff = StringUtils.difference(rtrim, etrim);

			if (diff.length() == 0)
				return true;

			System.out.println("Parsed content did not match expected: " + expected.getName());
			System.out.println("PARSED--------------");
			System.out.println(rtrim);
			System.out.println("EXPECT--------------");
			System.out.println(etrim);
			System.out.println("DIFF----------------");
			System.out.println(diff);
			System.out.println("END");
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
