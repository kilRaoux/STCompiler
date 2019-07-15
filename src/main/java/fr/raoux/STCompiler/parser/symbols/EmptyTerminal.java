package fr.raoux.STCompiler.parser.symbols;

public class EmptyTerminal extends Terminal {

	public EmptyTerminal() {
		super("<empty>");
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean isNullable() {
		return true;
	}
}
