package fr.raoux.STCompiler.parser.Exception;

public class SyntaxException extends Exception {

	public SyntaxException() {
		// TODO Auto-generated constructor stub
	}

	public SyntaxException(String message) {
		super("Syntax Error:"+message);
		// TODO Auto-generated constructor stub
	}

	public SyntaxException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public SyntaxException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public SyntaxException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
