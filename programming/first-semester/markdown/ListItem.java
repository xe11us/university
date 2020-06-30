package markup;

import java.util.*;

public class ListItem implements MainInterface {
	
	public List<MainInterface> list = new ArrayList();

	public ListItem(List<MainInterface> newList) {
		list = newList;
	}

	public void toTex(StringBuilder lineToTex) {
		lineToTex.append("\\item ");
		
		for (MainInterface current : list) {
			current.toTex(lineToTex);
		}
	}
}