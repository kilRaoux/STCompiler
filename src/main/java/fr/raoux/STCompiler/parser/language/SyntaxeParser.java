package fr.raoux.STCompiler.parser.language;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import fr.raoux.STCompiler.parser.exception.SyntaxException;

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
	private String startSymbol = "S";
	private LanguageConfig config = new LanguageConfig();

	public SyntaxeParser() {
		// TODO Auto-generated constructor stub
	}

	public Language parse(String src) throws IOException, SyntaxException {
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
		return this.config.generate();
	}

	private void parseLine(String line) throws SyntaxException {
		if (this.syntaxeStatus.equals(syntaxeStatus.CONFIG)) {
			this.parseConfiguration(line);
		}
		else if (this.syntaxeStatus.equals(syntaxeStatus.RULES)) {
			this.config.rules.add(line);
		}
		else if (this.syntaxeStatus.equals(syntaxeStatus.TERMINAL)) {
			this.parseTerminals(line);
		}
		else if (this.syntaxeStatus.equals(syntaxeStatus.NONTERMINAL)) {
			this.config.nonTerminals.add(line);
		}
	}

	private void parseConfiguration(String line) {
		String[] confl = line.split(":");
		switch(confl[0]) {
		case "startSymbol":
			this.config.startSymbol = confl[1];
			break;
		case "emptySymbol":
			this.config.emptySymbol = confl[1];
			break;
		case "spaceSymbol":
			this.config.spaceSymbol = confl[1];
			break;
		case "tabSymbol":
			this.config.tabSymbol = confl[1];
			break;
		case "returnSymbol":
			String[] strs = confl[1].split(" ");
			this.config.returnSymbol10 = strs[0];
			this.config.returnSymbol13 = strs[1];
			break;
		case "name":
			this.config.name = confl[1];
			break;
		default:
			System.out.println("the configuration <"+confl[0]+"> does'nt existe!");
		}
	}

	private void parseTerminals(String line) {
		String[] com = line.split(":");
		switch(com[0]) {
		case "static":
			this.config.staticTerminal.add(com[1]);
			break;
		case "breaks":
			this.config.breaksTerminal.add(com[1]);
			break;
		case "dynamic":
			this.config.dynamicTerminal.add(com[1]);
			break;
		}
	}
}
