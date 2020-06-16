package com.ddducn.assignment8;

public interface GameActivityDelegate {
    /**
     * request to reset the game
     */
    void requestReset();

    /**
     * check if the game is ongoing
     * @return is ongoing
     */
    boolean isPlaying();
}
