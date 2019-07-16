package fr.raoux.STCompiler.parser.symbols;

public class SpaceTerminal extends BreakerTerminal {

	public SpaceTerminal(String name) {
		super(name, ' ');
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean check(String str) {
		return str.equals(" ");
	}

}
