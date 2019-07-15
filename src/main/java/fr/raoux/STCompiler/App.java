package fr.raoux.STCompiler;

import fr.raoux.STCompiler.parser.Language;
import fr.raoux.STCompiler.parser.ParserAbstract;
import fr.raoux.STCompiler.parser.ParserDescent;

/**
 * Hello world!
 *
 */
public class App
{
	public static void main( String[] args )
	{
		Language l = new Language();
		l.parseAlphabet("var + - * / % ( )");
		l.parseNonTerminal("S exp expadd expmul expl");
		l.parseRules(
				"S exp \n"+
						"exp expadd \n"+
						"exp expl \n"+
						"expadd + exp\n"+
						"expadd - exp\n"+
						"expadd expmul \n"+
						"expmul * exp\n"+
						"expmul / exp\n"+
						"expl <empty>\n" +
				"expl var");
		l.build("S");
		//l.info();
		ParserAbstract parser = new ParserDescent(l);
		parser.run();
	}
}
