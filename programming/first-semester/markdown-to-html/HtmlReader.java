package md2html;

import java.util.*;
import java.io.*;

public class HtmlReader {
	MyScanner reader;
	boolean tokenIsUsed;
	StringBuilder token;

	public HtmlReader(String arg) {
		try {
			reader = new MyScanner(new File(arg));
		} catch (IOException e) {
			System.out.println("Problems with opening input file");
		}
		tokenIsUsed = true;
	}

	public boolean hasNextSegment() {
		getNextSegment();
		tokenIsUsed = false;

		return (!token.toString().isEmpty());
	}

	public StringBuilder getNextSegment() {

		if (tokenIsUsed) {

			token = new StringBuilder();
			
			try {

				StringBuilder currentLine = new StringBuilder(reader.nextLine());

				while (reader.hasNextLine() && currentLine.toString().isEmpty()) {
					currentLine = new StringBuilder(reader.nextLine());
				}
				
				while (!currentLine.toString().isEmpty()) {
					token.append(currentLine + "\n");
					if (reader.hasNextLine()) {
						currentLine = new StringBuilder(reader.nextLine());
					}
					else {
						currentLine = new StringBuilder();
					}
				}
		   } catch (IOException e) {
		   	return new StringBuilder();
		   }
		}

		if (token.toString().charAt(token.toString().length() - 1) == '\n') {
			token.deleteCharAt(token.toString().length() - 1);
		}

		tokenIsUsed = true;
		return token;
	}

	public void close() {
		try {
			reader.close();
		} catch(IOException e) {
			System.out.println("Problems with scanner closing");
		}
	}

}