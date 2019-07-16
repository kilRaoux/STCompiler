package fr.raoux.STCompiler.parser.symbols;

public class EOFTerminal extends BreakerTerminal {

	private static EOFTerminal INSTANCE = new EOFTerminal();
	
	private EOFTerminal() {
		super("<EOF>");
		// TODO Auto-generated constructor stub
	}
	
	public static EOFTerminal getInstance() {
		return INSTANCE;
	}
	

}
