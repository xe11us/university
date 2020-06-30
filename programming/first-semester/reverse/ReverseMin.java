import java.util.*;
import java.io.*;

public class ReverseMin {
	public static void main(String[] args) {

	int[][] array = new int[8][];
	int[] buffer = new int[8];
	int lineIndex = 0;
	int amountOfColumns = 0;
	int maxNumber = 0;

	try (MyScanner scanner = new MyScanner()) {

		while(scanner.hasNextLine()) {
			String currentLine = scanner.nextLine();

			int idInLine = 0;

			try (MyScanner numbers = new MyScanner(currentLine)) {
				while(numbers.hasNextInt()) {
					int currentNumber = numbers.nextInt();
					maxNumber = Math.max(maxNumber, currentNumber);

					if (buffer.length <= idInLine) {
						buffer = Arrays.copyOf(buffer, buffer.length * 2);
					}

					buffer[idInLine] = currentNumber;
					idInLine++;	
				}
			} catch(IOException e) {
				System.out.println("Troubles with string scanner");
			}

			amountOfColumns = Math.max(amountOfColumns, idInLine);

			if (array.length <= lineIndex) {
				array = Arrays.copyOf(array, array.length * 2);
			}

			array[lineIndex] = Arrays.copyOf(buffer, idInLine);
			lineIndex++;
		}

    } catch(Exception e) {
    	System.out.println("Troubles with scanner initialization");
    }

    int amountOfLines = lineIndex;
	int[] minInLine = new int[amountOfLines];
	int[] minInColumn = new int[amountOfColumns];

	for (int i = 0; i < amountOfLines; i++) {
		minInLine[i] = maxNumber;
	}

	for (int i = 0; i < amountOfColumns; i++) {
		minInColumn[i] = maxNumber;
	}

	for (int i = 0; i < amountOfLines; i++) {
		for (int j = 0; j < array[i].length; j++) {
			minInLine[i] = Math.min(minInLine[i], array[i][j]);
			minInColumn[j] = Math.min(minInColumn[j], array[i][j]);
		}
	}

	for (int i = 0; i < amountOfLines; i++) {
		for (int j = 0; j < array[i].length; j++) {
			System.out.print(Math.min(minInLine[i], minInColumn[j]) + " ");
		}
		System.out.println();
	}

	}
}