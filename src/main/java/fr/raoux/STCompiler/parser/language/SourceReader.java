package fr.raoux.STCompiler.parser.language;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SourceReader {
	private static SourceReader INSTANCE = new SourceReader();
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
	}

	public char nextChar() throws IOException {
		int intch = br.read();
		if (intch == -1) return '\0';
		this.column++;
		char res = (char) intch;
		if (res=='\n') {
			this.row++;
			this.column=0;
			res = nextChar();
		}
		return res;
	}

	public String atLine() {
		return "at line: "+row+" and column: "+column;
	}
}
