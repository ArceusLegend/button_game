package com.example.kapnakis_proodos;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;

public class GameOverActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
    }
}
