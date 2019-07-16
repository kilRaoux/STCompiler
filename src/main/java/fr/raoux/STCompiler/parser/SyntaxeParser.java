package fr.raoux.STCompiler.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import fr.raoux.STCompiler.parser.symbols.ISymbol;
import fr.raoux.STCompiler.parser.symbols.NonTerminal;
import fr.raoux.STCompiler.parser.symbols.Rule;
import fr.raoux.STCompiler.parser.symbols.Terminal;

enum SyntaxStatus{
	CONFIG, DEFINITION, TERMINAL, NONTERMINAL, RULES, NONE,
}
/**
 *  Parse une syntaxe depuis un fichier et la transforme en Language.
 * @author utilisateur2
 *
 */
public class SyntaxeParser {
	private SyntaxStatus syntaxeStatus = SyntaxStatus.NONE;
	private Language lang = new Language();
	private String startSymbol = "S";

	public SyntaxeParser() {
		// TODO Auto-generated constructor stub
	}

	public Language parse(String src) throws IOException {
		System.out.println("Debut de lecture...");
		File file = new File(src);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while((line=br.readLine())!=null) {
			line = line.replace('\t', ' ');
			line = line.trim();
			if (line.equals("configurations:")) {
				this.syntaxeStatus = SyntaxStatus.CONFIG;
			}else if(line.equals("terminals:")) {
				this.syntaxeStatus = syntaxeStatus.TERMINAL;
			}else if(line.equals("nonterminals:")) {
				this.syntaxeStatus = SyntaxStatus.NONTERMINAL;
			}else if(line.equals("rules:")) {
				this.syntaxeStatus = syntaxeStatus.RULES;
			}else if(line.equals("def:")) {
				this.syntaxeStatus = syntaxeStatus.DEFINITION;
			}else if(line.equals("comments:")) {
				this.syntaxeStatus = syntaxeStatus.NONE;
			}else if(!line.isEmpty()) {
				this.parseLine(line);
			}
		}
		this.lang.build(this.startSymbol);
		return this.lang;
	}

	private void parseLine(String line) {
		if (this.syntaxeStatus.equals(syntaxeStatus.CONFIG)) {
			this.parseConfiguration(line);
		}
		else if (this.syntaxeStatus.equals(syntaxeStatus.RULES)) {
			this.parseRule(line);
		}
		else if (this.syntaxeStatus.equals(syntaxeStatus.TERMINAL)) {
			this.parseTerminals(line);
		}
		else if (this.syntaxeStatus.equals(syntaxeStatus.NONTERMINAL)) {
			this.parseNonTerminals(line);
		}
	}

	private void parseConfiguration(String line) {
		String[] confl = line.split(":");
		if (confl[0].equals("startSymbol")) {
			this.startSymbol = confl[1];
		}
	}

	private void parseRule(String line) {
		String[] rawSymb = line.split(" ");
		NonTerminal nt = lang.getNonTerminal(rawSymb[0]);
		Rule r = new Rule(nt);
		for(int i = 1; i< rawSymb.length; i++) {
			ISymbol symb = lang.getSymbol(rawSymb[i]);
			symb.isInner(r);
			r.add(symb);
		}
		nt.addRule(r);
		lang.addRule(r);
	}

	private void parseTerminals(String line) {
		String[] alphabet = line.split(" ");
		for (String letter: alphabet) {
			this.lang.addTerminal(new Terminal(letter));
		}
	}

	private void parseNonTerminals(String line) {
		String[] alphabet = line.split(" ");
		for (String letter: alphabet) {
			this.lang.addNonTerminal(new NonTerminal(letter));
		}
	}

}