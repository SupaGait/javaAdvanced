/**
 * 
 */
package nl.gerardklomphaar.testpatterns;

import nl.gerardklomphaar.testpatterns.messageFactory.MessageInterface;

/**
 * @author Gerard
 *
 */
public final class Logger {
	private static Logger loggerInstance = new Logger();
	
	private Logger()
	{
	}
	
	public static Logger getLogger(){
		return loggerInstance;
	}
	
	public void log(MessageInterface message)
	{
		System.out.println(message.getMessageName() + message.getContext());
	}
}
