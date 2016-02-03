package j7.coin;

/**
 * Closeable object to demonstrate Try-with-resources
 * 
 * @author reid.dev
 *
 */
public class TwrCloseable
	implements AutoCloseable
{
	boolean closeWasCalled = false;

	@Override
	public void close()
	{
		if (closeWasCalled)
		{
			String msg = String.format("Closing an already closed object! : [%s] ", this.toString());
			System.err.println(msg);
			throw new IllegalStateException(msg);
		}
		
		closeWasCalled = true;
	}
	
	public boolean wasClosed()
	{
		return closeWasCalled;
	}

}
