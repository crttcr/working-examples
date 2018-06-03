package xivvic.adt.pgraph.antlr.error;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

import lombok.Builder;
import lombok.Data;
import lombok.val;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@Builder
public class GraphDefinitionError
{
	public static final String        FMT_SYNTAX_ERROR = "The model has a syntax error: [%s]";
	public static final String        FMT_SYMBOL_REUSE = "Redefinition of vertex symbol [%s]. Previous vertices with this symbol will be hidden";
	public static final String FMT_SYMBOL_NOT_RESOLVED = "Reference to [%s] not resolved to a vertex symbol. Referencing edge will not be created.";

	public static enum Severity
	{
		INFORMATION,
		WARNING,
		FAILURE
	}

	public static enum Phase
	{
		LEXICAL,
		SEMANTIC,
	}



	private final Phase phase;
	private final Severity severity;
	private final String message;
	private final int line_number;
	private final int character_position;

	public boolean issueDetected() { return severity.ordinal() != Severity.INFORMATION.ordinal(); }


	@Data
	public static class Coordinates
	{
		private final int line_number;
		private final int character_position;

		private Coordinates(TerminalNode node)
		{
			this.line_number = node.getSymbol().getLine();
			this.character_position = node.getSymbol().getCharPositionInLine();
		}
	}


	public static GraphDefinitionError getSyntaxError(Object symbol, int line, int char_pos, String error_text)
	{
		val builder = GraphDefinitionError.builder();
		val     msg = String.format(FMT_SYNTAX_ERROR, symbol + ", err: " +  error_text);

		builder
			.severity(Severity.FAILURE)
			.phase(Phase.LEXICAL)
			.message(msg)
			.line_number(line)
			.character_position(char_pos);

		val err = builder.build();
		return err;
	}

	public static GraphDefinitionError getSymbolError(TerminalNode node, ParserRuleContext c, Severity sev, String fmt)
	{
		val builder = GraphDefinitionError.builder();
		val     msg = String.format(fmt, node.getText());
		val     pos = new Coordinates(node);

		builder
			.severity(sev)
			.phase(Phase.SEMANTIC)
			.message(msg)
			.line_number(pos.line_number)
			.character_position(pos.character_position);

		val err = builder.build();
		return err;
	}

}
