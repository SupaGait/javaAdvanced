package fr.gklomphaar.services.exception;

/**
 * Exception for Hybernate query builder
 * @author Gerard
 *
 */
public class HybernateQueryException extends Exception {
	private static final long serialVersionUID = 1L;
	public HybernateQueryException(Throwable cause) {
		super("Error while generating hybernate query", cause);
	}
}