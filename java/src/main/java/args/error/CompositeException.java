package args.error;

import java.util.List;
import java.util.Objects;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper=false)
public class CompositeException
extends ArgsException
{
	private final List<ArgsException> exceptions;
	public CompositeException(List<ArgsException> exceptions)
	{
		super(ErrorCode.COMPOSITE_ERROR);

		this.exceptions = Objects.requireNonNull(exceptions);
	}

	@Override
	public String errorMessage()
	{
		StringBuilder sb = new StringBuilder(super.toString());
		for (ArgsException ex : exceptions)
		{
			sb.append("\n\t").append(ex.errorMessage());
		}

		sb.append("\n");
		return sb.toString();
	}

}
