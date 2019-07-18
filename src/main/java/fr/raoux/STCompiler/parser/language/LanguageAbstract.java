package fr.raoux.STCompiler.parser.language;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.raoux.STCompiler.parser.symbols.BreakerTerminal;
import fr.raoux.STCompiler.parser.symbols.DynamicTerminal;
import fr.raoux.STCompiler.parser.symbols.EmptyTerminal;
import fr.raoux.STCompiler.parser.symbols.ISymbol;
import fr.raoux.STCompiler.parser.symbols.NonTerminal;
import fr.raoux.STCompiler.parser.symbols.Rule;
import fr.raoux.STCompiler.parser.symbols.Terminal;

/**
 * Abstract function for define basis of Language Object.
 * @author utilisateur2
 *
 */
public abstract class LanguageAbstract {

	protected String name;
	protected String autors;
	protected NonTerminal startSymbol;

	protected Map<String, ISymbol> symbols                 = new HashMap<>();
	protected Map<String,Terminal> terminals               = new HashMap<>();
	protected Map<String,DynamicTerminal> dynamicTerminal  = new HashMap<>();
	protected Map<String,BreakerTerminal> breakerTerminals = new HashMap<>();
	protected Map<String,NonTerminal> nonTerminals         = new HashMap<>();
	protected Set<Rule> rules                              = new HashSet<>();
	protected List<Character> breakers                     = new ArrayList<Character>();

	//Constructor
	public LanguageAbstract(String name) {this.name = name;}

	// Getters
	public String          getName       ()            {return name;}
	public String          getAutors     ()            {return autors;}
	public NonTerminal     getStartSymbol()            {return startSymbol;}
	public List<Character> getBreakers   ()            {return breakers;}
	public Set<Rule>       getRules      ()            {return rules;}
	public Terminal        getTerminal   (String name) {return this.terminals.get(name);}
	public NonTerminal     getNonTerminal(String name) {return this.nonTerminals.get(name);}
	public ISymbol         getSymbol     (String name) {return this.symbols.get(name);}

	//Setters
	public void setName       (String name)             {this.name = name;}
	public void setAutors     (String autors)           {this.autors = autors;}
	public void setStartSymbol(NonTerminal startSymbol) {this.startSymbol = startSymbol;}
	public void setBreakers   (List<Character> breakers){this.breakers = breakers;}
	public void setRules      (Set<Rule> rules)         {this.rules = rules;}

	/**
	 * Define the start symbol for this language.
	 *   if inst not in the non-terminals symbols, it's add automatically.
	 * @param name
	 */
	public void setStartSymbol(String name) {
		NonTerminal snt = getNonTerminal(name);
		if (snt!=null) this.setStartSymbol(snt);
		else {
			NonTerminal nt = new NonTerminal(name);
			this.startSymbol = nt;
			this.addNonTerminal(nt);
		}
	}

	//Adders
	public void addTerminal(String name) {
		this.addTerminal(new Terminal(name));
	}
	public void addEmptySymbol(String name) { this.addTerminal(new EmptyTerminal(name));}
	public void addTerminal(Terminal terminal) {
		this.terminals.put(terminal.getValue(),terminal);
		this.symbols.put(terminal.getValue(),terminal);
	}
	public void addTerminal(BreakerTerminal terminal) {
		this.breakerTerminals.put(terminal.getValue(),terminal);
		this.addTerminal((Terminal)terminal);
	}
	public void addTerminal(DynamicTerminal terminal) {
		this.dynamicTerminal.put(terminal.getValue(),terminal);
		this.addTerminal((Terminal)terminal);
	}
	public void addNonTerminal(NonTerminal nonTerminal) {
		this.nonTerminals.put(nonTerminal.getValue(),nonTerminal);
		this.symbols.put(nonTerminal.getValue(),nonTerminal);
	}
	public void addRule(Rule rule) {
		this.rules.add(rule);
	}

	//Language pure abstract functions
	public abstract void nullable();
	public abstract void follow();
	public abstract void first();
	public abstract void info();
	public abstract void build();

}
