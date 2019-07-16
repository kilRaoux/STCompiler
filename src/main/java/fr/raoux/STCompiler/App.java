package fr.raoux.STCompiler;

import java.io.IOException;

import fr.raoux.STCompiler.parser.Language;
import fr.raoux.STCompiler.parser.ParserDescent;
import fr.raoux.STCompiler.parser.SourceReader;
import fr.raoux.STCompiler.parser.SyntaxeParser;
import fr.raoux.STCompiler.parser.Exception.LanguageException;
import fr.raoux.STCompiler.parser.Exception.SyntaxeException;

/**
 * Hello world!
 *
 */
public class App
{
	public static void main( String[] args ) throws IOException, SyntaxeException, LanguageException
	{
		char c = '.';
		String temp = ""+c;
		System.out.println(temp==".");
		SyntaxeParser stxp = new SyntaxeParser();
		Language lang = stxp.parse("ressources/syntax.stx");
		//lang.infoAll();
		ParserDescent parser = new ParserDescent(lang);
		parser.run(new SourceReader("0=0-06*78+5"));

	}
}
