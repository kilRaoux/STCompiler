package fr.raoux.STCompiler.parser;

import fr.raoux.STCompiler.ast.AST;

public class ParserDescent extends ParserAbstract {

	public ParserDescent(Language lang) {
		super(lang);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void build() {
		// TODO Auto-generated method stub

	}

	@Override
	public AST run() {
		this.stack.addAll(lang.getStartSymbol().getRules().get(0).getSymbols());
		System.out.println("STACK"+this.stack.size());
		return new AST();
	}

}
