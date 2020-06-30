package markup;

import java.util.*;

public abstract class AbstractList implements MainInterface {

	protected List<ListItem> list;

	protected AbstractList(List<ListItem> newList) {
		list = newList;
	}

	protected void toTex(StringBuilder lineToTex, String beginSymb, String endSymb) {
		lineToTex.append(beginSymb);

		for (ListItem currentListItem : list) {
			currentListItem.toTex(lineToTex);
		}

		lineToTex.append(endSymb);
	}

	public abstract void toTex(StringBuilder lineToTex);
}