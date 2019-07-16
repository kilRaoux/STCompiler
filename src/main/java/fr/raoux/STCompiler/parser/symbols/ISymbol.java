package fr.raoux.STCompiler.parser.symbols;

import java.util.Set;
import java.util.Stack;

import fr.raoux.STCompiler.parser.Exception.SyntaxeException;

public interface ISymbol {
	public int getId();
	public String getName();
	public String getValue();
	public boolean isNullable();
	public Set<Terminal> getPremier();
	public Set<Terminal> getSuivant();
	public void isInner(Rule rule);
	public void avance(Stack<ISymbol> stack, Terminal t) throws SyntaxeException;
}
