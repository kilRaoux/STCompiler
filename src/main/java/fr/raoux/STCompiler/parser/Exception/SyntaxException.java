package fr.raoux.STCompiler.parser.Exception;

import java.io.IOException;

import fr.raoux.STCompiler.parser.SourceReader;

public class SyntaxException extends Exception {

	public SyntaxException() {
		// TODO Auto-generated constructor stub
	}

	public SyntaxException(String message) {
		super("Syntax Error:"+message +SourceReader.getInstance().atLine()+"\n");
		try {
			SourceReader.getInstance().affAllSrouce();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
