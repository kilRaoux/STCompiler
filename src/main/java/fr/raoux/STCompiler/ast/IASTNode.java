package fr.raoux.STCompiler.ast;

import java.util.List;

import fr.raoux.STCompiler.parser.symbols.ISymbol;

public interface IASTNode {

	public String getName();
	public ISymbol getSymbol();
	public List<IASTNode> getChildren();
	public void add(IASTNode node);
	public void add(String name);
	public default void print() {
		System.out.print(this.getName());
		for(IASTNode node: this.getChildren()) {
			node.print();
		}
	};

}
