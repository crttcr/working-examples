package xivvic.adt.pgraph.antlr;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

import lombok.Data;
import lombok.val;
import xivvic.adt.pgraph.antlr.GraphDefinitionError.Severity;

public class GraphBuilderElf
{
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

	public static GraphDefinitionError getReusedSymbolicNameError(TerminalNode node, ParserRuleContext c)
	{
		val builder = GraphDefinitionError.builder();

		val  fmt = "Redefinition of vertex symbol [%s]. Previous vertices with this symbol will be hidden";
		val  msg = String.format(fmt, node.getText());
		val  pos = new Coordinates(node);

		builder
			.severity(Severity.WARNING)
			.message(msg)
			.line_number(pos.line_number)
			.character_position(pos.character_position);

		val err = builder.build();
		return err;
	}

	public static GraphDefinitionError getReferencedSymbolNotFoundError(TerminalNode node, ParserRuleContext c)
	{
		val builder = GraphDefinitionError.builder();

		val  fmt = "Reference to [%s] not resolved to a vertex symbol. Referencing edge will not be created.";
		val  msg = String.format(fmt, node.getText());
		val  pos = new Coordinates(node);

		builder
			.severity(Severity.FAILURE)
			.message(msg)
			.line_number(pos.line_number)
			.character_position(pos.character_position);

		val err = builder.build();
		return err;
	}
}
