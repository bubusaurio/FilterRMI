package FilterRMI;

public class Sequential {
    public static String[] filter(String[] words, char letter) {
        int count = 0;

        for (String word : words) {
            if (word.toLowerCase().charAt(0) == Character.toLowerCase(letter)) {
                count++;
            }
        }

        String[] filteredWords = new String[count];
        int index = 0;

        for (String word : words) {
            if (word.toLowerCase().charAt(0) == Character.toLowerCase(letter)) {
                filteredWords[index++] = word;
            }
        }

        return filteredWords;
    }
}
