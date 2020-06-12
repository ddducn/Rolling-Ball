package com.ddducn.assignment8;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends FullScreenActivity implements GamePlay {
    private String userName = "DDDucn";
    private int currentScore;
    private TextView scoreView;
    AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Toast.makeText(GameActivity.this, "Play now!", Toast.LENGTH_SHORT).show();

        scoreView = findViewById(R.id.scoreView);

        GameCanvas gc = findViewById(R.id.gameCanvas);
        gc.gamePlayDelegate = this;

        alert = new AlertDialog.Builder(this);
        alert.setTitle("Game Over");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentScore = 0;
                scoreView.setText("Score: " + currentScore);
                setFullScreen();
                Toast.makeText(GameActivity.this, "Play now!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void scoreUpdate(int score) {
        currentScore = score;

        scoreView.setText("Score: " + currentScore);
    }

    @Override
    public void gameStart() {
        Log.i("game", "game start");
    }

    @Override
    public void gameEnd() {
        alert.setMessage("Your score: " + currentScore);
        alert.show();
    }
}
