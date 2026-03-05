import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Autocorrect
 * <p>
 * A command-line tool to suggest similar words when given one not in the dictionary.
 * </p>
 * @author Zach Blick
 * @author YOUR NAME HERE
 */
public class Autocorrect {

    /**
     * Constucts an instance of the Autocorrect class.
     * @param words The dictionary of acceptable words.
     * @param threshold The maximum number of edits a suggestion can have.
     */
    private String[] words;
    private int threshold;
    private ArrayList<Pair> pairs;


    public Autocorrect(String[] words, int threshold) {
        this.words = words;
        this.threshold = threshold;
        pairs = new ArrayList<>();
    }

    /**
     * Runs a test from the tester file, AutocorrectTester.
     * @param typed The (potentially) misspelled word, provided by the user.
     * @return An array of all dictionary words with an edit distance less than or equal
     * to threshold, sorted by edit distance, then sorted alphabetically.
     */
    public String[] runTest(String typed) {

        for (String word : words) {
            Pair pair = new Pair(findEditDistance(word, typed), word);
            pairs.add(pair);
        }

        // Sort alphabetically first
        pairs.sort(Comparator.comparing(Pair::getWord));

        // Then sort by edit distance
        pairs.sort(Comparator.comparingInt(Pair::getEditDistance));

        int index = 0;
        ArrayList<Pair> close = new ArrayList<>();
        while (pairs.get(index).getEditDistance() <= threshold) {
            close.add(pairs.get(index));
            index++;
        }

        String[] withinEditDistance = new String[close.size()];
        for (int i = 0; i < close.size(); i++) {
            withinEditDistance[i] = pairs.get(i).getWord();
        }

        return withinEditDistance;
    }

    public static int findEditDistance(String word, String typed) {
        int[][] tabs = new int[word.length() + 1][typed.length() + 1];

        // Add base cases
        for (int i = 0; i < word.length() + 1; i++) {
            tabs[i][0] = i;
        }

        for (int i = 0; i < typed.length() + 1; i++) {
            tabs[0][i] = i;
        }

        for (int i = 1; i < word.length() + 1; i++) {
            for (int j = 1; j < typed.length() + 1; j++) {
                // If the letters match, the edit distance won't change so we can just get the edit distance from our
                // substitution case
                if (word.charAt(i - 1) == typed.charAt(j - 1))
                    tabs[i][j] = tabs[i - 1][j - 1];

                else {
                    int min = Math.min(tabs[i][j - 1], tabs[i - 1][j]);
                    tabs[i][j] = Math.min(min, tabs[i - 1][j - 1]) + 1;
                }
            }
        }

        return tabs[word.length()][typed.length()];



        //        int wLength = word.length();
//        int tLength = typed.length();
//
//        if (tLength == 0)
//            return wLength;
//
//        else if (wLength == 0)
//            return tLength;
//
//        else if (word.charAt(wLength-1) == typed.charAt(tLength-1))
//            return findEditDistance(word.substring(0,wLength-1), typed.substring(0,tLength-1));
//
//        else {
//            int min = Math.min(findEditDistance(word.substring(0, wLength-1), typed), findEditDistance(word, typed.substring(0, tLength-1)));
//            return 1 + Math.min(min, findEditDistance(word.substring(0, wLength-1), typed.substring(0, tLength-1)));
//        }
    }

    /**
     * Loads a dictionary of words from the provided textfiles in the dictionaries directory.
     * @param dictionary The name of the textfile, [dictionary].txt, in the dictionaries directory.
     * @return An array of Strings containing all words in alphabetical order.
     */
    private static String[] loadDictionary(String dictionary)  {
        try {
            String line;
            BufferedReader dictReader = new BufferedReader(new FileReader("dictionaries/" + dictionary + ".txt"));
            line = dictReader.readLine();

            // Update instance variables with test data
            int n = Integer.parseInt(line);
            String[] words = new String[n];

            for (int i = 0; i < n; i++) {
                line = dictReader.readLine();
                words[i] = line;
            }
            return words;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}