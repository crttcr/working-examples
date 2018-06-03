package xivvic.adt.pgraph.antlr.error;

import java.util.List;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import lombok.val;
import xivvic.adt.pgraph.antlr.PropertyGraphParser;
import xivvic.adt.pgraph.antlr.error.GraphDefinitionResult.GraphDefinitionResultBuilder;

public class AccumulatingErrorListener extends BaseErrorListener
{

	private final GraphDefinitionResultBuilder result_builder;

	public AccumulatingErrorListener(GraphDefinitionResultBuilder result_builder)
	{
		this.result_builder = result_builder;
	}

	@Override
	public void syntaxError(Recognizer<?, ?> recognizer,
				Object offendingSymbol,
				int line,
				int char_position_in_line,
				String msg,
				RecognitionException e)
	{
		List<String> stack = ((PropertyGraphParser) recognizer).getRuleInvocationStack();

		val   rule = stack.get(0);
		val  coord = String.format("Coordinates      : [%d:%d]", line, char_position_in_line);
		val symbol = String.format("Offending Symbol : [%s]", offendingSymbol);
		val  descr = String.format("Error description: [%s]", msg);

		System.err.println("Syntax error in property graph definition.");
		System.err.println("The input did not conform to the required lexical rules defined by the parser.");
		System.err.println("Matching rule    : " + rule);

		System.err.println(coord);
		System.err.println(symbol);
		System.err.println(descr);

		// Add a GraphDefitionError to the result builder so that the constructing
		// context can build the result and handle any errors.
		//
		val error = buildDefinitionError(symbol, line, char_position_in_line, msg);
		result_builder.error(error);
	}


	private GraphDefinitionError buildDefinitionError(Object symbol, int line, int char_pos, String msg)
	{
		val error = GraphDefinitionError.getSyntaxError(symbol, line, char_pos, msg);
		return error;
	}

}
