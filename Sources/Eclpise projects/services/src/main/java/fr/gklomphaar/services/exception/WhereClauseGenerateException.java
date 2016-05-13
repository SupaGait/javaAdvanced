package fr.gklomphaar.services.exception;

/**
 * Exception while building where clauses
 * @author Gerard
 *
 */
public class WhereClauseGenerateException extends Exception {
	static final long serialVersionUID = 1L;
	public WhereClauseGenerateException(Throwable cause) {
		super("Error while generating whereclauses", cause);
	}
}