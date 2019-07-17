package fr.raoux.STCompiler.parser;

import java.io.IOException;
import java.util.Stack;

import fr.raoux.STCompiler.ast.AST;
import fr.raoux.STCompiler.ast.ASTNode;
import fr.raoux.STCompiler.ast.IASTNode;
import fr.raoux.STCompiler.parser.Exception.SyntaxException;
import fr.raoux.STCompiler.parser.symbols.EOFTerminal;
import fr.raoux.STCompiler.parser.symbols.EmptyTerminal;
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
	private AST ast;
	private Stack<IASTNode> stackAST = new Stack<IASTNode>();
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
	 * @throws IOException
	 * @throws
	 */
	@Override
	public AST run(String path) throws SyntaxException, IOException {
		// Init Stack/target/src
		this.ast = new AST("START");
		SourceReader src = SourceReader.getInstance();
		src.setPath(path);
		this.stack.add(EOFTerminal.getInstance());
		this.stack.add(lang.getStartSymbol());
		stackAST.add(new ASTNode("S"));
		this.ast.add(stackAST.peek());
		target = this.lang.avance(src);
		sr = src;
		// parse
		while(this.next(src));
		return ast;
	}

	private boolean next(SourceReader src) throws SyntaxException, IOException {
		if( target.equals(EOFTerminal.getInstance()) && stack.peek() == EOFTerminal.getInstance())
			return false;
		else if (target.equals(EOFTerminal.getInstance())) {
			throw new SyntaxException("The file doesn't finish correctly!");
		}
		else if (stack.peek() instanceof EmptyTerminal)
			this.remove();
		else if(stack.peek() instanceof Terminal)
			this.avance();
		else if(stack.peek().isNullable() && stack.peek().getSuivant().contains(target)) {
			this.remove();
		}else if(stack.peek().getPremier().contains(target)) {
			this.replace();
		}else if(target.equals(EOFTerminal.getInstance())){
			this.target = this.lang.avance(this.sr);
		}else {
			throw new SyntaxException("invalid <"+target.getName()+">("+target.getValue()+") symbol");
		}
		return true;

	}

	private void avance() throws SyntaxException, IOException {
		System.out.println("Avance :"+target.getName());
		stackAST.add(new ASTNode(stack.peek().getName()));
		stack.pop();
		this.target = this.lang.avance(this.sr);
	}

	private void replace() throws SyntaxException {
		System.out.print("Replace:");
		stack.pop().avance(stack, target, stackAST);
	}

	private void remove() {
		System.out.println("Remove :"+stack.peek().getName());
		stack.pop();
		stackAST.pop();
	}

	private String printStack() {
		StringBuilder sb = new StringBuilder();
		for(ISymbol t: stack) {
			sb.append(t.getName()+", ");
		}
		return sb.toString();
	}
}
