package com.ddducn.assignment8;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ScoreActivity extends FullScreenActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        // get scores and player name
        Intent intent = getIntent();
        String[] scores =  intent.getStringArrayExtra("scores");
        String player = intent.getStringExtra("player");

        // the title with player name
        TextView textView = findViewById(R.id.scoreTitle);
        textView.setText(player + "'s \ntop scores");

        // the scores to list view
        ListView listView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scores);
        listView.setAdapter(adapter);
    }

    /**
     * click close button to finish the score screen
     */
    public void onCloseRankBtnClick(View v) {
        this.finish();
    }
}
