package markup;

import java.util.*;

public class Strong extends AbstractMarkUp {

	public Strong(List<MarkUpInterface> newList) {
		super(newList);
	}

	public void toMarkdown(StringBuilder lineToMarkdown) {
		super.toMarkdown(lineToMarkdown, "__");
	}

	public void toTex(StringBuilder lineToTex) {
		super.toTex(lineToTex, "\\textbf{", "}");
	}
}