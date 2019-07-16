package fr.raoux.STCompiler.parser.symbols;

public class BreakerTerminal extends Terminal{

	private char separator;
	public BreakerTerminal(String name) {
		super(name);
		this.separator = name.charAt(0);
	}

	public BreakerTerminal(String name, char separator) {
		super(name,""+separator);
		this.separator = separator;
	}

	public char getSeparator() {
		return this.separator;
	}

}
