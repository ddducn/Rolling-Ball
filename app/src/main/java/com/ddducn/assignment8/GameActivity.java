package com.ddducn.assignment8;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.Arrays;

public class GameActivity extends FullScreenActivity implements GamePlayDelegate {
    private String playerName;
    private int currentScore;
    private TextView scoreView;
    private AlertDialog.Builder alert;
    private int[] finalScores;
    private int scoreCount;
    public GameActivityDelegate gameActivityDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        scoreView = findViewById(R.id.scoreView);

        GameCanvas gc = findViewById(R.id.gameCanvas);
        gc.gamePlayDelegate = this;

        Intent intent = getIntent();
        playerName = intent.getStringExtra("playerName");

        showGameStartMessage();
        setupGameEndAlert();
    }

    private void setupGameEndAlert() {
        alert = new AlertDialog.Builder(this);
        alert.setTitle("You are dead! " + playerName);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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

    private void showGameStartMessage() {
        showMessage(GameActivity.this, playerName + ", Fling the ball now!");
    }

    public void onCloseGameBtnClick(View v) {
        this.finish();
    }

    public void onResetButtonClick(View v) {
        if (gameActivityDelegate != null) gameActivityDelegate.requestReset();
    }

    public void onRankBtnClick(View v) {
        if (gameActivityDelegate != null && gameActivityDelegate.isPlaying()) return;

        String[] scores = new String[scoreCount == 0 ? 1 : scoreCount];
        if (scoreCount == 0) {
            scores[0] = "No top score available, please play the game first";
        } else {
            for (int i = 0; i < scoreCount; i++) {
                scores[i] = finalScores[finalScores.length - 1 - i] + "";
            }
        }

        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra("player", playerName);
        intent.putExtra("scores", scores);
        startActivity(intent);
    }

    @Override
    public void scoreUpdate(int score) {
        currentScore = score;
        scoreView.setText("Score: " + currentScore);
    }

    @Override
    public void gameEnd() {
        recordScores(5);

        alert.setMessage("Your score: " + currentScore);
        alert.show();
    }

    private void recordScores(int topCount) {
        if (currentScore == 0) return;
        if (finalScores == null) finalScores = new int[topCount];

        for (int i = topCount - 1; i >= topCount - scoreCount; i--) {
            if (finalScores[i] == currentScore) return;
        }

        if (scoreCount == topCount) {
            if (currentScore < finalScores[0]) return;

            finalScores[0] = currentScore;

            if (finalScores[0] > finalScores[1]) Arrays.sort(finalScores);

            return;
        }

        finalScores[topCount - scoreCount - 1] = currentScore;

        if (scoreCount >= 1 && finalScores[topCount - scoreCount - 1] > finalScores[topCount - scoreCount]) Arrays.sort(finalScores);

        scoreCount += 1;
    }
}
