package fr.raoux.STCompiler.parser;

import fr.raoux.STCompiler.ast.AST;
import fr.raoux.STCompiler.parser.Exception.LanguageException;
import fr.raoux.STCompiler.parser.Exception.SyntaxeException;
import fr.raoux.STCompiler.parser.symbols.EOFTerminal;
import fr.raoux.STCompiler.parser.symbols.ISymbol;
import fr.raoux.STCompiler.parser.symbols.Terminal;

/**
 *  Top to Down Descending Parser.
 *  Use to parse source code with Language to create Abstract Syntax Tree.
 * @author raoux
 *
 */
public class ParserDescent extends ParserAbstract {

	private Terminal target;
	private SourceReader sr;
	public ParserDescent(Language lang) {
		super(lang);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void build() {
		// TODO Auto-generated method stub

	}

	/**
	 * Function to generate the AST with one source code.
	 */
	@Override
	public AST run(SourceReader src) throws SyntaxeException, LanguageException {
		// Init Stack/target/src
		this.stack.add(EOFTerminal.getInstance());
		this.stack.add(lang.getStartSymbol());
		target = this.lang.avance(src);
		sr = src;
		// parse
		while(this.next(src));
		return new AST();
	}

	private boolean next(SourceReader src) throws SyntaxeException, LanguageException {
		System.out.println("TARGET "+target.getName()+" STACKTOP "+ stack.peek().getName());
		System.out.println("    P::"+printStack());
		System.out.println("    S::"+src.getNextSource());
		if( target.equals(EOFTerminal.getInstance()) && stack.peek() == EOFTerminal.getInstance())
			return false;
		else if(stack.peek() instanceof Terminal)
			this.avance();
		else if(stack.peek().isNullable() && stack.peek().getSuivant().contains(target)) {
			this.remove();
		}else if(stack.peek().getPremier().contains(target)) {
			this.replace();
		}else {
			throw new LanguageException("TARGET "+target.getName()+" STACK "+ stack.peek().getName());
		}
		return true;

	}

	private void avance() {
		System.out.println("Avance :"+target.getName());
		stack.pop();
		this.target = this.lang.avance(this.sr);
	}

	private void replace() throws SyntaxeException {
		System.out.print("Replace:");
		stack.pop().avance(stack, target);
	}

	private void remove() {
		System.out.println("Remove :"+printStack());
		stack.pop();
	}

	private String printStack() {
		StringBuilder sb = new StringBuilder();
		for(ISymbol t: stack) {
			sb.append(t.getName()+", ");
		}
		return sb.toString();
	}
}
