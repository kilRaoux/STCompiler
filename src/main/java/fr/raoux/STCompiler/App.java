package fr.raoux.STCompiler;

import java.io.IOException;

import fr.raoux.STCompiler.parser.Language;
import fr.raoux.STCompiler.parser.ParserDescent;
import fr.raoux.STCompiler.parser.SyntaxeParser;
import fr.raoux.STCompiler.parser.Exception.LanguageException;
import fr.raoux.STCompiler.parser.Exception.SyntaxException;

/**
 * Hello world!
 *
 */
public class App
{
	public static void main( String[] args ) throws IOException, SyntaxException, LanguageException
	{
		SyntaxeParser stxp = new SyntaxeParser();
		Language lang = stxp.parse("ressources/syntax.stx");
		lang.outputLog("ressources/log.txt");
		ParserDescent parser = new ParserDescent(lang);
		parser.run("ressources/source.stx").print();

	}
}
