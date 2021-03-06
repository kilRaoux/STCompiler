package fr.raoux.STCompiler.ast;

import java.util.ArrayList;
import java.util.List;

import fr.raoux.STCompiler.parser.symbols.ISymbol;

public class AST implements IASTNode {

	private String name;
	private ISymbol symbol;
	private List<IASTNode> children = new ArrayList<IASTNode>();
	public AST(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public ISymbol getSymbol() {
		// TODO Auto-generated method stub
		return symbol;
	}

	@Override
	public List<IASTNode> getChildren() {
		// TODO Auto-generated method stub
		return children;
	}

	@Override
	public void add(IASTNode node) {
		this.children.add(node);

	}
	@Override
	public void add(String name) {
		this.children.add(new ASTNode(name));

	}

}
