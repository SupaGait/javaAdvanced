package nl.gerardklomphaar.testpatterns.messageFactory;

class ErrorMessage implements MessageInterface
{
	private String message;
	ErrorMessage(String message)
	{
		this.message = message;
	}
	public String getMessageName() {
		return "Error: ";
	}
	public String getContext() {
		return this.message;
	}
}

public class MessageFactory 
{
	private static MessageFactory instance = new MessageFactory();
	
	private MessageFactory()
	{	
	}
	
	public static MessageFactory get()
	{
		return instance;
	}
	public MessageInterface createNewMessage(String message, MessageTypes type)
	{
		MessageInterface instance = null;
		
		switch(type)
		{
		case ErrorMessage:
			instance = new ErrorMessage(message);
			break;
		case InfoMessage:
			instance = null; // Additional
			break;
		}
		
		return instance;
	}
}
