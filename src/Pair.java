import java.lang.reflect.Array;
import java.util.ArrayList;

public class Pair {
    private int editDistance;
    private String word;

    public Pair(int editDistance, String word) {
        this.editDistance = editDistance;
        this.word = word;
    }

    public int getEditDistance() {
        return editDistance;
    }

    public void setEditDistance(int editDistance) {
        this.editDistance = editDistance;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
