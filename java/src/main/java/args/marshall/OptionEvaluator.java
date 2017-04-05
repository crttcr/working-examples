package args.marshall;

import java.util.Iterator;

import args.ArgsException;

public interface OptionEvaluator
{
	public void set(Iterator<String> currentArgument) throws ArgsException;
}
