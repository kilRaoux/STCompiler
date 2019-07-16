package fr.raoux.STCompiler.parser.symbols;

public class DynamicTerminal extends Terminal {

	private String regex;
	public DynamicTerminal(String name, String regex) {
		super(name);
		this.regex = regex;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean check(String str) {
		return str.matches(this.regex);
	}
}
