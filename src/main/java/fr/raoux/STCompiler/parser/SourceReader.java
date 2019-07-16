package fr.raoux.STCompiler.parser;

public class SourceReader {
	private String source;
	private int index = 0;
	public SourceReader(String src) {
		this.source = src;
	}

	public char nextChar() {
		char res = '\0';
		if(this.index < this.source.length()) {
			res = this.source.charAt(this.index);
			this.index ++;
		}
		if(res == ' ') return nextChar();
		if(res == '\n') return nextChar();
		if(res == '\t') return nextChar();
		
		return res;
	}

	public int size() {
		return this.source.length();
	}

	public boolean canContinue() {
		return index < size();
	}

	public void before() {
		this.index--;
	}
	
	public String getNextSource() {
		return this.source.substring(index);
	}
}
