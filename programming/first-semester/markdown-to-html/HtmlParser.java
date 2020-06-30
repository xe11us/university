package md2html;

import java.util.*;
import java.io.*;

public class HtmlParser {

	private StringBuilder segment;

	public HtmlParser(StringBuilder segment) {
		this.segment = segment;
	}

	public int isHeader() {
		int index = 0;
		int headerLevel = 0;
		if (segment.toString().length() == 0) {
			return 0;
		}
		while (segment.charAt(index) == '#') {
			index++;
		}

		headerLevel = index;

		if (headerLevel > 0 && segment.charAt(index) == ' ') {
			index++;
		}

		if (headerLevel > 0 && index > headerLevel) {
			segment.delete(0, index);
			return headerLevel;
		}

		return 0;
	}

	Set<Integer> toSkip = new HashSet();
	Set<Integer> linksOpenings = new HashSet();
	Set<Integer> linksClosings = new HashSet();
	Set<Integer> closingSymbols = new HashSet();
	Map<Integer, Integer> toReplace = new HashMap();
	Map<Integer, Integer> jump = new HashMap();


	private int specialSymbolType(int pos) {
		int type = getSpecialSymbolNumber(pos);
		if (type == 0) {
			return 0;
		}
		else if (type <= 5) {
			return 1;  //single symbol
		}
		else {
			return 2;  //double symbol
		}
	}

	private int getSpecialSymbolNumber(int pos) {
		char currentSymbol = segment.charAt(pos);
		char nextSymbol = 'h';
		if (pos + 1 < segment.length()) {
			nextSymbol = segment.charAt(pos + 1);
		}

		if (pos + 1 == segment.length() || nextSymbol != currentSymbol) {
			switch(currentSymbol) {
				case '*': return 1;
				case '_': return 2;
				case '`': return 3;
				case '[': return 4;
				case ']': return 5;
			}
		}
		else {
			switch(currentSymbol) {
				case '*': return 6;
				case '_': return 7;
				case '-': return 8;
			}
		}

		return 0;
	}

	private boolean isMatch(int firstSymbolCode, int secondSymbolCode) {
		return (firstSymbolCode == secondSymbolCode) || (firstSymbolCode == 4 && secondSymbolCode == 5);
	}

	public StringBuilder parseSegment() {
		List<Integer> specialSymbolsStack = new ArrayList();
		List<Integer> specialSymbolIndexesStack = new ArrayList();

		for (int pos = 0; pos < segment.length(); pos++) {
			if (toSkip.contains(pos)) {
				continue;
			}

			if (pos < segment.length() - 1 && segment.charAt(pos) == '\\') {
				toSkip.add(pos);
				pos++;
				continue;
			}

			int symbolType = specialSymbolType(pos);

			if (symbolType != 0) {
				int lastSpecialSymbolNumber = -1;
				int lastSpecialSymbolIndex = -1;
				if (!specialSymbolsStack.isEmpty()) {
					lastSpecialSymbolNumber = specialSymbolsStack.get(specialSymbolsStack.size() - 1);
					lastSpecialSymbolIndex = specialSymbolIndexesStack.get(specialSymbolIndexesStack.size() - 1);
				}
				
				if (isMatch(lastSpecialSymbolNumber, getSpecialSymbolNumber(pos))) {
					toReplace.put(specialSymbolIndexesStack.get(specialSymbolIndexesStack.size() - 1), getSpecialSymbolNumber(pos));
					toReplace.put(pos, getSpecialSymbolNumber(pos));

					specialSymbolsStack.remove(specialSymbolsStack.size() - 1);
					specialSymbolIndexesStack.remove(specialSymbolIndexesStack.size() - 1);

					closingSymbols.add(pos);

					if (segment.charAt(pos) == ']' && pos + 1 < segment.length() && segment.charAt(pos + 1) == '(') {
						jump.put(lastSpecialSymbolIndex, pos + 2);
						linksOpenings.add(lastSpecialSymbolIndex);
						linksClosings.add(pos);
						int nextPos = pos;
						while (nextPos < segment.length() && segment.charAt(nextPos) != ')') {
							nextPos++;
						}
						jump.put(pos, nextPos + 1);
						pos = nextPos;
					}
				}
				else {
					specialSymbolsStack.add(getSpecialSymbolNumber(pos));
					specialSymbolIndexesStack.add(pos);
				}

				if (symbolType == 2) {
					toSkip.add(pos + 1);
				}
			}
		}	

		StringBuilder answer = new StringBuilder();

		for (int pos = 0; pos < segment.length(); pos++) {
			if (toSkip.contains(pos)) {
				continue;
			}

			if (linksOpenings.contains(pos)) {
				answer.append("<a href='");
				int linkPos = jump.get(pos);

				while (segment.charAt(linkPos) != ')') {
					answer.append(segment.charAt(linkPos));
					linkPos++;
				}

				answer.append("'>");
				continue;
			}
			if (linksClosings.contains(pos)) {
				answer.append("</a>");
				pos = jump.get(pos) - 1;
				continue;
			}

			if (toReplace.get(pos) != null) {
				answer.append(getReplaceString(getSpecialSymbolNumber(pos), closingSymbols.contains(pos)));
			}
			else {
				if (segment.charAt(pos) == '<') {
					answer.append("&lt;");
				}
				else if (segment.charAt(pos) == '>') {
					answer.append("&gt;");
				}
				else if (segment.charAt(pos) == '&') {
					answer.append("&amp;");
				}
				else {
					answer.append(segment.charAt(pos));
				}
			}
		}

		toSkip.clear();
		closingSymbols.clear();
		toReplace.clear();
		jump.clear();
		linksOpenings.clear();
		linksClosings.clear();

		return answer;
	}

	private String getReplaceString(int symbolCode, boolean isClosing) {
		String answer = "<";

		if (isClosing) {
			answer += '/';
		}

		switch(symbolCode) {
			case 1: answer += "em>"; break;
			case 2: answer += "em>"; break;
			case 3: answer += "code>"; break;
			case 4: answer = "["; break;
			case 5: answer = "]"; break;
			case 6: answer += "strong>"; break;
			case 7: answer += "strong>"; break;
			case 8: answer += "s>"; break;
		}

		return answer;
	}

}