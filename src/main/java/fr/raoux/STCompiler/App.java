package fr.raoux.STCompiler;

import java.io.IOException;

import fr.raoux.STCompiler.parser.exception.LanguageException;
import fr.raoux.STCompiler.parser.exception.SyntaxException;
import fr.raoux.STCompiler.parser.language.Language;
import fr.raoux.STCompiler.parser.language.SyntaxeParser;
import fr.raoux.STCompiler.parser.parser.ParserDescent;

/**
 * Hello world!
 *
 */
public class App
{
	private static void example1() throws IOException, SyntaxException {
		SyntaxeParser stxp = new SyntaxeParser();
		Language lang = stxp.parse("ressources/syntax.stx");
		lang.outLog("ressources/log.txt");
		ParserDescent parser = new ParserDescent(lang);
		parser.run("ressources/source.stx").print();
	}

	public static void main( String[] args ) throws IOException, SyntaxException, LanguageException
	{
		example1();
	}
}
