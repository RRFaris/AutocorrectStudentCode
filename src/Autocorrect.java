import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

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

    String[] words;
    int threshold;


    public Autocorrect(String[] words, int threshold) {
        this.words = words;
        this.threshold = threshold;
    }

    /**
     * Runs a test from the tester file, AutocorrectTester.
     * @param typed The (potentially) misspelled word, provided by the user.
     * @return An array of all dictionary words with an edit distance less than or equal
     * to threshold, sorted by edit distnace, then sorted alphabetically.
     */
    public String[] runTest(String typed) {
        words = loadDictionary("small.txt");
        threshold = 3;

        int[] distances = new int[words.length];
        int index = 0;

        for (String word : words) {
            distances[index] = findEditDistance(word, typed);
            index++;
        }

        Arrays.sort(distances);

        String[] close = new String[threshold];
        for (int i = 0; i < threshold; i++) {
            close[i] = distances[i];
        }

        return new String[0];
    }

    public static int findEditDistance(String word, String typed) {
        int wLength = word.length();
        int tLength = typed.length();

        if (tLength == 0)
            return wLength;

        else if (wLength == 0)
            return tLength;

        else if (word.charAt(wLength-1) == typed.charAt(tLength-1))
            return findEditDistance(word.substring(0,wLength-1), typed.substring(0,tLength-1));

        else {
            int min = Math.min(findEditDistance(word.substring(0, wLength-1), typed), findEditDistance(word, typed.substring(0, tLength-1)));
            return 1 + Math.min(min, findEditDistance(word.substring(0, wLength-1), typed.substring(0, tLength-1));
        }
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