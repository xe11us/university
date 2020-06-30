package markup;

import java.util.*;

public class Strikeout extends AbstractMarkUp {

	public Strikeout(List<MarkUpInterface> newList) {
		super(newList);
	}

	public void toMarkdown(StringBuilder lineToMarkdown) {
		super.toMarkdown(lineToMarkdown, "~");
	}

	public void toTex(StringBuilder lineToTex) {
		super.toTex(lineToTex, "\\textst{", "}");
	}
}