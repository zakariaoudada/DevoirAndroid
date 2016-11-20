package devoir.android.emsi.ma.devoir;

/**
 * Created by Zakaria on 19/11/2016.
 */

public class Note {
    private String label;
    private Double score;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Note(String label, Double score) {
        this.label = label;
        this.score = score;
    }

    public Note() {
    }
}
