package fr.raoux.STCompiler.parser;

import java.util.HashSet;
import java.util.Set;

import fr.raoux.STCompiler.parser.Exception.LanguageException;
import fr.raoux.STCompiler.parser.symbols.EmptyTerminal;
import fr.raoux.STCompiler.parser.symbols.ISymbol;
import fr.raoux.STCompiler.parser.symbols.NonTerminal;
import fr.raoux.STCompiler.parser.symbols.Rule;
import fr.raoux.STCompiler.parser.symbols.Terminal;


public class Language {
	private Set<ISymbol> symbols;
	private Set<Terminal> terminals;
	private Set<NonTerminal> nonTerminals;
	private NonTerminal startSymbol;

	private Set<Rule> rules;

	public Language() {
		this.symbols = new HashSet<ISymbol>();
		this.terminals = new HashSet<Terminal>();
		this.nonTerminals = new HashSet<NonTerminal>();
		this.rules = new HashSet<Rule>();
		this.addTerminal(new EmptyTerminal());
	}
	public NonTerminal getStartSymbol() {
		return startSymbol;
	}
	public Set<ISymbol> getSymbols() {
		return symbols;
	}
	public Set<Terminal> getTerminals() {
		return terminals;
	}
	public Set<NonTerminal> getNonTerminals() {
		return nonTerminals;
	}
	public void addTerminal(Terminal terminal) {
		this.terminals.add(terminal);
		this.symbols.add(terminal);
	}
	public void addNonTerminal(NonTerminal nonTerminal) {
		this.nonTerminals.add(nonTerminal);
		this.symbols.add(nonTerminal);
	}

	public Terminal getTerminal(String name) {
		Terminal res = null;
		for (Terminal symb: this.terminals) {
			if (symb.getName().equals(name)) {
				res = symb;
				break;
			}
		}
		return res;
	}
	public NonTerminal getNonTerminal(String name) {
		NonTerminal res = null;
		for (NonTerminal symb: this.nonTerminals) {
			if (symb.getName().equals(name)) {
				res = symb;
				break;
			}
		}
		return res;
	}
	public ISymbol getSymbol(String str) {
		ISymbol symbol = null;
		for(ISymbol symb: this.symbols) {
			if (symb.getName().equals(str)) {
				symbol = symb;
				break;
			}
		}
		if(symbol==null) {
			try {
				throw new LanguageException("Error: "+str+" n'est pas definie en tant que symbol");
			} catch (LanguageException e) {
				e.printStackTrace();
			}
		}
		return symbol;
	}
	public void info() {
		for (ISymbol symb:this.terminals) {
			System.out.println(symb);
		}
		for (ISymbol symb:this.nonTerminals) {
			System.out.println(symb);
		}
		System.out.println("Le Language "+(this.checkLeftRecursivity()?"est":"n'est pas")+" recursif a gauche!");
	}

	public void parseAlphabet(String rawAalphabet) {
		String[] alphabet = rawAalphabet.split(" ");
		for (String letter: alphabet) {
			this.addTerminal(new Terminal(letter));
		}
	}

	public void parseNonTerminal(String rawNonTerminals) {
		String[] alphabet = rawNonTerminals.split(" ");
		for (String letter: alphabet) {
			this.addNonTerminal(new NonTerminal(letter));
		}
	}

	public void parseRule(String nt, String...rawRule) {
		NonTerminal nT = this.getNonTerminal(nt);
		Rule rule = new Rule(nT);
		nT.addRule(rule);
		for(String str: rawRule) {
			ISymbol symb = this.getSymbol(str);
			symb.isInner(rule);
			rule.add(symb);
		}
		this.rules.add(rule);
	}

	public void parseRules(String rawRules) {
		String[] rules = rawRules.split("\n");
		for(String rule: rules) {
			String[] rawSymb = rule.split(" ");
			NonTerminal nt = this.getNonTerminal(rawSymb[0]);
			Rule r = new Rule(nt);
			for(int i = 1; i< rawSymb.length; i++) {
				ISymbol symb = this.getSymbol(rawSymb[i]);
				symb.isInner(r);
				r.add(symb);
			}
			nt.addRule(r);
			this.rules.add(r);
		}
	}
	public void build(String startSymbol) {
		this.startSymbol = this.getNonTerminal(startSymbol);
		this.nullable();
		this.premier();
		this.suivant();
	}
	private void nullable() {
		for (ISymbol symb:symbols) {
			symb.isNullable();
		}
	}
	private void premier() {
		for (ISymbol symb:symbols) {
			symb.getPremier();
		}
	}
	private void suivant() {
		for (ISymbol symb:symbols) {
			symb.getSuivant();
		}
	}
	private boolean checkLeftRecursivity() {
		for (NonTerminal nt: this.nonTerminals) {
			if( nt.checckrecursivity())
				return true;
		}
		return false;
	}

}
