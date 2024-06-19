package FilterRMI;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;

public class ExecutorServiceFilter{
    public static String[] filter(String[] words, char letter) {
        //ExecutorService executor = Executors.newCachedThreadPool();
        ExecutorService executor = Executors.newFixedThreadPool(8);
        int numThreads = Runtime.getRuntime().availableProcessors();
        int chunkSize = (int) Math.ceil((double) words.length / numThreads);

        String[] filteredWords = new String[words.length];
        final int[] count = {0}; // Using an array to hold the count variable

        for (int i = 0; i < words.length; i += chunkSize) {
            int start = i;
            int end = Math.min(i + chunkSize, words.length);
            executor.submit(new FilterTask(words, letter, start, end, filteredWords, count));
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Trim the array to the actual number of filtered words
        String[] result = new String[count[0]];
        System.arraycopy(filteredWords, 0, result, 0, count[0]);
        return result;
    }

    private static class FilterTask implements Runnable {
        private final String[] words;
        private final char letter;
        private final int start;
        private final int end;
        private final String[] filteredWords;
        private final int[] count;

        public FilterTask(String[] words, char letter, int start, int end, String[] filteredWords, int[] count) {
            this.words = words;
            this.letter = letter;
            this.start = start;
            this.end = end;
            this.filteredWords = filteredWords;
            this.count = count;
        }

        @Override
        public void run() {
            for (int j = start; j < end; j++) {
                if (words[j].charAt(0) == letter) {
                    synchronized (filteredWords) {
                        filteredWords[count[0]++] = words[j];
                    }
                }
            }
        }
    }
}
