import java.io.*;
import java.util.*;

public class WordStatCount {

	static class WordStat {
		String word;
		int amount;
		int firstSeen;

		public WordStat() {
			this.word = null;
			this.amount = -1;
			this.firstSeen = -1;
		}

		public WordStat(String newWord, Integer newAmount, Integer newFirstSeen) {
			this.word = newWord;
			this.amount = newAmount;
			this.firstSeen = newFirstSeen;
		}
	};

	public static Comparator<WordStat> myCompare = new Comparator<WordStat>() {
		public int compare(WordStat a, WordStat b) {
			if (a.amount < b.amount || (a.amount == b.amount && a.firstSeen < b.firstSeen)) {
				return -1;
			}
			else {
				return 1;
			}
		}
	};

	static class Word {
		int firstSeen;
		int amountInText;

		public Word(int first, int cnt) {
			this.firstSeen = first;
			this.amountInText = cnt;
		}
	};
	
	public static void main(String[] args) {

		Map<String, Word> wordData = new HashMap<>();

		try (MyScanner scanner = new MyScanner(new File(args[0]), "utf8")) {

			int indexOfWordInText = 0;

			while (scanner.hasNextWord()) {
				String currentWord = scanner.nextWord();

				currentWord = currentWord.toLowerCase();

				if (!wordData.containsKey(currentWord)) {
						wordData.put(currentWord, new Word(indexOfWordInText, 1));
				}
				else {
					int currentWordFirstSeen = wordData.get(currentWord).firstSeen;
					int currentWordAmount = wordData.get(currentWord).amountInText;
					wordData.put(currentWord, new Word(currentWordFirstSeen, currentWordAmount + 1));
				}
				
				indexOfWordInText++;
			}

		} catch(FileNotFoundException e) {
			System.out.println("Can't find input file");
		} catch(UnsupportedEncodingException e) {
			System.out.println("Unsupported encoding in input");
		} catch(IOException e) {
			System.out.println("Wrong operations with input reader");
		}

		List<WordStat> words = new ArrayList<>();

		for (String key : wordData.keySet()) {
			words.add(new WordStat(key, wordData.get(key).amountInText, wordData.get(key).firstSeen));
		}

		try {
			Writer bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF8"));

			Collections.sort(words, myCompare);
			
			for (WordStat currentWord : words) {
				bufferedWriter.write(currentWord.word + " " + currentWord.amount + "\n");
			}

			bufferedWriter.close();

		} catch(FileNotFoundException e) {
			System.out.println("Can't find output file");
		} catch(UnsupportedEncodingException e) {
			System.out.println("Unsupported encoding in output");
		} catch(IOException e) {
			System.out.println("Wrong operations with output reader");
		}
	}

}