package fr.raoux.STCompiler.parser.symbols;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Rule {

	private NonTerminal master;
	private List<ISymbol> symbols;
	public List<ISymbol> getSymbols() {
		return symbols;
	}

	private boolean nullable;
	private boolean nullableSet;

	private boolean premierSet;
	private Set<Terminal> premiers;
	private Set<NonTerminal> premierNonterminal;

	public Set<NonTerminal> getPremierNonterminal() {
		return premierNonterminal;
	}
	public Rule(NonTerminal master) {
		this.master = master;
		nullable = true;
		nullableSet = false;
		this.symbols = new ArrayList<ISymbol>();
		this.premiers = new HashSet<Terminal>();
		this.premierNonterminal = new HashSet<NonTerminal>();
	}
	private boolean subPremier(int index){
		if (index >= this.symbols.size()) {
			return false;
		}else {
			ISymbol symb = this.symbols.get(index);
			if(symb instanceof NonTerminal) this.premierNonterminal.add((NonTerminal) symb);
			this.premiers.addAll(symb.getPremier());
			if (symb.isNullable()) {
				this.subPremier(index+1);
			}
			return true;

		}
	}
	public Set<Terminal> getPremier() {
		if (!this.premierSet) {
			this.premierSet = true;
			this.subPremier(0);
		}
		return this.premiers;
	}


	public String getLineString() {
		StringBuilder sb = new StringBuilder();
		for (ISymbol symbol:symbols) {
			sb.append(symbol.getName());
		}
		sb.append(this.isNullable()?"    (nullable)":"    (not nullable)");
		return sb.toString();
	}
	public void add(ISymbol symbol) {
		this.symbols.add(symbol);
	}

	public boolean isNullable() {
		if (!nullableSet) {
			this.nullableSet = true;
			for(ISymbol symb: symbols) {
				if(!symb.isNullable()) {
					this.nullable = false;
					break;
				}
			}
		}
		return this.nullable;
	}

	public Set<Terminal> findSuivant(ISymbol nt){
		Set<Terminal> res = new HashSet<Terminal>();
		int index = 0;
		boolean toAdd = false;
		while (index<this.symbols.size()-1) {
			index++;
			if(toAdd) {
				res.addAll(this.symbols.get(index).getPremier());
				res.addAll(this.symbols.get(index).getSuivant());
				toAdd = this.symbols.get(index).isNullable();
			}
			if(this.symbols.get(index)==nt) {
				toAdd = true;
			}
		}
		if(this.symbols.get(this.symbols.size()-1) == nt) {
			System.out.println("NAME "+master.getName()+" LAST "+ nt.getName());
			res.addAll(master.getSuivant());
		}
		return res;
	}

	public boolean checkLeftRecursivity() {
		for(ISymbol symb:this.symbols) {
			if(symb==this.master) {
				return true;
			}
			if(!symb.isNullable()) {
				break;
			}
		}
		return false;
	}
}