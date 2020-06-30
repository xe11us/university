package markup;

import java.util.*;

public class OrderedList extends AbstractList {

	public OrderedList(List<ListItem> newList) {
		super(newList);
	}

	public void toTex(StringBuilder lineToTex) {
		super.toTex(lineToTex, "\\begin{enumerate}", "\\end{enumerate}");
	}
}