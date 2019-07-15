package fr.raoux.STCompiler.parser.symbols;

import java.util.HashSet;
import java.util.Set;

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

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

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

	public boolean isNullable() {
		return false;
	}

	public Set<Terminal> getPremier(){
		Set<Terminal> res = new HashSet<Terminal>();
		res.add(this);
		return res;
	}
	public Set<Terminal> getSuivant() {
		Set<Terminal> res = new HashSet<Terminal>();
		res.add(this);
		return res;
	}
	public void isInner(Rule rule) {

	}
}
