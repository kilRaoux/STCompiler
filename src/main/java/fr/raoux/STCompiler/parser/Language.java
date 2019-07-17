package fr.raoux.STCompiler.parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.raoux.STCompiler.parser.Exception.LanguageException;
import fr.raoux.STCompiler.parser.Exception.SyntaxException;
import fr.raoux.STCompiler.parser.symbols.BreakerTerminal;
import fr.raoux.STCompiler.parser.symbols.DynamicTerminal;
import fr.raoux.STCompiler.parser.symbols.EOFTerminal;
import fr.raoux.STCompiler.parser.symbols.GostTerminal;
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
	private String temp;
	private int maxSizeToken = 32;

	public Language() {
	}

	public Terminal getTerminal(String name) {
		Terminal res = null;
		for (Terminal symb: this.terminals) {
			if (symb.getValue().equals(name)) {
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
			if (symb.getValue().equals(str)) {
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

	public Terminal avance(SourceReader src) throws SyntaxException, IOException {
		char c = src.nextChar();
		this.temp = ""+c;
		Terminal res = this.getTerminal(temp);
		if(c=='\0') return EOFTerminal.getInstance();
		if(res instanceof GostTerminal) return avance(src);
		if(res == null) res = this.findStaticTerminal(src);
		if(res == null) res = this.findDynamicterminal();
		if(res == null) throw new SyntaxException("<"+temp+">("+(int)temp.charAt(0)+")can't correctly be parse");
		return res;
	}

	private Terminal findStaticTerminal(SourceReader src) throws IOException {
		char c;
		while (( c = src.nextChar())!='\0') {
			// If c is a breaker terminal we break.
			if(this.breakers.contains(c)) {
				src.before();
				break;
			}
			else temp += c;
		}
		// we retry to find the terminal
		return this.getTerminal(temp);
	}

	private Terminal findDynamicterminal() {
		for (DynamicTerminal t: this.dynamicTerminal) {
			if (t.check(temp)) {
				return t;
			}
		}
		return null;
	}

	public void outputLog(String path) throws IOException {
		FileWriter fr = new FileWriter(new File(path));
		StringBuilder sb = new StringBuilder();
		sb.append("Logging du language");
		sb.append("----Terminal----------------------------------------------------------------------------------");
		for(Terminal symb: terminals) {
			sb.append(symb.toString());
		}
		sb.append("----NonTerminal-------------------------------------------------------------------------------");
		for(NonTerminal symb: nonTerminals) {
			sb.append(symb.toString());
		}
		fr.write(sb.toString());
		fr.close();
	}
}
