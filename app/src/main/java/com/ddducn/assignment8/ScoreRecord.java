package com.ddducn.assignment8;

import java.util.Objects;

public class ScoreRecord implements Comparable {
    private int score;

    public ScoreRecord(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return score + "";
    }

    @Override
    public int compareTo(Object o) {
        return ((ScoreRecord)o).score - score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoreRecord that = (ScoreRecord) o;
        return score == that.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(score);
    }
}
