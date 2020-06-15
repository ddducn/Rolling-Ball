package com.ddducn.assignment8;

import android.os.Bundle;
import android.view.View;


public class ScoreActivity extends FullScreenActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
    }

    public void onCloseRankBtnClick(View v) {
        this.finish();
    }
}
