package com.ddducn.assignment8;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WelcomeActivity extends FullScreenActivity {
    private NameEditText nameInput; // name input

    private View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            // re enter full screen when name input lost focus
            if (!hasFocus) setFullScreen();
        }
    }; // focus change listener for name input

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // set name input
        nameInput = findViewById(R.id.nameInput);
        nameInput.setOnFocusChangeListener(focusChangeListener);
    }

    public void onPlayBtnClick(View v) {
        // get player name
        String player = nameInput.getText().toString();

        // show the tip if no name is inputted
        if (player.isEmpty()) {
            showMessage(this, "Please input your name to start");
            return;
        }

        // start the game and pass player
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("playerName", player);
        startActivity(intent);
    }
}
