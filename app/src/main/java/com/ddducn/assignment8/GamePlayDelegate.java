package com.ddducn.assignment8;

public interface GamePlayDelegate {
    /**
     * score update when game is ongoing
     * @param score new score
     */
    void scoreUpdate(int score);

    /**
     * game is ended
     */
    void gameEnd();
}
