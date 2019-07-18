package fr.raoux.STCompiler.parser.language;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import fr.raoux.STCompiler.parser.exception.SyntaxException;
import fr.raoux.STCompiler.parser.symbols.BreakerTerminal;
import fr.raoux.STCompiler.parser.symbols.DynamicTerminal;
import fr.raoux.STCompiler.parser.symbols.EOFTerminal;
import fr.raoux.STCompiler.parser.symbols.GostTerminal;
import fr.raoux.STCompiler.parser.symbols.ISymbol;
import fr.raoux.STCompiler.parser.symbols.NonTerminal;
import fr.raoux.STCompiler.parser.symbols.Terminal;


public class Language extends LanguageAbstract {

	private String temp;
	public Language(String name) {
		super(name);
	}

	@Override public void nullable() { for (Map.Entry<String, ISymbol> map:symbols.entrySet()) map.getValue().isNullable();}
	@Override public void first()    { for (Map.Entry<String, ISymbol> map:symbols.entrySet()) map.getValue().getPremier();}
	@Override public void follow()   { for (Map.Entry<String, ISymbol> map:symbols.entrySet()) map.getValue().getSuivant();}



	@Override
	public void build() {
		this.nullable();
		this.first();
		this.follow();
		for(Map.Entry<String, BreakerTerminal> map:this.breakerTerminals.entrySet())
			this.breakers.add(map.getValue().getSeparator());
	}


	@Override
	public void info() {
		System.out.println("Language----------------------");
		System.out.print("    Alphabet terminals:\n        ");
		for (Map.Entry<String, Terminal> map:this.terminals.entrySet()) System.out.print(map.getValue().getName()+", ");
		System.out.print("\n    Nonterminals:\n        ");
		for (Map.Entry<String, NonTerminal> map:this.nonTerminals.entrySet()) System.out.print(map.getValue().getName()+", ");
		System.out.println("\n    StartSymbol: "+this.startSymbol.getName());
	}

	public void infoAll(){
		for (Map.Entry<String, Terminal> map:this.terminals.entrySet()) {
			System.out.println(map.getValue());
		}
		for (Map.Entry<String, NonTerminal> map:this.nonTerminals.entrySet()) {
			System.out.println(map.getValue());
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
				break;
			}
			else temp += c;
		}
		// we retry to find the terminal
		return this.getTerminal(temp);
	}

	private Terminal findDynamicterminal() {
		for (Map.Entry<String, DynamicTerminal> map:this.dynamicTerminal.entrySet())
			if (map.getValue().check(temp))
				return map.getValue();
		return null;
	}

	public void outLog(String path) throws IOException {
		FileWriter fr = new FileWriter(new File(path));
		StringBuilder sb = new StringBuilder();
		sb.append("Logging du language");
		sb.append("----Terminal----------------------------------------------------------------------------------");
		for(Map.Entry<String, Terminal> map:this.terminals.entrySet())
			sb.append(map.getValue().toString());
		sb.append("----NonTerminal-------------------------------------------------------------------------------");
		for(Map.Entry<String, NonTerminal> map:this.nonTerminals.entrySet())
			sb.append(map.getValue().toString());
		fr.write(sb.toString());
		fr.close();
	}

	public String getLog() {
		StringBuilder sb = new StringBuilder();

		return sb.toString();
	}
}
