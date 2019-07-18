package fr.raoux.STCompiler.parser.symbols;

public class EmptyTerminal extends Terminal {

	public EmptyTerminal() {
		super("<empty>");
	}
	public EmptyTerminal(String name) {
		super(name);
	}
	@Override
	public boolean isNullable() {
		return true;
	}

}
