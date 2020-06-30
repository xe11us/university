package markup;

import java.util.*;

public class UnorderedList extends AbstractList {

	public UnorderedList(List<ListItem> newList) {
		super(newList);
	}

	public void toTex(StringBuilder lineToTex) {
		super.toTex(lineToTex, "\\begin{itemize}", "\\end{itemize}");
	}
}