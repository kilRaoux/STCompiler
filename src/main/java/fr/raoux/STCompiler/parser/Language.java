package fr.raoux.STCompiler.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.raoux.STCompiler.parser.Exception.LanguageException;
import fr.raoux.STCompiler.parser.symbols.BreakerTerminal;
import fr.raoux.STCompiler.parser.symbols.DynamicTerminal;
import fr.raoux.STCompiler.parser.symbols.EOFTerminal;
import fr.raoux.STCompiler.parser.symbols.ISymbol;
import fr.raoux.STCompiler.parser.symbols.NonTerminal;
import fr.raoux.STCompiler.parser.symbols.Rule;
import fr.raoux.STCompiler.parser.symbols.Terminal;


public class Language {
	private Set<ISymbol> symbols = new HashSet<ISymbol>();
	private Set<Terminal> terminals = new HashSet<Terminal>();
	private Set<DynamicTerminal> dynamicTerminal = new HashSet<>();
	private Set<BreakerTerminal> breakerTerminals = new HashSet<BreakerTerminal>();
	private Set<NonTerminal> nonTerminals = new HashSet<NonTerminal>();
	private NonTerminal startSymbol;
	private List<Character> breakers = new ArrayList<Character>();

	private Set<Rule> rules = new HashSet<Rule>();
	private int maxSizeToken = 32;

	public Language() {
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
	public void addTerminal(BreakerTerminal terminal) {
		this.breakerTerminals.add(terminal);
		this.terminals.add(terminal);
		this.symbols.add(terminal);
	}
	public void addTerminal(DynamicTerminal terminal) {
		this.terminals.add(terminal);
		this.symbols.add(terminal);
		this.dynamicTerminal.add(terminal);
	}
	public void addNonTerminal(NonTerminal nonTerminal) {
		this.nonTerminals.add(nonTerminal);
		this.symbols.add(nonTerminal);
	}
	public void addRule(Rule rule) {
		this.rules.add(rule);
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


	public void build(String startSymbol) {
		this.startSymbol = this.getNonTerminal(startSymbol);
		this.nullable();
		this.premier();
		this.suivant();
		for(BreakerTerminal b:this.breakerTerminals) {
			this.breakers.add(b.getSeparator());
		}
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
			if( nt.checkRecursivity())
				return true;
		}
		return false;
	}
	public void info() {
		System.out.println("Language----------------------");
		System.out.print("    Alphabet terminals:\n        ");
		for (Terminal t:terminals) System.out.print(t.getName()+", ");
		System.out.print("\n    Nonterminals:\n        ");
		for (NonTerminal t:nonTerminals) System.out.print(t.getName()+", ");
		System.out.println("\n    StartSymbol: "+this.startSymbol.getName());
	}

	public void infoAll(){
		for (ISymbol symb:this.terminals) {
			System.out.println(symb);
		}
		for (ISymbol symb:this.nonTerminals) {
			System.out.println(symb);
		}
	}

	public Terminal avance(SourceReader src) {
		char c = src.nextChar();
		// We add the next char of source to string.
		String temp = ""+c;
		// We add try to find one terminal with.
		Terminal res = this.getTerminal(temp);
		if (c=='\0') res = EOFTerminal.getInstance();
		// If it's not done
		else if(res == null) {
			// while the source contain char
			while (( c = src.nextChar())!='\0') {
				// If c is a breaker terminal we break.
				if(this.breakers.contains(c)) {
					src.before();
					break; 
				}
				// Else we add to string
				else temp += c;
			}
			// we retry to find the terminal
			res = this.getTerminal(temp);
			// If isn't may be it's a dynamic terminal.
			if (res == null) {
				// we try every dynamic terminal
				for (DynamicTerminal t: this.dynamicTerminal) {
					if (t.check(temp)) {
						res = t;
						break;
					}
				}
			}
			
		}
		//if (res == null) res = "EOF";
		//System.out.println("temp:"+temp);
		return res;
	}
}
