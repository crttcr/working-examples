package xivvic.adt.pgraph.antlr;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@Builder
public class GraphDefinitionError
{
	public static enum Severity
	{
		INFORMATION,
		WARNING,
		FAILURE
	}


	private final Severity severity;
	private final String message;
	private final int line_number;
	private final int character_position;

	public boolean issueDetected() { return severity.ordinal() > Severity.INFORMATION.ordinal(); }
}
