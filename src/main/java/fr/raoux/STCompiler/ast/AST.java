package fr.raoux.STCompiler.ast;

import java.util.List;

import fr.raoux.STCompiler.parser.symbols.ISymbol;

public class AST implements IASTNode {

	private String name;
	private ISymbol symbol;
	private List<IASTNode> children;
	public AST() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public ISymbol getSymbol() {
		// TODO Auto-generated method stub
		return symbol;
	}

	public List<IASTNode> getChildren() {
		// TODO Auto-generated method stub
		return children;
	}

	public void add(IASTNode node) {
		this.children.add(node);

	}

}
