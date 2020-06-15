package com.ddducn.assignment8;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class GameActivity extends FullScreenActivity implements GamePlayDelegate {
    private String playerName;
    private int currentScore;
    private TextView scoreView;
    private AlertDialog.Builder alert;
    private Set<ScoreRecord> scoreSets = new HashSet<>();
    private List<ScoreRecord> finalScores = new ArrayList<>();

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
        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra("player", playerName);
        intent.putExtra("scores", finalScores.toArray());
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
        ScoreRecord sr = new ScoreRecord(currentScore);
        scoreSets.add(sr);

        PriorityQueue<ScoreRecord> sortedScores = new PriorityQueue<>(scoreSets);
        finalScores.clear();
        scoreSets.clear();

        for (int i = 0; i < topCount; i++) {
            if (sortedScores.isEmpty()) break;
            ScoreRecord current = sortedScores.poll();
            finalScores.add(current);
            scoreSets.add(current);
        }
        sortedScores.clear();
    }
}
