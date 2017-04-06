package args.marshall;

import java.util.Iterator;

import args.error.ArgsException;

public interface OptEvaluator<T>
{
	void set(Iterator<String> currentArgument) throws ArgsException;

	T getValue();
}
