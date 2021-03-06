package fr.raoux.STCompiler.parser.parser;

import java.io.IOException;
import java.util.Stack;

import fr.raoux.STCompiler.ast.AST;
import fr.raoux.STCompiler.parser.exception.SyntaxException;
import fr.raoux.STCompiler.parser.language.Language;
import fr.raoux.STCompiler.parser.symbols.ISymbol;

public abstract class ParserAbstract {

	protected Language lang;
	protected Stack<ISymbol> stack;

	public ParserAbstract(Language lang) {
		this.lang = lang;
		this.stack = new Stack<ISymbol>();
	}

	public abstract void build();
	public abstract AST run(String path) throws  IOException, SyntaxException;

}
