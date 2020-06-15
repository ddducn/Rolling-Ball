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
        String player = nameInput.getText().toString();
        if (player.isEmpty()) {
            showMessage(this, "Please input your name to start");
            return;
        }

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("playerName", player);
        startActivity(intent);
    }
}
