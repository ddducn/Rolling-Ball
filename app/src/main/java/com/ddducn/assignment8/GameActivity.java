package com.ddducn.assignment8;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameActivity extends FullScreenActivity implements GamePlayDelegate {
    private String playerName;
    private int currentScore;
    private TextView scoreView;
    private AlertDialog.Builder alert;

    public GameActivityDelegate gameActivityDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        scoreView = findViewById(R.id.scoreView);

        GameCanvas gc = findViewById(R.id.gameCanvas);
        gc.gamePlayDelegate = this;

        playerName = getIntent().getStringExtra("playerName");
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
        startActivity(intent);
    }

    @Override
    public void scoreUpdate(int score) {
        currentScore = score;
        scoreView.setText("Score: " + currentScore);
    }

    @Override
    public void gameEnd() {
        alert.setMessage("Your score: " + currentScore);
        alert.show();
    }
}
