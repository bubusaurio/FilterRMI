package FilterRMI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomWordGenerator {
    private static final String WORD_LIST_FILE = "wordlist.txt"; // Path to the word list file

    // Method to read the word list file and store words in a list
    private static List<String> readWordList() {
        List<String> wordList = new ArrayList<>();
        try {
            URL url = RandomWordGenerator.class.getResource(WORD_LIST_FILE);
            if (url != null) {
                String filePath = Paths.get(url.toURI()).toString();
                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        wordList.add(line.trim());
                    }
                }
            } else {
                System.err.println("Word list file not found: " + WORD_LIST_FILE);
            }
        } catch (URISyntaxException | IOException e) {
            System.err.println("Error reading word list file: " + e.getMessage());
        }
        return wordList;
    }

    // Method to generate a random word from the word list
    private static String generateRandomWord(List<String> wordList) {
        Random random = new Random();
        int randomIndex = random.nextInt(wordList.size());
        return wordList.get(randomIndex);
    }
    
    // Method to generate an array of random words
    public static String[] generateRandomWordsArray(int numberOfWords) {
        List<String> wordList = readWordList();
        if (wordList.isEmpty()) {
            System.out.println("Word list is empty or cannot be read. Please check the file: " + WORD_LIST_FILE);
            return new String[0];
        }

        String[] randomWords = new String[numberOfWords];
        for (int i = 0; i < numberOfWords; i++) {
            randomWords[i] = generateRandomWord(wordList);
        }
        return randomWords;
    }

    public static void main(String[] args) {
        int numberOfWords = 5; // Change this to the desired number of random words
        
        System.out.println("Generating " + numberOfWords + " random words:");
        
        // Calling the method to generate an array of random words
        String[] randomWords = generateRandomWordsArray(numberOfWords);
        
        // Printing the array of random words
        for (String word : randomWords) {
            System.out.println(word);
        }
    }
}