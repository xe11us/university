public class SumDouble {

    public static void main(String[] args) {

		double answer = 0.0;

	    for (int i = 0; i < args.length; i++) {
			int left = 0;

			for (int j = 0; j < args[i].length(); j++) {
				char current = args[i].charAt(j);

				if (Character.isWhitespace(current) || (j == args[i].length() - 1 && !Character.isWhitespace(current))) {
					if (j == args[i].length() - 1 && !Character.isWhitespace(current)) {
						j++;	
					}

					if (j > left) { 
						answer += Double.parseDouble(args[i].substring(left, j));
					}

					if (j == args[i].length()) {
						break;
					}

					int nextLeft = j;
					while(Character.isWhitespace(args[i].charAt(nextLeft)) && (nextLeft + 1 < args[i].length())) {
						nextLeft++;
					}
					left = nextLeft;
				}
			}	  
		}

		System.out.println(answer);
	}
}