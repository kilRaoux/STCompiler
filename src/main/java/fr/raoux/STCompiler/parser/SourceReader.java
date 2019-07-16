package fr.raoux.STCompiler.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SourceReader {
	private static SourceReader INSTANCE = new SourceReader();
	private String line = "";
	private String path;
	private BufferedReader br;
	private int column = 0;
	private int row = 0;
	private SourceReader() {
	}
	
	public static SourceReader getInstance() {
		return INSTANCE;
	}
	
	public void setPath(String path) throws IOException {
		this.path = path;
		File file = new File(path);
		br = new BufferedReader(new FileReader(file));
		line = br.readLine();
	}
	
	public char nextChar() {
		char res = '\0';
		if(this.column < this.line.length()) {
			res = this.line.charAt(this.column);
			this.column ++;
		}else {
			try {
				line = br.readLine();
				row++;
				column=0;
				if(line==null) {
					line = "\0";
					return '\0';
				}
			} catch (IOException e) {
				
			}
		}
		if(res == ' ') return nextChar();
		if(res == '\n') return nextChar();
		if(res == '\t') return nextChar();
		
		return res;
	}

	public int size() {
		return this.line.length();
	}

	public boolean canContinue() {
		return column < size();
	}

	public void before() {
		this.column--;
	}
	
	public String getNextSource() {
		return this.line.substring(column);
	}
}
