import java.io.*;
import java.util.*;

public class MyScanner implements AutoCloseable {
	private Reader myReader;
	private String charSet = "UTF-8";
	private char[] buffer = new char[4096];
	private int bufferSize = 0;
	private int indexInBuffer = 0;
	private boolean endOfFile = false;
	private String currentToken = null;

	public MyScanner() throws IOException {
		myReader = new InputStreamReader(System.in);
	}

	public MyScanner(InputStream io) throws IOException {
		myReader = new InputStreamReader(io, charSet);
	}

	public MyScanner(File io, String anotherCharSet) throws IOException {
		myReader = new InputStreamReader(new FileInputStream(io), anotherCharSet);
	}

	public MyScanner(String s) throws IOException {
		myReader = new StringReader(s);
	}

	public boolean fits(char toCheck) {
		return Character.isLetter(toCheck) || toCheck == '\'' || Character.getType(toCheck) == Character.DASH_PUNCTUATION;
	}

	private void readBuffer() throws IOException {
		bufferSize = myReader.read(buffer);

		while (bufferSize == 0) {
			bufferSize = myReader.read(buffer);
		}

		if (bufferSize == -1) {
			endOfFile = true;
			return;
		}

		indexInBuffer = 0;
	}

	public boolean hasNext() throws IOException {
		if (indexInBuffer >= bufferSize) {
			readBuffer();
		}

		return !endOfFile;
	}

	public char nextChar() throws IOException {
		return buffer[indexInBuffer++];
	}

	private char lookAtNextChar()  throws IOException {
		return buffer[indexInBuffer];
	}

	public boolean hasNextLine() throws IOException {
		while (hasNext() && lookAtNextChar() == '\n') {
			indexInBuffer++;
		}
		return !endOfFile;
	}

	public String nextLine() throws IOException {

		if (!hasNextLine()) {
			throw new IOException();
		}

		StringBuilder line = new StringBuilder();

		char currentSymb;

		while (hasNext() && (currentSymb = nextChar()) != '\n') {
			if (currentSymb != '\r') {
				line.append(currentSymb);
			}
		}

		return line.toString();
	}

	public boolean hasNextWord() throws IOException {
		while (hasNext() && !fits(lookAtNextChar())) {
			indexInBuffer++;
		}

		return !endOfFile;
	}

	public String nextWord() throws IOException {

		if (!hasNextWord()) {
			throw new IOException();
		}

		skipBlank();

		StringBuilder token = new StringBuilder();

		while (hasNext() && fits(lookAtNextChar())) {
			token.append(nextChar());
		}

		return token.toString();
	}

	public boolean hasNextToken() throws IOException {
		skipBlank();

		return !endOfFile;
	}

	public String nextToken() throws IOException {
		skipBlank();

		if (currentToken != null) {
			return currentToken;
		}

		StringBuilder token = new StringBuilder();

		while (hasNext() && !Character.isWhitespace(lookAtNextChar())) {
			token.append(lookAtNextChar());
			indexInBuffer++;
		}

		currentToken = token.toString();
		return currentToken;
	}

	public boolean hasNextInt() throws IOException {
		String token = nextToken();

		try {
			int tmp = Integer.parseInt(token.toString());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public int nextInt() throws IOException {

		String token = nextToken();

		currentToken = null;

		try {
			return Integer.parseInt(token.toString());
		} catch (NumberFormatException e) {
			throw new InputMismatchException();
		}
	}

	public void skipBlank() throws IOException {
		while (hasNext() && Character.isWhitespace(lookAtNextChar())) {
			indexInBuffer++;
		}
	}

	public void close() throws IOException {
		myReader.close();
	}
}