package fr.raoux.STCompiler.parser.symbols;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import fr.raoux.STCompiler.parser.Exception.SyntaxeException;

public class NonTerminal implements ISymbol {

	private static int TID;
	private int id;
	private String name;
	private String value;
	private List<Rule> rules;

	private boolean nullable;
	private boolean nullableSet;

	private Set<Terminal> premiers;
	private Set<NonTerminal> premierNonTerminals;
	private boolean premierSet = false;

	private Set<Terminal> suivants;
	private boolean suivantSet;

	private Set<Rule> inners;

	public NonTerminal(String name) {
		this(name,name,new ArrayList<Rule>());
	}

	public NonTerminal(String name, List<Rule> rules) {
		this(name,name,rules);
	}

	public NonTerminal(String name, String value, List<Rule> rules) {
		this.id = TID++;
		this.name = name;
		this.value = value;
		this.rules = rules;
		this.premierSet = false;
		this.premiers = new HashSet<Terminal>();
		this.premierNonTerminals = new HashSet<NonTerminal>();
		this.suivants = new HashSet<Terminal>();
		this.suivants.add(EOFTerminal.getInstance());
		this.inners = new HashSet<Rule>();
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
	public List<Rule> getRules() {
		return rules;
	}
	public void addRule(Rule rule) {
		this.rules.add(rule);
	}
	
	@Override
	public boolean isNullable() {
		if (nullableSet){
			return this.nullable;
		}else {
			this.nullableSet = true;
			for (Rule rule: rules) {
				if(rule.isNullable()) {
					this.nullable = true;
					break;
				}
			}
		}
		return this.nullable;
	}
	
	@Override
	public Set<Terminal> getSuivant() {
		if(!this.suivantSet) {
			this.suivantSet = true;
			for(Rule rule: this.inners) {
				this.suivants.addAll(rule.findSuivant(this));
			}
		}
		return this.suivants;
	}

	@Override
	public Set<Terminal> getPremier() {
		if (!this.premierSet) {
			this.premiers = new HashSet<Terminal>();
			this.premierSet = true;
			for(Rule rule:rules) {
				this.premiers.addAll(rule.getPremier());
				this.premierNonTerminals.addAll(rule.getPremierNonterminal());
			}
		}
		return this.premiers;
	}
	@Override
	public void isInner(Rule rule) {
		this.inners.add(rule);
	}
	
	public void addToStack(Stack stack, Rule rule) {
		for (int i= rule.getSymbols().size()-1; i >= 0; i--) {
			System.out.print(rule.getSymbols().get(i).getName()+", ");
			stack.push(rule.getSymbols().get(i));
		}
	}

	public boolean checkRecursivity() {
		return this.premierNonTerminals.contains(this);
	}

	public boolean checkAbiguity() {
		Set<Terminal> first = new HashSet<Terminal>();
		for(Rule rule:rules) {
			for(Terminal t:rule.getPremier()) {
				if(first.contains(t))
					return true;
				first.add(t);
			}
		}
		return false;
	}

	@Override
	public void avance(Stack<ISymbol> stack, Terminal target) throws SyntaxeException{
		System.out.print(this.name+"->");
		for(Rule rule: rules) {
			if(rule.getPremier().contains(target)) {
				addToStack(stack,rule);
			}
		}
		System.out.println();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\nNonTerminal-------------------------\n");
		sb.append("    name: "+this.name);
		sb.append("\n    Nullable: "+(this.isNullable()?"Oui":"Non"));
		sb.append("\n    Left Recursitity: "+(this.checkRecursivity()?"Oui":"Non"));
		sb.append("\n    Ambiguity: "+(this.checkAbiguity()?"Oui":"Non"));
		sb.append("\n    Premier: ");
		for(ISymbol symb: this.getPremier()) sb.append(symb.getName()+", ");
		sb.append("\n    Suivant: ");
		for(ISymbol symb: this.getSuivant()) sb.append(symb.getName()+", ");
		sb.append("\n    Value:\n");
		for(Rule rule:rules){
			sb.append("        "+rule.getLineString()+"\n");
		}
		return sb.toString();
	}
}
