package args.schema;

public interface Schema
{
	<T> Item<T> getItem(char c);
}
