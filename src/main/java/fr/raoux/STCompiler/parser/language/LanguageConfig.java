package fr.raoux.STCompiler.parser.language;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.raoux.STCompiler.parser.symbols.BreakerTerminal;
import fr.raoux.STCompiler.parser.symbols.DynamicTerminal;
import fr.raoux.STCompiler.parser.symbols.EOFTerminal;
import fr.raoux.STCompiler.parser.symbols.GostTerminal;
import fr.raoux.STCompiler.parser.symbols.ISymbol;
import fr.raoux.STCompiler.parser.symbols.NonTerminal;
import fr.raoux.STCompiler.parser.symbols.Rule;
import fr.raoux.STCompiler.parser.symbols.Terminal;

class LanguageConfig {
	public String name = "undefine";
	public String autors = "undefine";
	public String startSymbol  = "S";
	public String emptySymbol  = "<empty>";
	public String spaceSymbol  = "<space>";
	public String tabSymbol    = "<tab>";
	public String returnSymbol10 = "<n>";
	public String returnSymbol13 = "<r>";
	public List<String> staticTerminal  = new ArrayList<>();
	public List<String> breaksTerminal  = new ArrayList<>();
	public List<String> dynamicTerminal = new ArrayList<>();
	public List<String> nonTerminals    = new ArrayList<>();
	public List<String> rules           = new ArrayList<>();

	public Language generate() throws IOException {
		Language lang = new Language(this.name);

		lang.setAutors(autors);
		lang.setStartSymbol(startSymbol);
		lang.addEmptySymbol(this.emptySymbol);
		lang.addTerminal(new GostTerminal(this.returnSymbol10, '\n'));
		lang.addTerminal(new GostTerminal(this.returnSymbol13, '\r'));
		lang.addTerminal(new GostTerminal(this.tabSymbol, '\t'));
		lang.addTerminal(new GostTerminal(this.spaceSymbol, ' '));
		lang.addTerminal(EOFTerminal.getInstance());

		for(String line:this.staticTerminal)
			for (String name: line.split(" ")) lang.addTerminal(new Terminal(name));

		for(String line:this.breaksTerminal)
			for (String name: line.split(" ")) lang.addTerminal(new BreakerTerminal(name));

		for(String line:this.dynamicTerminal) {
			String[] sub = line.split(" ");
			lang.addTerminal(new DynamicTerminal(sub[0], sub[1]));
		}

		for(String line:this.nonTerminals)
			for (String name: line.split(" ")) lang.addNonTerminal(new NonTerminal(name));

		for (String line:this.rules) parseRule(lang, line);

		lang.build();
		lang.outLog("ressources/log.txt");
		return lang;
	}

	public void parseRule(Language lang, String line) {
		String[] sub = line.split(" ");
		NonTerminal nt = lang.getNonTerminal(sub[0]);
		Rule rule = new Rule(nt);
		for (int i=1; i< sub.length; i++) {
			ISymbol symb = lang.getSymbol(sub[i]);
			if (symb != null) rule.add(symb);
		}
		nt.addRule(rule);
		lang.addRule(rule);
	}
}
