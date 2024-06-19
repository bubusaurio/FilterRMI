package FilterRMI;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;


public class ForkJoin extends RecursiveAction {
    private final String[] words;
    private final char letter;
    private final int start;
    private final int end;
    private final String[] filteredWords;
    private final int[] count;

    public ForkJoin(String[] words, char letter, int start, int end, String[] filteredWords, int[] count) {
        this.words = words;
        this.letter = letter;
        this.start = start;
        this.end = end;
        this.filteredWords = filteredWords;
        this.count = count;
    }

    @Override
    protected void compute() {
        if (end - start <= 20) {
            for (int i = start; i < end; i++) {
                if (words[i].toLowerCase().charAt(0) == Character.toLowerCase(letter)) {
                    synchronized (filteredWords) {
                        filteredWords[count[0]++] = words[i];
                    }
                }
            }
        } else {
            int mid = start + (end - start) / 2;
            invokeAll(
                    new ForkJoin(words, letter, start, mid, filteredWords, count),
                    new ForkJoin(words, letter, mid, end, filteredWords, count)
            );
        }
    }

    public static String[] filter(String[] words, char letter) {
        ForkJoinPool pool = new ForkJoinPool();
        String[] filteredWords = new String[words.length];
        int[] count = {0};
        pool.invoke(new ForkJoin(words, letter, 0, words.length, filteredWords, count));
        // Trim the array to the actual number of filtered words
        String[] result = new String[count[0]];
        System.arraycopy(filteredWords, 0, result, 0, count[0]);
        return result;
    }
}
