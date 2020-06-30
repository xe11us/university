package markup;

public class Text implements MarkUpInterface {

	String text;

	public Text(String newText) {
		text = newText;
	}

	public void toMarkdown(StringBuilder lineToMarkdown) {
		lineToMarkdown.append(text);
	}

	public void toTex(StringBuilder lineToTex) {
		lineToTex.append(text);
	}
}