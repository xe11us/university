package md2html;

import java.io.*;
import java.util.*;


public class Md2Html {
	public static void main(String[] args) {
		
		HtmlReader reader = new HtmlReader(args[0]);
		try {

			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF-8"));

			while (reader.hasNextSegment()) {
				StringBuilder currentSegment = reader.getNextSegment();

				HtmlParser parser = new HtmlParser(currentSegment);

				int currentSegmentLevel = parser.isHeader();

				currentSegment = parser.parseSegment();

				if (currentSegmentLevel != 0) {
					writer.write("<h" + currentSegmentLevel + ">" + currentSegment.toString() + "</h" + currentSegmentLevel + ">\n");
				} else {
					writer.write("<p>" + currentSegment.toString() + "</p>\n");
				} 
			}
			writer.close();
		} catch(IOException e) {
			System.out.println("Problems with writer");
		};

		reader.close();
	}
}