package fr.raoux.STCompiler;

import java.io.IOException;

import fr.raoux.STCompiler.parser.Language;
import fr.raoux.STCompiler.parser.SyntaxeParser;

/**
 * Hello world!
 *
 */
public class App
{
	public static void main( String[] args ) throws IOException
	{
		SyntaxeParser stxp = new SyntaxeParser();

		Language lang = stxp.parse("ressources/syntax.stx");
		lang.infoAll();
	}
}
