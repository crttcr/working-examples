package xivvic.adt.pgraph.antlr;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Singular;
import lombok.val;
import xivvic.adt.pgraph.antlr.GraphDefinitionError.Severity;

@Builder
public class GraphParseResult
{
	public static enum Result
	{
		SUCCESS,
		FAILURE,
	}


	@Singular private List<GraphDefinitionError> errors;

	public Result status()
	{
		val has_errors = errors.stream().anyMatch(GraphDefinitionError::issueDetected);

		return has_errors ? Result.FAILURE :  Result.SUCCESS;
	}

	public boolean isSuccess() { return status() == Result.SUCCESS; }
	public boolean isFailure() { return status() == Result.FAILURE; }

	public List<GraphDefinitionError> errorsForSeverity(Severity sev)
	{
		return errors
					.stream()
					.filter(e -> e.severity() == sev)
					.collect(Collectors.toList());
	}
}
