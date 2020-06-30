package markup;

import java.util.*;

public class Paragraph implements MainInterface {
	
	List<MarkUpInterface> elements;

	public Paragraph(List<MarkUpInterface> newList) {
		elements = newList;
	}

	public void toMarkdown(StringBuilder lineToMarkdown) {
		for (MarkUpInterface currentElement : elements) {
			currentElement.toMarkdown(lineToMarkdown);
		}
	}

	public void toTex(StringBuilder lineToTex) {
		for (MarkUpInterface currentElement : elements) {
			currentElement.toTex(lineToTex);
		}
	}
}