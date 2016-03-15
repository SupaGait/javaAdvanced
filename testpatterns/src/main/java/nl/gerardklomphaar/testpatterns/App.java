package nl.gerardklomphaar.testpatterns;

import nl.gerardklomphaar.testpatterns.messageFactory.MessageFactory;
import nl.gerardklomphaar.testpatterns.messageFactory.MessageInterface;
import nl.gerardklomphaar.testpatterns.messageFactory.MessageTypes;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	// Logger singleton
        Logger logger = Logger.getLogger();
        // Message factory
        MessageFactory factory = MessageFactory.get();
        
        // Create a new message, and give to the logger instance.
        final MessageInterface message = factory.createNewMessage("Im a factory created message", MessageTypes.ErrorMessage);
		logger.log( message );
        
    }
}
