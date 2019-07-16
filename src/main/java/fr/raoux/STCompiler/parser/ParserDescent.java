package fr.raoux.STCompiler.parser;

import fr.raoux.STCompiler.ast.AST;
import fr.raoux.STCompiler.parser.Exception.LanguageException;
import fr.raoux.STCompiler.parser.Exception.SyntaxeException;
import fr.raoux.STCompiler.parser.symbols.ISymbol;
import fr.raoux.STCompiler.parser.symbols.Terminal;

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

	@Override
	public AST run(SourceReader src) throws SyntaxeException, LanguageException {
		// Init Stack/target/src
		this.stack.add(lang.getStartSymbol());
		target = this.lang.avance(src);
		sr = src;
		// parse
		while(this.next(src));
		return new AST();
	}

	private boolean next(SourceReader src) throws SyntaxeException, LanguageException {
		System.out.println("TARGET "+target.getName()+" STACKTOP "+ stack.peek().getName());
		if(stack.peek() instanceof Terminal)
			this.avance();
		else if(stack.peek().isNullable() && stack.peek().getSuivant().contains(target)) {
			this.remove();
		}else if(stack.peek().getPremier().contains(target)) {
			this.replace();
		}else {
			throw new LanguageException("TARGET "+target.getName()+" STACK "+ printStack());
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
