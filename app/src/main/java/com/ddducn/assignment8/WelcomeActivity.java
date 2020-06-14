package com.ddducn.assignment8;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WelcomeActivity extends FullScreenActivity {
    private NameEditText nameInput;
    private View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) setFullScreen();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        nameInput = findViewById(R.id.nameInput);
        nameInput.setOnFocusChangeListener(focusChangeListener);
    }

    public void onPlayBtnClick(View v) {
        if (nameInput.getText().toString().isEmpty()) {
            showMessage(this, "Please input your name to start");
            return;
        }

        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void onRankBtnClick(View v) {
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);
    }
}
