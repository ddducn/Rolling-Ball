package com.ddducn.assignment8;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.Arrays;

public class GameActivity extends FullScreenActivity implements GamePlayDelegate {
    private String playerName; // player name
    private int currentScore; // current score
    private TextView scoreView; // score view

    private AlertDialog.Builder alert; // alert after game is ended

    private int[] finalScores; // final ranked scores
    private int scoreCount; // count of recorded scores

    public GameActivityDelegate gameActivityDelegate; // delegate

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        scoreView = findViewById(R.id.scoreView);

        // setup delegate
        GameCanvas gc = findViewById(R.id.gameCanvas);
        gc.gamePlayDelegate = this;

        // get player name
        Intent intent = getIntent();
        playerName = intent.getStringExtra("playerName");

        showGameStartMessage();
        setupGameEndAlert();
    }

    /**
     * create game end alert
     */
    private void setupGameEndAlert() {
        alert = new AlertDialog.Builder(this);
        alert.setTitle("You are dead! " + playerName);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // reset current score
                currentScore = 0;
                scoreView.setText("Score: " + currentScore);
            }
        });
        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                setFullScreen();
                showGameStartMessage();
            }
        });
    }

    /**
     * show game start message
     */
    private void showGameStartMessage() {
        showMessage(GameActivity.this, playerName + ", Fling the ball now!");
    }

    /**
     * dismiss game screen
     */
    public void onCloseGameBtnClick(View v) {
        this.finish();
    }

    /**
     * reset the game
     */
    public void onResetButtonClick(View v) {
        if (gameActivityDelegate != null) gameActivityDelegate.requestReset();
    }

    /**
     * go to score screen
     */
    public void onRankBtnClick(View v) {
        // return if the is game is ongoing
        if (gameActivityDelegate != null && gameActivityDelegate.isPlaying()) return;

        // prepare ranked data
        String[] scores = new String[scoreCount == 0 ? 1 : scoreCount];
        if (scoreCount == 0) {
            scores[0] = "No top score available, please play the game first";
        } else {
            for (int i = 0; i < scoreCount; i++) {
                scores[i] = finalScores[finalScores.length - 1 - i] + "";
            }
        }

        // jump to score screen
        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra("player", playerName);
        intent.putExtra("scores", scores);
        startActivity(intent);
    }

    @Override
    public void scoreUpdate(int score) {
        // setup current score and score view
        currentScore = score;
        scoreView.setText("Score: " + currentScore);
    }

    @Override
    public void gameEnd() {
        // record top score
        recordScores(5);

        // show alert
        alert.setMessage("Your score: " + currentScore);
        alert.show();
    }

    /**
     * record and calculate top 5 scores
     * @param topCount top count
     */
    private void recordScores(int topCount) {
        // exclude 0 score
        if (currentScore == 0) return;
        if (finalScores == null) finalScores = new int[topCount];

        // return if is duplicated
        for (int i = topCount - 1; i >= topCount - scoreCount; i--) {
            if (finalScores[i] == currentScore) return;
        }

        // final score array is full
        if (scoreCount == topCount) {
            // new score is smaller than smallest score
            if (currentScore < finalScores[0]) return;

            // update and resort array
            finalScores[0] = currentScore;
            if (finalScores[0] > finalScores[1]) Arrays.sort(finalScores);

            return;
        }

        // append new score to array
        finalScores[topCount - scoreCount - 1] = currentScore;

        // resort the array only if new score is bigger than the smallest score
        if (scoreCount >= 1 && finalScores[topCount - scoreCount - 1] > finalScores[topCount - scoreCount]) Arrays.sort(finalScores);

        // update current score count
        scoreCount += 1;
    }
}
