package fr.raoux.STCompiler.parser.symbols;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import fr.raoux.STCompiler.ast.IASTNode;
import fr.raoux.STCompiler.parser.exception.SyntaxException;

public class Terminal implements ISymbol {
	private static int TID;
	private int id;
	private String name;
	private String value;
	public Terminal(String name) {
		this(name,name);
	}
	public Terminal(String name, String value) {
		this.id = TID++;
		this.name = name;
		this.value = value;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getValue() {
		return this.value;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\nTerminale----------------------------\n");
		sb.append("    name:"+this.name);
		sb.append("    Nullable: "+(this.isNullable()?"nullable":"not nullable"));
		return sb.toString();
	}

	@Override
	public boolean isNullable() {
		return false;
	}

	@Override
	public Set<Terminal> getPremier(){
		Set<Terminal> res = new HashSet<Terminal>();
		res.add(this);
		return res;
	}
	@Override
	public Set<Terminal> getSuivant() {
		Set<Terminal> res = new HashSet<Terminal>();
		res.add(this);
		return res;
	}
	@Override
	public void isInner(Rule rule) {

	}
	/**
	 * Check if the value of string parameter can be this terminal.
	 * @param str string to parse
	 * @return true if is, false else.
	 */
	public boolean check(String str) {
		return this.name.equals(str);
	}
	@Override
	public void avance(Stack<ISymbol> stack, Terminal target, Stack<IASTNode> stackAST) throws SyntaxException {
		if (target ==this) {
			System.out.println("Pop "+name+" beacause find in source");
			stack.pop();
		}

	}
}
