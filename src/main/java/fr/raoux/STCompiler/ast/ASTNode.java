package fr.raoux.STCompiler.ast;

import java.util.ArrayList;
import java.util.List;

import fr.raoux.STCompiler.parser.symbols.ISymbol;

public class ASTNode implements IASTNode {
	private String name;
	private ISymbol symb;
	private List<IASTNode> children = new ArrayList<IASTNode>();
	public ASTNode(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public ISymbol getSymbol() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IASTNode> getChildren() {
		// TODO Auto-generated method stub
		return this.children;
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
