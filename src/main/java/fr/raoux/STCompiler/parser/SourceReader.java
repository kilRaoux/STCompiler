package fr.raoux.STCompiler.parser;

import java.io.BufferedReader;
import java.io.File;
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

	public char nextChar() throws IOException {
		char res = '\0';
		if (line==null)  res = '\0';
		else if(this.column < this.line.length()) {
			res = this.line.charAt(this.column);
			this.column ++;
		}else {
			if (nextLine()) return '\0';
			else return nextChar();
		}/*
		if(res == ' ') return nextChar();
		if(res == '\n') return nextChar();
		if(res == '\t') return nextChar();*/

		return res;
	}

	private boolean nextLine() throws IOException {
		this.line = br.readLine();
		this.row++;
		this.column = 0;
		if (line!=null && line.isEmpty()) return nextLine();
		else return line==null;
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
		if (line != null) return this.line.substring(column);
		else return "";
	}

	public void affAllSrouce() throws IOException {
		while ((line=br.readLine())!=null) {
			System.out.println(line);
		}
	}

	public String atLine() {
		return "at line: "+row+" and column: "+column;
	}
}
