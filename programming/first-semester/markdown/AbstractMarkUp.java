package markup;

import java.util.*;

public abstract class AbstractMarkUp implements MarkUpInterface {

	List<MarkUpInterface> list = new ArrayList();

	protected AbstractMarkUp(List<MarkUpInterface> newList) {
		this.list = newList;
	}

	protected void toMarkdown(StringBuilder lineToMarkdown, String specialSymbol) {
		lineToMarkdown.append(specialSymbol);

		for (MarkUpInterface currentItem : list) {
			currentItem.toMarkdown(lineToMarkdown);
		}

		lineToMarkdown.append(specialSymbol);
	}

	protected void toTex(StringBuilder lineToTex, String beginSymbol, String endSymbol) {
		lineToTex.append(beginSymbol);

		for (MarkUpInterface currentItem : list) {
			currentItem.toTex(lineToTex);
		}

		lineToTex.append(endSymbol);
	}

	public abstract void toMarkdown(StringBuilder lineToMarkdown);
}