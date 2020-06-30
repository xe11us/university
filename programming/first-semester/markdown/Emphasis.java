package markup;

import java.util.*;

public class Emphasis extends AbstractMarkUp {

	public Emphasis(List<MarkUpInterface> newList) {
		super(newList);
	}
	
	public void toMarkdown(StringBuilder lineToMarkdown) {
		super.toMarkdown(lineToMarkdown, "*");
	}

	public void toTex(StringBuilder lineToTex) {
		super.toTex(lineToTex, "\\emph{", "}");
	}
}